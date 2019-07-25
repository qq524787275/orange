package com.zhuzichu.orange.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.bus.RxBus
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import com.zhuzichu.mvvm.view.banner.ScaleLayoutManager
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentHomeBinding
import com.zhuzichu.orange.event.HomeEvent
import com.zhuzichu.orange.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.min


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 13:27
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        initSearchBar()
        initRefresh()
        initBanner()
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
                search_layout.alpha = 1 - min(percent, 1f)
                secondfloor.translationY =
                    min(offset - secondfloor.height + search_layout.height, refresh.layout.height - secondfloor.height)
                        .toFloat()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                _viewModel.loadHomeData()
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
        _viewModel.loadHomeData()
    }

    private fun initBanner() {
        val scaleLayoutManager = ScaleLayoutManager(_activity, -dip2px(5f))
        scaleLayoutManager.minScale = 0.8f
        banner.layoutManager = scaleLayoutManager

    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        banner.start()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        banner.pause()
    }

    @SuppressLint("CheckResult")
    override fun initViewObservable() {
        _viewModel.uc.finishLoadmore.observe(this, Observer {
            refresh.finishLoadMore()
        })

        _viewModel.uc.finishRefreshing.observe(this, Observer {
            refresh.finishRefresh()
            refresh.setNoMoreData(false)
            banner.post {
                dots.attachRecyclerView(banner)
            }
        })

        _viewModel.uc.finishLoadMoreWithNoMoreData.observe(this, Observer {
            refresh.finishLoadMore()
            refresh.setNoMoreData(true)
        })

    }
}