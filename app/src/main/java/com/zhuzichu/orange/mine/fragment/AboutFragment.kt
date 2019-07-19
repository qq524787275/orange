package com.zhuzichu.orange.mine.fragment

import com.zhuzichu.orange.flutter.base.BaseFlutterFragment

class AboutFragment : BaseFlutterFragment() {
    override fun setRoute(): String ="about"

    override fun setParam(): Map<String, Any>?= mapOf(
        "hha" to 1
    )
}