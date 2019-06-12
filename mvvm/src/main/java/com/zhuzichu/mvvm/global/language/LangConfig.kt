package com.zhuzichu.mvvm.global.language;

import androidx.databinding.ObservableField

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 15:45
 */
object LangConfig {
    val loading: ObservableField<String> = ObservableField()

    fun initLang(lang: Lang) {
        loading.set(lang.loading)
    }
}