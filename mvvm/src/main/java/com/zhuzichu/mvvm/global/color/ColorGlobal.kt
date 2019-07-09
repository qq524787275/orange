package com.zhuzichu.mvvm.global.color

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.utils.toColorById

object ColorGlobal {
    val colorPrimary: MutableLiveData<Int> = MutableLiveData()
    val windowBackgroud: ObservableField<Int> = ObservableField()
    val textColorPrimary: ObservableField<Int> = ObservableField()
    val textColorSeconday: ObservableField<Int> = ObservableField()
    val hintColor: ObservableField<Int> = ObservableField()
    val bottomBackgroud: ObservableField<Int> = ObservableField()

    fun initColor(isDark: Boolean = false) {
        colorPrimary.value = R.color.colorPrimary.toColorById()
        if (isDark) {
            windowBackgroud.set(R.color.colorBackgroundDark.toColorById())
            textColorPrimary.set(R.color.colorPrimaryTextDark.toColorById())
            textColorSeconday.set(R.color.colorSecondTextDark.toColorById())
            hintColor.set(R.color.colorHintDark.toColorById())
            bottomBackgroud.set(R.color.black.toColorById())
        } else {
            windowBackgroud.set(R.color.colorBackground.toColorById())
            textColorPrimary.set(R.color.colorPrimaryText.toColorById())
            textColorSeconday.set(R.color.colorSecondText.toColorById())
            hintColor.set(R.color.colorHint.toColorById())
            bottomBackgroud.set(R.color.white.toColorById())
        }
    }
}