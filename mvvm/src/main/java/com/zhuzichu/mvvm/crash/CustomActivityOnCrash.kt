/*
 * Copyright 2014-2017 Eduard Ereza Martínez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhuzichu.mvvm.crash

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RestrictTo
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


object CustomActivityOnCrash {

    private const val TAG = "CustomActivityOnCrash"

    private const val EXTRA_CONFIG = "cat.ereza.customactivityoncrash.EXTRA_CONFIG"
    private const val EXTRA_STACK_TRACE = "cat.ereza.customactivityoncrash.EXTRA_STACK_TRACE"
    private const val EXTRA_ACTIVITY_LOG = "cat.ereza.customactivityoncrash.EXTRA_ACTIVITY_LOG"

    //General constants
    private const val INTENT_ACTION_ERROR_ACTIVITY = "cat.ereza.customactivityoncrash.ERROR"
    private const val INTENT_ACTION_RESTART_ACTIVITY = "cat.ereza.customactivityoncrash.RESTART"
    private const val CAOC_HANDLER_PACKAGE_NAME = "cat.ereza.customactivityoncrash"
    private const val DEFAULT_HANDLER_PACKAGE_NAME = "com.android.internal.os"
    private const val MAX_STACK_TRACE_SIZE = 131071 //128 KB - 1
    private const val MAX_ACTIVITIES_IN_LOG = 50

    //Shared preferences
    private const val SHARED_PREFERENCES_FILE = "custom_activity_on_crash"
    private const val SHARED_PREFERENCES_FIELD_TIMESTAMP = "last_crash_timestamp"

    //Internal variables
    @SuppressLint("StaticFieldLeak") //This is an application-wide component
    private var application: Application? = null

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    @set:RestrictTo(RestrictTo.Scope.LIBRARY)
    var config = CaocConfig()
    private val activityLog = ArrayDeque<String>(MAX_ACTIVITIES_IN_LOG)
    private var lastActivityCreated = WeakReference<Activity>(null)
    private var isInBackground = true

    private val deviceModelName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    fun install(context: Context?) {
        try {
            if (context == null) {
                Log.e(TAG, "Install failed: context is null!")
            } else {
                //INSTALL!
                val oldHandler = Thread.getDefaultUncaughtExceptionHandler()

                if (oldHandler != null && oldHandler.javaClass.name.startsWith(CAOC_HANDLER_PACKAGE_NAME)) {
                    Log.e(TAG, "CustomActivityOnCrash was already installed, doing nothing!")
                } else {
                    if (oldHandler != null && !oldHandler.javaClass.name.startsWith(DEFAULT_HANDLER_PACKAGE_NAME)) {
                        Log.e(
                            TAG,
                            "IMPORTANT WARNING! You already have an UncaughtExceptionHandler, are you sure this is correct? If you use a custom UncaughtExceptionHandler, you must initialize it AFTER CustomActivityOnCrash! Installing anyway, but your original handler will not be called."
                        )
                    }

                    application = context.applicationContext as Application

                    //We define a default exception handler that does what we want so it can be called from Crashlytics/ACRA
                    Thread.setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler { thread, throwable ->
                        if (config.isEnabled) {
                            Log.e(
                                TAG,
                                "App has crashed, executing CustomActivityOnCrash's UncaughtExceptionHandler",
                                throwable
                            )

                            if (hasCrashedInTheLastSeconds(application!!)) {
                                Log.e(
                                    TAG,
                                    "App already crashed recently, not starting custom error activity because we could enter a restart loop. Are you sure that your app does not crash directly on init?",
                                    throwable
                                )
                                if (oldHandler != null) {
                                    oldHandler.uncaughtException(thread, throwable)
                                    return@UncaughtExceptionHandler
                                }
                            } else {
                                setLastCrashTimestamp(application!!, Date().time)
                                var errorActivityClass = config.errorActivityClass

                                if (errorActivityClass == null) {
                                    errorActivityClass = guessErrorActivityClass(application!!)
                                }
                                if (isStackTraceLikelyConflictive(throwable, errorActivityClass)) {
                                    Log.e(
                                        TAG,
                                        "Your application class or your error activity have crashed, the custom activity will not be launched!"
                                    )
                                    if (oldHandler != null) {
                                        oldHandler.uncaughtException(thread, throwable)
                                        return@UncaughtExceptionHandler
                                    }
                                } else if (config.backgroundMode == CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM || !isInBackground) {
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
                                    if (config.isShowRestartButton && config.restartActivityClass == null) {
                                        config.restartActivityClass = guessRestartActivityClass(application!!)
                                    }
                                    intent.putExtra(EXTRA_CONFIG, config)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    if (config.eventListener != null) {
                                        config.eventListener!!.onLaunchErrorActivity()
                                    }
                                    application!!.startActivity(intent)
                                } else if (config.backgroundMode == CaocConfig.BACKGROUND_MODE_CRASH) {
                                    if (oldHandler != null) {
                                        oldHandler.uncaughtException(thread, throwable)
                                        return@UncaughtExceptionHandler
                                    }
                                }
                            }
                            val lastActivity = lastActivityCreated.get()
                            if (lastActivity != null) {
                                lastActivity.finish()
                                lastActivityCreated.clear()
                            }
                            killCurrentProcess()
                        } else oldHandler?.uncaughtException(thread, throwable)
                    })
                    application!!.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                        var currentlyStartedActivities = 0
                        var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

                        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                            if (activity.javaClass != config.errorActivityClass) {
                                lastActivityCreated = WeakReference(activity)
                            }
                            if (config.isTrackActivities) {
                                activityLog.add(dateFormat.format(Date()) + ": " + activity.javaClass.simpleName + " created\n")
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

                        override fun onActivityPaused(activity: Activity) {
                            if (config.isTrackActivities) {
                                activityLog.add(dateFormat.format(Date()) + ": " + activity.javaClass.simpleName + " paused\n")
                            }
                        }

                        override fun onActivityStopped(activity: Activity) {
                            //Do nothing
                            currentlyStartedActivities--
                            isInBackground = currentlyStartedActivities == 0
                        }

                        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                            //Do nothing
                        }

                        override fun onActivityDestroyed(activity: Activity) {
                            if (config.isTrackActivities) {
                                activityLog.add(dateFormat.format(Date()) + ": " + activity.javaClass.simpleName + " destroyed\n")
                            }
                        }
                    })
                }

                Log.i(TAG, "CustomActivityOnCrash has been installed.")
            }
        } catch (t: Throwable) {
            Log.e(
                TAG,
                "An unknown error occurred while installing CustomActivityOnCrash, it may not have been properly initialized. Please report this as a bug if needed.",
                t
            )
        }

    }

    private fun getStackTraceFromIntent(intent: Intent): String? {
        return intent.getStringExtra(EXTRA_STACK_TRACE)
    }


    fun getConfigFromIntent(intent: Intent): CaocConfig {
        return intent.getSerializableExtra(EXTRA_CONFIG) as CaocConfig
    }

    private fun getActivityLogFromIntent(intent: Intent): String? {
        return intent.getStringExtra(EXTRA_ACTIVITY_LOG)
    }

    fun getAllErrorDetailsFromIntent(context: Context, intent: Intent): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val buildDateAsString = getBuildDateAsString(context, dateFormat)
        val versionName = getVersionName(context)
        var errorDetails = ""
        errorDetails += "Build version: $versionName \n"
        if (buildDateAsString != null) {
            errorDetails += "Build date: $buildDateAsString \n"
        }
        errorDetails += "Current date: " + dateFormat.format(currentDate) + " \n"
        errorDetails += "Device: $deviceModelName \n \n"
        errorDetails += "Stack trace:  \n"
        errorDetails += getStackTraceFromIntent(intent)
        val activityLog = getActivityLogFromIntent(intent)
        if (activityLog != null) {
            errorDetails += "\nUser actions: \n"
            errorDetails += activityLog
        }
        return errorDetails
    }

    private fun restartApplicationWithIntent(activity: Activity, intent: Intent, config: CaocConfig) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        if (intent.component != null) {
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
        }
        if (config.eventListener != null) {
            config.eventListener!!.onRestartAppFromErrorActivity()
        }
        activity.finish()
        activity.startActivity(intent)
        killCurrentProcess()
    }

    fun restartApplication(activity: Activity, config: CaocConfig) {
        val intent = Intent(activity, config.restartActivityClass)
        restartApplicationWithIntent(activity, intent, config)
    }

    fun closeApplication(activity: Activity, config: CaocConfig) {
        if (config.eventListener != null) {
            config.eventListener!!.onCloseAppFromErrorActivity()
        }
        activity.finish()
        killCurrentProcess()
    }

    private fun isStackTraceLikelyConflictive(throwable: Throwable, activityClass: Class<out Activity>): Boolean {
        val throwableCause = throwable.cause
        do {
            val stackTrace = throwable.stackTrace
            for (element in stackTrace) {
                if (element.className == "android.app.ActivityThread" && element.methodName == "handleBindApplication" || element.className == activityClass.name) {
                    return true
                }
            }
        } while (throwableCause != null)
        return false
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


    private fun getVersionName(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: Exception) {
            "Unknown"
        }

    }

    /**
     * INTERNAL method that capitalizes the first character of a string
     *
     * @param s The string to capitalize
     * @return The capitalized string
     */
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

    /**
     * INTERNAL method used to guess which activity must be called from the error activity to restart the app.
     * It will first get activities from the AndroidManifest with intent filter <action android:name="cat.ereza.customactivityoncrash.RESTART"></action>,
     * if it cannot find them, then it will get the default launcher.
     * If there is no default launcher, this returns null.
     *
     * @param context A valid context. Must not be null.
     * @return The guessed restart activity class, or null if no suitable one is found
     */
    private fun guessRestartActivityClass(context: Context): Class<out Activity>? {
        var resolvedActivityClass: Class<out Activity>?

        //If action is defined, use that
        resolvedActivityClass = getRestartActivityClassWithIntentFilter(context)

        //Else, get the default launcher activity
        if (resolvedActivityClass == null) {
            resolvedActivityClass = getLauncherActivity(context)
        }

        return resolvedActivityClass
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
                //Should not happen, print it to the log!
                Log.e(
                    TAG,
                    "Failed when resolving the restart activity class via intent filter, stack trace follows!",
                    e
                )
            }

        }

        return null
    }

    private fun getLauncherActivity(context: Context): Class<out Activity>? {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        if (intent != null) {
            try {
                return cast(Class.forName(intent.component!!.className))
            } catch (e: ClassNotFoundException) {
                //Should not happen, print it to the log!
                Log.e(
                    TAG,
                    "Failed when resolving the restart activity class via getLaunchIntentForPackage, stack trace follows!",
                    e
                )
            }

        }

        return null
    }

    private fun guessErrorActivityClass(context: Context): Class<out Activity> {
        var resolvedActivityClass: Class<out Activity>?

        //If action is defined, use that
        resolvedActivityClass = getErrorActivityClassWithIntentFilter(context)

        //Else, get the default error activity
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
                //Should not happen, print it to the log!
                Log.e(TAG, "Failed when resolving the error activity class via intent filter, stack trace follows!", e)
            }

        }

        return null
    }

    private fun killCurrentProcess() {
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(10)
    }

    @SuppressLint("ApplySharedPref") //This must be done immediately since we are killing the app
    private fun setLastCrashTimestamp(context: Context, timestamp: Long) {
        context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE).edit()
            .putLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, timestamp).commit()
    }

    private fun getLastCrashTimestamp(context: Context): Long {
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
            .getLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, -1)
    }

    private fun hasCrashedInTheLastSeconds(context: Context): Boolean {
        val lastTimestamp = getLastCrashTimestamp(context)
        val currentTimestamp = Date().time

        return lastTimestamp <= currentTimestamp && currentTimestamp - lastTimestamp < config.minTimeBetweenCrashesMs
    }

    interface EventListener : Serializable {
        fun onLaunchErrorActivity()

        fun onRestartAppFromErrorActivity()

        fun onCloseAppFromErrorActivity()
    }
}
