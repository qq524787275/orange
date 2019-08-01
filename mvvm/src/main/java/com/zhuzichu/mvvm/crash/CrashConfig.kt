package com.zhuzichu.mvvm.crash

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import java.io.Serializable

class CrashConfig : Serializable {

    var isEnable = true
    var isShowErrorDetails = true
    var isShowRestartButton = true
    var isTrackActivities = true
    private var minTimeBetweenCrashesMs = 3000

    private var restartActivityClass: Class<out Activity>? = null
    private var errorActivityClass: Class<out Activity>? = null
    private var eventListener: CrashClient.EventListener? = null
    private var errorDrawable: Int? = null

    private var backgroundMode = BACKGROUND_MODE_SHOW_CUSTOM

    @IntDef(BACKGROUND_MODE_CRASH, BACKGROUND_MODE_SHOW_CUSTOM, BACKGROUND_MODE_SILENT)
    @Retention(AnnotationRetention.SOURCE)
    private annotation class BackgroundMode

    @BackgroundMode
    fun getBackgroundMode(): Int {
        return backgroundMode
    }

    fun getMinTimeBetweenCrashesMs(): Int {
        return minTimeBetweenCrashesMs

    }

    fun getErrorActivityClass(): Class<out Activity>? {
        return errorActivityClass
    }

    fun getRestartActivityClass(): Class<out Activity>? {
        return restartActivityClass
    }

    fun setRestartActivityClass(restartActivityClass: Class<out Activity>?) {
        this.restartActivityClass = restartActivityClass
    }

    fun getEventListener(): CrashClient.EventListener? {
        return eventListener
    }

    fun setEventListener(eventListener: CrashClient.EventListener?) {
        this.eventListener = eventListener
    }

    @DrawableRes
    fun getErrorDrawable(): Int? {
        return errorDrawable
    }

    companion object {
        const val BACKGROUND_MODE_SILENT = 0
        const val BACKGROUND_MODE_SHOW_CUSTOM = 1
        const val BACKGROUND_MODE_CRASH = 2

        fun initDefault(config: CrashConfig) {
            CrashClient.setConifg(config)
        }

    }

    class Builder {
        var config: CrashConfig = CrashConfig()

        fun build(): CrashConfig {
            return config
        }

        fun enabled(isEnable: Boolean): Builder {
            config.isEnable = isEnable
            return this@Builder
        }

        fun showErrorDetails(isShowErrorDetails: Boolean): Builder {
            config.isShowErrorDetails = isShowErrorDetails
            return this@Builder
        }

        fun showRestartButton(isShowRestartButton: Boolean): Builder {
            config.isShowRestartButton = isShowRestartButton
            return this@Builder
        }

        fun trackActivities(isTrackActivities: Boolean): Builder {
            config.isTrackActivities = isTrackActivities
            return this@Builder
        }

        fun backgroundMode(backgroundModeSilent: Int): Builder {
            config.backgroundMode = backgroundModeSilent
            return this@Builder
        }

        fun minTimeBetweenCrashesMs(minTimeBetweenCrashesMs: Int): Builder {
            config.minTimeBetweenCrashesMs = minTimeBetweenCrashesMs
            return this@Builder
        }

        fun errorDrawable(errorDrawable: Int): Builder {
            config.errorDrawable = errorDrawable
            return this@Builder
        }

        fun restartActivity(restartActivityClass: Class<out Activity>): Builder {
            config.restartActivityClass = restartActivityClass
            return this@Builder
        }

        fun errorActivity(errorActivityClass: Class<out Activity>): Builder {
            config.errorActivityClass = errorActivityClass
            return this@Builder
        }

    }

}