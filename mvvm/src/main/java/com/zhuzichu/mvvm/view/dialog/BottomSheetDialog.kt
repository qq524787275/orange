package com.zhuzichu.mvvm.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.view.animation.Animation.AnimationListener
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.utils.helper.QMUIDisplayHelper

class BottomSheetDialog(context: Context) : Dialog(context, R.style.BottomSheet) {
    // 持有 ContentView，为了做动画
    private lateinit var contentView: View
    private var mIsAnimating = false
    private val mOnBottomSheetShowListener: OnBottomSheetShowListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.decorView.setPadding(0, 0, 0, 0)
        // 在底部，宽度撑满
        val params = window!!.attributes
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.BOTTOM or Gravity.CENTER
        val screenWidth = QMUIDisplayHelper.getScreenWidth(context)
        val screenHeight = QMUIDisplayHelper.getScreenHeight(context)
        params.width = if (screenWidth < screenHeight) screenWidth else screenHeight
        window!!.attributes = params
        setCanceledOnTouchOutside(true)
    }

    override fun setContentView(layoutResID: Int) {
        contentView = LayoutInflater.from(context).inflate(layoutResID, null)
        super.setContentView(contentView)
    }

    override fun setContentView(
        view: View,
        params: ViewGroup.LayoutParams?
    ) {
        contentView = view
        super.setContentView(view, params)
    }

    override fun setContentView(view: View) {
        contentView = view
        super.setContentView(view)
    }

    /**
     * BottomSheet升起动画
     */
    private fun animateUp() {
        val translate = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            1f,
            Animation.RELATIVE_TO_SELF,
            0f
        )
        val alpha = AlphaAnimation(0f, 1f)
        val set = AnimationSet(true)
        set.addAnimation(translate)
        set.addAnimation(alpha)
        set.interpolator = DecelerateInterpolator()
        set.duration = mAnimationDuration.toLong()
        set.fillAfter = true
        contentView.startAnimation(set)
    }

    /**
     * BottomSheet降下动画
     */
    private fun animateDown() {
        val translate = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            1f
        )
        val alpha = AlphaAnimation(1f, 0f)
        val set = AnimationSet(true)
        set.addAnimation(translate)
        set.addAnimation(alpha)
        set.interpolator = DecelerateInterpolator()
        set.duration = mAnimationDuration.toLong()
        set.fillAfter = true
        set.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                mIsAnimating = true
            }

            override fun onAnimationEnd(animation: Animation?) {
                mIsAnimating = false
                /**
                 * Bugfix： Attempting to destroy the window while drawing!
                 */
                contentView.post {
                    // java.lang.IllegalArgumentException: View=com.android.internal.policy.PhoneWindow$DecorView{22dbf5b V.E...... R......D 0,0-1080,1083} not attached to window manager
                    // 在dismiss的时候可能已经detach了，简单try-catch一下

                    try {
                        super@BottomSheetDialog.dismiss()
                    } catch (e: Exception) {
                    }
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        contentView.startAnimation(set)
    }

    override fun show() {
        super.show()
        animateUp()
        mOnBottomSheetShowListener?.onShow()
    }

    override fun dismiss() {
        if (mIsAnimating) {
            return
        }
        animateDown()
    }

    interface OnBottomSheetShowListener {
        fun onShow()
    }

    companion object {
        // 动画时长
        private const val mAnimationDuration = 200
    }
}