package com.zhuzichu.orange.main.activity

import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.zhuzichu.mvvm.base.BaseActivity
import com.zhuzichu.mvvm.widget.FadeAnimator
import com.zhuzichu.orange.main.fragment.MainFragment
import com.zhuzichu.orange.view.plane.PlaneMaker
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.FragmentAnimator

class MainActivity : BaseActivity() {

    override fun setRootFragment(): ISupportFragment = MainFragment()

    companion object {
        fun start(context: Context, options: ActivityOptionsCompat? = null) {
            val intent = Intent()
            intent.setClass(context, MainActivity::class.java)
            if (options != null)
                context.startActivity(intent, options.toBundle())
            else
                context.startActivity(intent)
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置横向(和安卓4.x动画相同)
        return FadeAnimator()
    }


    override fun onDestroy() {
        super.onDestroy()
        AlibcTradeSDK.destory()
    }

    override fun onResume() {
        super.onResume()
        PlaneMaker.dismissLodingDialog()
    }
}
