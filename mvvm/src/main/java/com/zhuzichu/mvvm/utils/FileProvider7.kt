package com.zhuzichu.mvvm.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-13
 * Time: 17:27
 */

object FileProvider7 {

    private fun getUriForFile(context: Context, file: File): Uri {
        return if (Build.VERSION.SDK_INT >= 24) {
            getUriForFile24(context, file)
//            Uri.fromFile(file)
        } else {
            Uri.fromFile(file)
        }
    }


    private fun getUriForFile24(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            context.packageName.plus(".FileProvider"),
            file
        )
    }


    fun setIntentDataAndType(
        context: Context,
        intent: Intent,
        type: String,
        file: File,
        writeAble: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(context, file), type)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type)
        }
    }


    fun setIntentData(
        context: Context,
        intent: Intent,
        file: File,
        writeAble: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.data = getUriForFile(context, file)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
        } else {
            intent.data = Uri.fromFile(file)
        }
    }


    fun grantPermissions(context: Context, intent: Intent, uri: Uri, writeAble: Boolean) {

        var flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        if (writeAble) {
            flag = flag or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        }
        intent.addFlags(flag)
        val resInfoList = context.packageManager
            .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            context.grantUriPermission(packageName, uri, flag)
        }
    }


}