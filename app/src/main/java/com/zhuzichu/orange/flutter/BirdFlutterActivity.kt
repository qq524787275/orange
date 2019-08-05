package com.zhuzichu.orange.flutter

import com.zhuzichu.mvvm.base.BaseActivity
import com.zhuzichu.orange.flutter.base.BaseFlutterFragment
import me.yokeyword.fragmentation.ISupportFragment

class BirdFlutterActivity : BaseActivity() {
    override fun setRootFragment(): ISupportFragment = BirdFlutterFragmnet()

    class BirdFlutterFragmnet : BaseFlutterFragment() {
        override fun setRoute(): String = "game_bird"
    }
}
