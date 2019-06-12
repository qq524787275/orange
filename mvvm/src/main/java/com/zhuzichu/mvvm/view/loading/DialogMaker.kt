package com.zhuzichu.mvvm.view.loading

import android.content.Context
import android.content.DialogInterface
import java.lang.ref.WeakReference

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-11
 * Time: 18:53
 */
object DialogMaker {

    private var sProgressDialogRef: WeakReference<LoadingDialog>? = null

    val isShowing: Boolean
        get() {
            val dialog = dialog
            return dialog != null && dialog.isShowing
        }

    private val dialog: LoadingDialog?
        get() = if (sProgressDialogRef == null) null else sProgressDialogRef!!.get()


    fun showLoadingDialog(
        context: Context,
        canCancelable: Boolean = false,
        listener: DialogInterface.OnCancelListener? = null
    ): LoadingDialog {
        var dialog = dialog
        if (dialog != null && dialog.context !== context) {
            dismissLodingDialog()
            dialog = null
        }
        if (dialog == null) {
            dialog = LoadingDialog(context)
            sProgressDialogRef = WeakReference(dialog)
        }
        dialog.setCancelable(canCancelable)
        dialog.show()
        return dialog
    }

    fun dismissLodingDialog() {
        val dialog = dialog ?: return
        sProgressDialogRef!!.clear()
        if (dialog.isShowing) {
            try {
                dialog.dismiss()
            } catch (e: Exception) {
            }
        }
    }
}
