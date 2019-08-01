package com.zhuzichu.mvvm.crash

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.zhuzichu.mvvm.utils.cast
import java.io.PrintWriter
import java.io.Serializable
import java.io.StringWriter
import java.lang.ref.WeakReference
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipFile
import kotlin.system.exitProcess


class CrashClient {

    interface EventListener : Serializable {
        fun onLaunchErrorActivity()

        fun onRestartAppFromErrorActivity()

        fun onCloseAppFromErrorActivity()
    }

    companion object {
        private const val SHARED_PREFERENCES_FILE = "crash_client"
        private const val SHARED_PREFERENCES_FIELD_TIMESTAMP = "last_crash_timestamp"

        //Extras passed to the error activity
        private const val EXTRA_CONFIG = "com.zhuzichu.mvvm.crash.CrashClient.EXTRA_CONFIG"
        private const val EXTRA_STACK_TRACE = "com.zhuzichu.mvvm.crash.CrashClient.EXTRA_STACK_TRACE"
        private const val EXTRA_ACTIVITY_LOG = "com.zhuzichu.mvvm.crash.CrashClient.EXTRA_ACTIVITY_LOG"

        private const val INTENT_ACTION_ERROR_ACTIVITY = "com.zhuzichu.mvvm.crash.CrashClient.ERROR"
        private const val INTENT_ACTION_RESTART_ACTIVITY = "com.zhuzichu.mvvm.crash.CrashClient.RESTART"

        private const val MAX_STACK_TRACE_SIZE = 131071 //128 KB - 1
        private const val MAX_ACTIVITIES_IN_LOG = 50
        private var isInBackground = true
        private lateinit var application: Application
        private var config = CrashConfig()
        private var lastActivityCreated = WeakReference<Activity>(null)

        private val activityLog = ArrayDeque<String>(MAX_ACTIVITIES_IN_LOG)

        fun initall(context: Context?) {
            context?.let {
                val exceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
                exceptionHandler?.let {
                    application = context.applicationContext as Application
                    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                        if (config.isEnable) {
                            if (hasCrashedInTheLastSeconds(application)) {
                                exceptionHandler.uncaughtException(thread, throwable)
                                return@setDefaultUncaughtExceptionHandler
                            } else {
                                setLastCrashTimestamp(application, Date().time)
                                var errorActivityClass = config.getErrorActivityClass()
                                errorActivityClass?.let {
                                    errorActivityClass = guessErrorActivityClass(application)
                                }
                                if (isStackTraceLikelyConflictive(throwable, errorActivityClass)) run {
                                    exceptionHandler.uncaughtException(thread, throwable)
                                    return@setDefaultUncaughtExceptionHandler
                                } else if (config.getBackgroundMode() == CrashConfig.BACKGROUND_MODE_SHOW_CUSTOM || !isInBackground) {
                                    val intent = Intent(application, errorActivityClass)
                                    val sw = StringWriter()
                                    val pw = PrintWriter(sw)
                                    throwable.printStackTrace(pw)
                                    var stackTraceString = sw.toString()
                                    if (stackTraceString.length > MAX_STACK_TRACE_SIZE) {
                                        val disclaimer = " [stack trace too large]"
                                        stackTraceString = stackTraceString.substring(
                                            0,
                                            MAX_STACK_TRACE_SIZE - disclaimer.length
                                        ) + disclaimer
                                    }
                                    intent.putExtra(EXTRA_STACK_TRACE, stackTraceString)

                                    if (config.isTrackActivities) {
                                        var activityLogString = ""
                                        while (!activityLog.isEmpty()) {
                                            activityLogString += activityLog.poll()
                                        }
                                        intent.putExtra(EXTRA_ACTIVITY_LOG, activityLogString)
                                    }

                                    if (config.isShowRestartButton && config.getRestartActivityClass() == null) {
                                        //We can set the restartActivityClass because the app will terminate right now,
                                        //and when relaunched, will be null again by default.
                                        config.setRestartActivityClass(guessRestartActivityClass(application))
                                    }

                                    intent.putExtra(EXTRA_CONFIG, config)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    config.getEventListener()?.onLaunchErrorActivity()
                                    Log.i("zzc","执行了跳转")
                                    application.startActivity(intent)
                                } else if (config.getBackgroundMode() == CrashConfig.BACKGROUND_MODE_CRASH) {
                                    exceptionHandler.uncaughtException(thread, throwable)
                                    return@setDefaultUncaughtExceptionHandler
                                }
                            }
                            val lastActivity = lastActivityCreated.get()
                            lastActivity?.let {
                                lastActivity.finish()
                                lastActivityCreated.clear()
                            }
                            killCurrentProcess()
                        } else {
                            exceptionHandler.uncaughtException(thread, throwable)
                        }
                    }
                }
                application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                    var currentlyStartedActivities = 0
                    var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

                    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                        if (activity.javaClass != config.getErrorActivityClass()) {
                            lastActivityCreated = WeakReference(activity)
                        }
                        if (config.isTrackActivities) {
                            activityLog.add(dateFormat.format(Date()) + ": " + activity.javaClass.simpleName + " created\n")
                        }
                    }

                    override fun onActivityPaused(activity: Activity) {
                        if (config.isTrackActivities) {
                            activityLog.add(dateFormat.format(Date()) + ": " + activity.javaClass.simpleName + " paused\n")
                        }
                    }

                    override fun onActivityStarted(activity: Activity) {
                        currentlyStartedActivities++
                        isInBackground = currentlyStartedActivities == 0
                        //Do nothing
                    }

                    override fun onActivityResumed(activity: Activity) {
                        if (config.isTrackActivities) {
                            activityLog.add(dateFormat.format(Date()) + ": " + activity.javaClass.simpleName + " resumed\n")
                        }
                    }

                    override fun onActivityStopped(activity: Activity) {
                        currentlyStartedActivities--
                        isInBackground = currentlyStartedActivities == 0
                    }

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
                    }

