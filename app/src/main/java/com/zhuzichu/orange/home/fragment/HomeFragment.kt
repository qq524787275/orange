package com.zhuzichu.orange.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.scwang.smartrefresh.layout.api.OnTwoLevelListener
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.base.BaseTopBarFragment
import com.zhuzichu.mvvm.bus.RxBus
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.mvvm.utils.helper.QMUIDeviceHelper
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import com.zhuzichu.mvvm.widget.GlideImageLoader
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.databinding.FragmentHomeBinding
import com.zhuzichu.orange.home.adpater.HomeFragmentPageAdapter
import com.zhuzichu.orange.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import com.zhuzichu.mvvm.view.banner.BannerConfig
import com.zhuzichu.mvvm.view.banner.Transformer.DepthPage
import com.zhuzichu.orange.R
import com.zhuzichu.orange.event.HomeEvent


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 13:27
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    val titles = listOf("榜单", "品牌", "快抢", "达人说")
    val fragments = listOf<Fragment>(RankingFragment(), TalentFragment(), BuyingFragment(), TalentFragment())
    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        initSearchBar()
        initRefresh()
        initBanner()
        viewpager.adapter = HomeFragmentPageAdapter(childFragmentManager, fragments)
        viewpager.offscreenPageLimit = titles.size
        initMagicIndicator(
            activity, titles, viewpager, indicator
        )
    }

    private fun initSearchBar() {
        val layoutParams = search_card.layoutParams as FrameLayout.LayoutParams
        layoutParams.topMargin = layoutParams.topMargin + QMUIStatusBarHelper.getStatusbarHeight(context)
    }

    private fun initRefresh() {
        refresh.setEnableLoadMore(false)
        refresh.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {
                search_layout.alpha = 1 - Math.min(percent, 1f)
                secondfloor.translationY = Math.min(
                    offset - secondfloor.height + search_layout.height,
                    refresh.layout.height - secondfloor.height
                )
                    .toFloat()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                RxBus.default.post(HomeEvent.OnHomeRefreshEvent())
                refreshLayout.finishRefresh(2000)
            }

            override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
                if (newState == RefreshState.TwoLevelReleased) {
                    //进入二层
                    RxBus.default.post(HomeEvent.OnTowLevelEvent(HomeEvent.ENTER_TOW_LEVEL))
                }

                if (newState == RefreshState.TwoLevelFinish) {
                    //退出二层
                    RxBus.default.post(HomeEvent.OnTowLevelEvent(HomeEvent.EXIT_TOW_LEVEL))
                }
            }

        })
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        _viewModel.loadBanner()
    }

    private fun initBanner() {
        val layoutParams = banner.layoutParams
        layoutParams.width = getScreenW() - 2 * dip2px(10f)
        layoutParams.height = layoutParams.width / (368 / 150)
        banner.setImageLoader(GlideImageLoader())
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
        //设置图片加载器
        banner.setImageLoader(GlideImageLoader())
        //设置banner动画效果
        banner.setBannerAnimation(DepthPage)
        //设置标题集合（当banner样式有显示title时）
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(1500)
        //设置指示器位置（当banner模式中有指示器时）
        //banner设置方法全部调用完毕时最后调用
    }

    @SuppressLint("CheckResult")
    override fun initViewObservable() {
        _viewModel.uc.onBannerLoadSuccess.observe(this, Observer {
            //设置图片集合
            banner.setImages(it.flatMap { item ->
                listOf(item.app_image)
            })
            banner.setBannerTitles(it.flatMap { item -> listOf(item.name) })
            banner.start()
        })

        RxBus.default.toObservable(HomeEvent.OnHomeRefreshEvent::class.java)
            .compose(bindToLifecycle())
            .subscribe {
                _viewModel.loadBanner()
            }
    }

}