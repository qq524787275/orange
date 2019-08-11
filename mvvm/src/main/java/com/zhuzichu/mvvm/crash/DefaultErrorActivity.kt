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
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.utils.toStringById
import com.zhuzichu.mvvm.utils.toast


class DefaultErrorActivity : AppCompatActivity() {

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val a = obtainStyledAttributes(R.styleable.AppCompatTheme)
        if (!a.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            setTheme(R.style.Theme_AppCompat_Light_DarkActionBar)
        }
        a.recycle()
        setContentView(R.layout.activity_default_error)
        val restartButton = findViewById<View>(R.id.error_activity_restart_button) as Button
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        if (config.isShowRestartButton && config.restartActivityClass != null) {
            restartButton.setText(R.string.error_activity_restart_app)
            restartButton.setOnClickListener {
                CustomActivityOnCrash.restartApplication(
                    this@DefaultErrorActivity,
                    config
                )
            }
        } else {
            restartButton.setOnClickListener {
                CustomActivityOnCrash.closeApplication(
                    this@DefaultErrorActivity,
                    config
                )
            }
        }
        val moreInfoButton = findViewById<View>(R.id.error_activity_more_info_button) as Button
        if (config.isShowErrorDetails) {
            moreInfoButton.setOnClickListener {

                MaterialDialog(this, BottomSheet()).show {
                    title(R.string.error_activity_error_details_title)
                    message(
                        text = CustomActivityOnCrash.getAllErrorDetailsFromIntent(
                            this@DefaultErrorActivity,
                            intent
                        )
                    )
                    positiveButton(R.string.error_activity_error_details_close)
                    negativeButton(R.string.error_activity_error_details_copy) {
                        copyErrorToClipboard()
                        R.string.error_activity_error_details_copied.toStringById().toast()
                    }
                }
            }
        } else {
            moreInfoButton.visibility = View.GONE
        }
        val defaultErrorActivityDrawableId = config.errorDrawable
        val errorImageView = findViewById<View>(R.id.error_activity_image) as ImageView
        if (defaultErrorActivityDrawableId != null) {
            errorImageView.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    defaultErrorActivityDrawableId,
                    theme
                )
            )
        }
    }

    private fun copyErrorToClipboard() {
        val errorInformation = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this@DefaultErrorActivity, intent)
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip =
            ClipData.newPlainText(getString(R.string.error_activity_error_details_clipboard_label), errorInformation)
        clipboard.setPrimaryClip(clip)
    }
}