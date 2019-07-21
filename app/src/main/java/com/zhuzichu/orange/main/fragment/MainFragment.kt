package com.zhuzichu.orange.main.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zhuzichu.mvvm.base.BaseMainFragment
import com.zhuzichu.mvvm.base.DefaultFragmentPagerAdapter
import com.zhuzichu.mvvm.bus.RxBus
import com.zhuzichu.mvvm.view.navigationtabbar.ntb.NavigationTabBar
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentMainBinding
import com.zhuzichu.orange.event.HomeEvent
import com.zhuzichu.orange.find.fragment.FindFragment
import com.zhuzichu.orange.home.fragment.HomeFragment
import com.zhuzichu.orange.main.viewmodel.MainViewModel
import com.zhuzichu.orange.mine.fragment.MineFragment
import com.zhuzichu.orange.sort.fragment.SortFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.concurrent.TimeUnit


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 15:20
 */
class MainFragment : BaseMainFragment<FragmentMainBinding, MainViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_main

    override fun bindVariableId(): Int = BR.viewModel

    private val mFragments = listOf<Fragment>(
        HomeFragment(),
        SortFragment(),
        FindFragment(),
        MineFragment()
    )

    override fun onNewBundle(args: Bundle) {
        super.onNewBundle(args)
        content.setCurrentItem(0, false)
    }

    @SuppressLint("CheckResult")
    override fun initViewObservable() {
        RxBus.default.toObservable(HomeEvent.OnTowLevelEvent::class.java)
            .delay(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(bindToLifecycle())
            .subscribe {
                if (it.state == HomeEvent.EXIT_TOW_LEVEL) {
                    showBottom()
                }

                if (it.state == HomeEvent.ENTER_TOW_LEVEL) {
                    hideBottom()
                }
            }
    }

    override fun initView() {
        content.offscreenPageLimit = mFragments.size
        content.adapter = DefaultFragmentPagerAdapter(childFragmentManager, mFragments)
        initTabs()
        content.post {
            val layout = content.layoutParams
            if (layout is FrameLayout.LayoutParams) {
                layout.bottomMargin = (layout.bottomMargin - tabs.badgeMargin).toInt()
            }
        }
    }

    private fun initTabs() {

        val colors: Array<String> = resources.getStringArray(R.array.default_preview)
        val models = listOf<NavigationTabBar.Model>(
            NavigationTabBar.Model.Builder(
                ContextCompat.getDrawable(_activity, R.drawable.main_tab_home),
                Color.parseColor(colors[0])
            )
                .selectedIcon(
                    ContextCompat.getDrawable(_activity, R.drawable.main_tab_home_selected)
                )
                .title("首页")
                .badgeTitle("Hot")
                .build().apply {
                    showBadge()
                }
            ,
            NavigationTabBar.Model.Builder(
                ContextCompat.getDrawable(_activity, R.drawable.main_tab_sort),
                Color.parseColor(colors[1])
            )
                .title("分类")
                .build()
            ,
            NavigationTabBar.Model.Builder(
                ContextCompat.getDrawable(_activity, R.drawable.main_tab_find),
                Color.parseColor(colors[2])
            )
                .title("发现")
                .build()
            ,
            NavigationTabBar.Model.Builder(
                ContextCompat.getDrawable(_activity, R.drawable.main_tab_mine),
                Color.parseColor(colors[3])
            )
                .title("我的")
                .build()
        )

        tabs.models = models
        tabs.setViewPager(content)
    }


    private fun hideBottom() {
        tabs.visibility = View.GONE
    }

    private fun showBottom() {
        tabs.visibility = View.VISIBLE

    }

}