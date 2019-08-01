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

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import java.io.Serializable
import java.lang.reflect.Modifier


class CaocConfig : Serializable {

    @get:BackgroundMode
    var backgroundMode = BACKGROUND_MODE_SHOW_CUSTOM
    var isEnabled = true
    var isShowErrorDetails = true
    var isShowRestartButton = true
    var isTrackActivities = false
    var minTimeBetweenCrashesMs = 3000
    @get:DrawableRes
    var errorDrawable: Int? = null
    var errorActivityClass: Class<out Activity>? = null
    var restartActivityClass: Class<out Activity>? = null
    var eventListener: CustomActivityOnCrash.EventListener? = null

    @IntDef(BACKGROUND_MODE_CRASH, BACKGROUND_MODE_SHOW_CUSTOM, BACKGROUND_MODE_SILENT)
    @Retention(AnnotationRetention.SOURCE)
    private annotation class BackgroundMode//I hate empty blocks

    class Builder {
        private var config: CaocConfig? = null


        fun backgroundMode(@BackgroundMode backgroundMode: Int): Builder {
            config!!.backgroundMode = backgroundMode
            return this
        }


        fun enabled(enabled: Boolean): Builder {
            config!!.isEnabled = enabled
            return this
        }

        fun showErrorDetails(showErrorDetails: Boolean): Builder {
            config!!.isShowErrorDetails = showErrorDetails
            return this
        }

        fun showRestartButton(showRestartButton: Boolean): Builder {
            config!!.isShowRestartButton = showRestartButton
            return this
        }

        fun trackActivities(trackActivities: Boolean): Builder {
            config!!.isTrackActivities = trackActivities
            return this
        }


        fun minTimeBetweenCrashesMs(minTimeBetweenCrashesMs: Int): Builder {
            config!!.minTimeBetweenCrashesMs = minTimeBetweenCrashesMs
            return this
        }


        fun errorDrawable(@DrawableRes errorDrawable: Int?): Builder {
            config!!.errorDrawable = errorDrawable
            return this
        }


        fun errorActivity(errorActivityClass: Class<out Activity>?): Builder {
            config!!.errorActivityClass = errorActivityClass
            return this
        }


        fun restartActivity(restartActivityClass: Class<out Activity>?): Builder {
            config!!.restartActivityClass = restartActivityClass
            return this
        }


        fun eventListener(eventListener: CustomActivityOnCrash.EventListener?): Builder {
            if (eventListener != null && eventListener.javaClass.enclosingClass != null && !Modifier.isStatic(
                    eventListener.javaClass.modifiers
                )
            ) {
                throw IllegalArgumentException("The event listener cannot be an inner or anonymous class, because it will need to be serialized. Change it to a class of its own, or make it a static inner class.")
            } else {
                config!!.eventListener = eventListener
            }
            return this
        }

        fun get(): CaocConfig? {
            return config
        }

        fun apply() {
            CustomActivityOnCrash.config = config!!
        }

        companion object {

            fun create(): Builder {
                val builder = Builder()
                val currentConfig = CustomActivityOnCrash.config

                val config = CaocConfig()
                config.backgroundMode = currentConfig.backgroundMode
                config.isEnabled = currentConfig.isEnabled
                config.isShowErrorDetails = currentConfig.isShowErrorDetails
                config.isShowRestartButton = currentConfig.isShowRestartButton
                config.isTrackActivities = currentConfig.isTrackActivities
                config.minTimeBetweenCrashesMs = currentConfig.minTimeBetweenCrashesMs
                config.errorDrawable = currentConfig.errorDrawable
                config.errorActivityClass = currentConfig.errorActivityClass
                config.restartActivityClass = currentConfig.restartActivityClass
                config.eventListener = currentConfig.eventListener

                builder.config = config

                return builder
            }
        }
    }

    companion object {
        const val BACKGROUND_MODE_SILENT = 0
        const val BACKGROUND_MODE_SHOW_CUSTOM = 1
        const val BACKGROUND_MODE_CRASH = 2
    }


}
