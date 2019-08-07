package com.zhuzichu.orange.flutter

import com.zhuzichu.mvvm.base.BaseActivity
import com.zhuzichu.orange.flutter.base.BaseFlutterFragment
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-07
 * Time: 15:23
 */
class BallGameFlutterActivity : BaseActivity() {
    override fun setRootFragment(): ISupportFragment = BallGameFragment()

    class BallGameFragment : BaseFlutterFragment() {
        override fun setRoute(): String = "game_ball"
    }
}