                    override fun onActivityDestroyed(activity: Activity) {
                        if (config.isTrackActivities) {
                            activityLog.add(dateFormat.format(Date()) + ": " + activity.javaClass.simpleName + " destroyed\n")
                        }
                    }

                })
            }
        }

        private fun killCurrentProcess() {
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(10)
        }


        private fun setLastCrashTimestamp(context: Application, timestamp: Long) {
            context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE).edit()
                .putLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, timestamp).apply()
        }

        fun setConifg(config: CrashConfig) {
            this.config = config
        }

        private fun hasCrashedInTheLastSeconds(context: Context): Boolean {
            val lastTimestamp = getLastCrashTimestamp(context)
            val currentTimestamp = Date().time
            return lastTimestamp <= currentTimestamp && currentTimestamp - lastTimestamp < config.getMinTimeBetweenCrashesMs()
        }

        private fun getLastCrashTimestamp(context: Context): Long {
            return context
                .getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
                .getLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, -1)
        }

        private fun guessErrorActivityClass(context: Context): Class<out Activity> {
            var resolvedActivityClass: Class<out Activity>?
            resolvedActivityClass = getErrorActivityClassWithIntentFilter(context)
            if (resolvedActivityClass == null) {
                resolvedActivityClass = DefaultErrorActivity::class.java
            }
            return resolvedActivityClass
        }

        private fun getErrorActivityClassWithIntentFilter(context: Context): Class<out Activity>? {
            val searchedIntent = Intent().setAction(INTENT_ACTION_ERROR_ACTIVITY).setPackage(context.packageName)
            val resolveInfos = context.packageManager.queryIntentActivities(
                searchedIntent,
                PackageManager.GET_RESOLVED_FILTER
            )
            if (resolveInfos.size > 0) {
                val resolveInfo = resolveInfos[0]
                try {
                    return cast(Class.forName(resolveInfo.activityInfo.name))
                } catch (e: ClassNotFoundException) {
                }

            }
            return null
        }

        private fun isStackTraceLikelyConflictive(throwable: Throwable, activityClass: Class<out Activity>?): Boolean {
            val throwableCause = throwable.cause
            do {
                val stackTrace = throwable.stackTrace
                for (element in stackTrace) {
                    if (element.className == "android.app.ActivityThread" && element.methodName == "handleBindApplication" || element.className == activityClass?.name) {
                        return true
                    }
                }
            } while (throwableCause != null)
            return false
        }

        private fun guessRestartActivityClass(context: Context): Class<out Activity>? {
            var resolvedActivityClass: Class<out Activity>?
            resolvedActivityClass = getRestartActivityClassWithIntentFilter(context)
            if (resolvedActivityClass == null) {
                resolvedActivityClass = getLauncherActivity(context)
            }
            return resolvedActivityClass
        }

        fun restartApplication(activity: Activity, config: CrashConfig) {
            val intent = Intent(activity, config.getRestartActivityClass())
            restartApplicationWithIntent(activity, intent, config)
        }

        fun getConfigFromIntent(intent: Intent): CrashConfig {
            return cast(intent.getSerializableExtra(EXTRA_CONFIG))
        }

        private fun restartApplicationWithIntent(activity: Activity, intent: Intent, config: CrashConfig) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            if (intent.component != null) {
                intent.action = Intent.ACTION_MAIN
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
            }
            config.getEventListener()?.onRestartAppFromErrorActivity()
            activity.finish()
            activity.startActivity(intent)
            killCurrentProcess()
        }

        private fun getRestartActivityClassWithIntentFilter(context: Context): Class<out Activity>? {
            val searchedIntent = Intent().setAction(INTENT_ACTION_RESTART_ACTIVITY).setPackage(context.packageName)
            val resolveInfos = context.packageManager.queryIntentActivities(
                searchedIntent,
                PackageManager.GET_RESOLVED_FILTER
            )

            if (resolveInfos.size > 0) {
                val resolveInfo = resolveInfos[0]
                try {
                    return cast(Class.forName(resolveInfo.activityInfo.name))
                } catch (e: ClassNotFoundException) {

                }
            }
            return null
        }

        fun closeApplication(activity: Activity, config: CrashConfig) {
            config.getEventListener()?.onCloseAppFromErrorActivity()
            activity.finish()
            killCurrentProcess()
        }

        private fun getLauncherActivity(context: Context): Class<out Activity>? {
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            if (intent != null) {
                try {
                    return cast(Class.forName(intent.component!!.className))
                } catch (e: ClassNotFoundException) {

                }
            }
            return null
        }

        fun getAllErrorDetailsFromIntent(context: Context, intent: Intent): String {
            //I don't think that this needs localization because it's a development string...

            val currentDate = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

            //Get build date
            val buildDateAsString = getBuildDateAsString(context, dateFormat)

            //Get app version
            val versionName = getVersionName(context)

            var errorDetails = ""

            errorDetails += "Build version: $versionName \n"
            if (buildDateAsString != null) {
                errorDetails += "Build date: $buildDateAsString \n"
            }
            errorDetails += "Current date: " + dateFormat.format(currentDate) + " \n"
            //Added a space between line feeds to fix #18.
            //Ideally, we should not use this method at all... It is only formatted this way because of coupling with the default error activity.
            //We should move it to a method that returns a bean, and let anyone format it as they wish.
            errorDetails += "Device: " + getDeviceModelName() + " \n \n"
            errorDetails += "Stack trace:  \n"
            errorDetails += getStackTraceFromIntent(intent)

            val activityLog = getActivityLogFromIntent(intent)

            if (activityLog != null) {
                errorDetails += "\nUser actions: \n"
                errorDetails += activityLog
            }
            return errorDetails
        }

        private fun getVersionName(context: Context): String {
            return try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName
            } catch (e: Exception) {
                "Unknown"
            }

        }

        private fun getStackTraceFromIntent(intent: Intent): String? {
            return intent.getStringExtra(EXTRA_STACK_TRACE)
        }

        private fun getActivityLogFromIntent(intent: Intent): String? {
            return intent.getStringExtra(EXTRA_ACTIVITY_LOG)
        }

        private fun getDeviceModelName(): String {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

        private fun capitalize(s: String?): String {
            if (s == null || s.isEmpty()) {
                return ""
            }
            val first = s[0]
            return if (Character.isUpperCase(first)) {
                s
            } else {
                Character.toUpperCase(first) + s.substring(1)
            }
        }

        private fun getBuildDateAsString(context: Context, dateFormat: DateFormat): String? {
            var buildDate: Long
            try {
                val ai = context.packageManager.getApplicationInfo(context.packageName, 0)
                val zf = ZipFile(ai.sourceDir)

                //If this failed, try with the old zip method
                val ze = zf.getEntry("classes.dex")
                buildDate = ze.time


                zf.close()
            } catch (e: Exception) {
                buildDate = 0
            }
            return if (buildDate > 312764400000L) {
                dateFormat.format(Date(buildDate))
            } else {
                null
            }
        }
    }
}