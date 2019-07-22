package com.zhuzichu.orange.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zhuzichu.mvvm.base.BaseMainFragment
import com.zhuzichu.mvvm.base.DefaultFragmentPagerAdapter
import com.zhuzichu.mvvm.bus.RxBus
import com.zhuzichu.mvvm.utils.setupWithViewPager
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
    }

    override fun initView() {
        content.offscreenPageLimit = mFragments.size
        content.adapter = DefaultFragmentPagerAdapter(childFragmentManager, mFragments)
        initTabs()
        tabs.setupWithViewPager(viewPager = content)
    }

    private fun initTabs() {

    }


    private fun hideBottom() {
        tabs.visibility = View.GONE
    }

    private fun showBottom() {
        tabs.visibility = View.VISIBLE

    }

}