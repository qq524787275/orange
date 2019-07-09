package com.zhuzichu.orange.main.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.bus.RxBus
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.view.magicindicator.ViewPagerHelper
import com.zhuzichu.mvvm.view.magicindicator.buildins.commonnavigator.CommonNavigator
import com.zhuzichu.mvvm.view.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import com.zhuzichu.mvvm.view.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import com.zhuzichu.mvvm.view.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import com.zhuzichu.mvvm.view.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentMainBinding
import com.zhuzichu.orange.event.HomeEvent
import com.zhuzichu.orange.find.fragment.FindFragment
import com.zhuzichu.orange.home.fragment.HomeFragment
import com.zhuzichu.orange.main.adapter.MainFragmentPageAdapter
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
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_main

    override fun bindVariableId(): Int = BR.viewModel
    private val commonNavigator by lazy {
        CommonNavigator(context)
    }
    private val mTitles = listOf("首页", "分类", "发现", "我的")
    private val mImageNormals =
        listOf(
            R.mipmap.main_tab_home_normal,
            R.mipmap.main_tab_sort_normal,
            R.mipmap.main_tab_find_normal,
            R.mipmap.main_tab_mine_normal
        )
    private val mImageSeleteds = listOf(
        R.mipmap.main_tab_home_selected,
        R.mipmap.main_tab_sort_selected,
        R.mipmap.main_tab_find_selected,
        R.mipmap.main_tab_mine_selected
    )

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

    override fun initViewObservable() {
        ColorGlobal.colorPrimary.observe(this, Observer {
            commonNavigator.adapter.notifyDataSetChanged()
        })
    }

    override fun initView() {
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int = mFragments.size
            override
            fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val commonPagerTitleView = CommonPagerTitleView(context)
                val customLayout =
                    LayoutInflater.from(context).inflate(R.layout.view_bottom_tab, null)
                val titleImg = customLayout.findViewById<View>(R.id.bottom_img) as ImageView
                val titleText = customLayout.findViewById<View>(R.id.bottom_text) as TextView
                titleImg.setImageResource(mImageNormals[index])
                titleText.text = mTitles[index]
                commonPagerTitleView.setContentView(customLayout)
                commonPagerTitleView.onPagerTitleChangeListener =
                    object : CommonPagerTitleView.OnPagerTitleChangeListener {
                        override fun onSelected(index: Int, totalCount: Int) {
                            val c = ColorGlobal.colorPrimary.value!!
                            titleText.setTextColor(c)
                            titleImg.setImageResource(mImageSeleteds[index])
                            titleImg.setColorFilter(c)
                        }

                        override fun onDeselected(index: Int, totalCount: Int) {
                            val c = ColorGlobal.textColorSeconday.get()!!
                            titleText.setTextColor(c)
                            titleImg.setImageResource(mImageNormals[index])
                            titleImg.setColorFilter(c)
                        }

                        override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
                            titleImg.scaleX = 1.3f + (0.8f - 1.3f) * leavePercent
                            titleImg.scaleY = 1.3f + (0.8f - 1.3f) * leavePercent
                        }

                        override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
                            titleImg.scaleX = 0.8f + (1.3f - 0.8f) * enterPercent
                            titleImg.scaleY = 0.8f + (1.3f - 0.8f) * enterPercent
                        }

                    }
                commonPagerTitleView.setOnClickListener { content.setCurrentItem(index, false) }
                return commonPagerTitleView
            }

            override fun getIndicator(context: Context?): IPagerIndicator? = null
        }
        content.offscreenPageLimit = mFragments.size
        content.adapter = MainFragmentPageAdapter(childFragmentManager, mFragments)

        bottom.navigator = commonNavigator
        ViewPagerHelper.bind(bottom, content)
    }

    @SuppressLint("CheckResult")
    override fun initVariable() {
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

    private fun hideBottom() {
        bottom.visibility = View.GONE
    }

    private fun showBottom() {
        bottom.visibility = View.VISIBLE

    }

}