package com.zhuzichu.mvvm.widget

import android.view.animation.Interpolator
import kotlin.math.sin

class CycleInterpolator : Interpolator {
    private val mCycles = 0.5f
    override fun getInterpolation(input: Float): Float {
        return sin(2.0f * mCycles * Math.PI * input).toFloat()
    }
}