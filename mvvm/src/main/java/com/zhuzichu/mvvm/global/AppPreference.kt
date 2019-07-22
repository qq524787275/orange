package com.zhuzichu.mvvm.global

import android.content.Context
import android.content.SharedPreferences
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.utils.toColorById
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-19
 * Time: 15:04
 */
open class AppPreference {

    companion object {
        private const val PREFERENCE_NAME = "orange-preference"
    }

    private val preferences: SharedPreferences by lazy {
        AppGlobal.context.getSharedPreferences(
            PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
    }

    var isDark by PreferenceDelegates.boolean(defaultValue = false)
    var colorPrimary by PreferenceDelegates.int(defaultValue = R.color.colorPrimary.toColorById())


    private object PreferenceDelegates {

        fun int(defaultValue: Int = 0) = object : ReadWriteProperty<AppPreference, Int> {

            override fun getValue(thisRef: AppPreference, property: KProperty<*>): Int {
                return thisRef.preferences.getInt(property.name, defaultValue)
            }

            override fun setValue(thisRef: AppPreference, property: KProperty<*>, value: Int) {
                thisRef.preferences.edit().putInt(property.name, value).apply()
            }
        }

        fun long(defaultValue: Long = 0L) = object : ReadWriteProperty<AppPreference, Long> {

            override fun getValue(thisRef: AppPreference, property: KProperty<*>): Long {
                return thisRef.preferences.getLong(property.name, defaultValue)
            }

            override fun setValue(thisRef: AppPreference, property: KProperty<*>, value: Long) {
                thisRef.preferences.edit().putLong(property.name, value).apply()
            }
        }

        fun boolean(defaultValue: Boolean = false) = object : ReadWriteProperty<AppPreference, Boolean> {
            override fun getValue(thisRef: AppPreference, property: KProperty<*>): Boolean {
                return thisRef.preferences.getBoolean(property.name, defaultValue)
            }

            override fun setValue(thisRef: AppPreference, property: KProperty<*>, value: Boolean) {
                thisRef.preferences.edit().putBoolean(property.name, value).apply()
            }
        }

        fun float(defaultValue: Float = 0.0f) = object : ReadWriteProperty<AppPreference, Float> {
            override fun getValue(thisRef: AppPreference, property: KProperty<*>): Float {
                return thisRef.preferences.getFloat(property.name, defaultValue)
            }

            override fun setValue(thisRef: AppPreference, property: KProperty<*>, value: Float) {
                thisRef.preferences.edit().putFloat(property.name, value).apply()
            }
        }

        fun string(defaultValue: String? = null) = object : ReadWriteProperty<AppPreference, String?> {
            override fun getValue(thisRef: AppPreference, property: KProperty<*>): String? {
                return thisRef.preferences.getString(property.name, defaultValue)
            }

            override fun setValue(thisRef: AppPreference, property: KProperty<*>, value: String?) {
                thisRef.preferences.edit().putString(property.name, value).apply()
            }
        }

        fun setString(defaultValue: Set<String>? = null) =
            object : ReadWriteProperty<AppPreference, Set<String>?> {
                override fun getValue(thisRef: AppPreference, property: KProperty<*>): Set<String>? {
                    return thisRef.preferences.getStringSet(property.name, defaultValue)
                }

                override fun setValue(thisRef: AppPreference, property: KProperty<*>, value: Set<String>?) {
                    thisRef.preferences.edit().putStringSet(property.name, value).apply()
                }
            }
    }
}