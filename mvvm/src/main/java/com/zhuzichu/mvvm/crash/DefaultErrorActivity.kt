package com.zhuzichu.mvvm.crash

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.utils.sp2px

class DefaultErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_error)

        val restartButton = findViewById<View>(R.id.error_activity_restart_button) as Button

        val config = CrashClient.getConfigFromIntent(intent)

        if (config.isShowRestartButton && config.getRestartActivityClass() != null) {
            restartButton.setText(R.string.error_activity_restart_app)
            restartButton.setOnClickListener {
                CrashClient.restartApplication(
                    this@DefaultErrorActivity,
                    config
                )
            }
        } else {
            restartButton.setOnClickListener {
                CrashClient.closeApplication(
                    this@DefaultErrorActivity,
                    config
                )
            }
        }

        val moreInfoButton = findViewById<View>(R.id.error_activity_more_info_button) as Button

        if (config.isShowErrorDetails) {
            moreInfoButton.setOnClickListener {
                //We retrieve all the error data and show it

                val dialog = AlertDialog.Builder(this@DefaultErrorActivity)
                    .setTitle(R.string.error_activity_error_details_title)
                    .setMessage(CrashClient.getAllErrorDetailsFromIntent(this@DefaultErrorActivity, intent))
                    .setPositiveButton(R.string.error_activity_error_details_close, null)
                    .setNeutralButton(
                        R.string.error_activity_error_details_copy
                    ) { _, _ ->
                        copyErrorToClipboard()
                        Toast.makeText(
                            this@DefaultErrorActivity,
                            R.string.error_activity_error_details_copied,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .show()
                val textView = dialog.findViewById(android.R.id.message) as TextView
                textView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    sp2px(12f).toFloat()
                )
            }
        } else {
            moreInfoButton.visibility = View.GONE
        }

        val defaultErrorActivityDrawableId = config.getErrorDrawable()
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
        val errorInformation = CrashClient.getAllErrorDetailsFromIntent(this@DefaultErrorActivity, intent)

        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(
            getString(R.string.error_activity_error_details_clipboard_label),
            errorInformation
        )
        clipboard.setPrimaryClip(clip)
    }
}