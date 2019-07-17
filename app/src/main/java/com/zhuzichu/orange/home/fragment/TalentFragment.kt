package com.zhuzichu.orange.home.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.view.banner.BannerConfig
import com.zhuzichu.mvvm.view.banner.Transformer.DepthPage
import com.zhuzichu.mvvm.widget.GlideImageLoader
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentTalentBinding
import com.zhuzichu.orange.home.viewmodel.TalentViewModel
import kotlinx.android.synthetic.main.fragment_talent.*

class TalentFragment : BaseFragment<FragmentTalentBinding, TalentViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_talent

    override fun bindVariableId(): Int = BR.viewModel


    override fun initView() {
        initBanner()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.loadData()
    }

    override fun initViewObservable() {

        _viewModel.uc.onLoadDataSuccess.observe(this, Observer {
            nice_banner.setImages(
                it.map { item ->
                    item.app_image
                }
            )
            nice_banner.start(lifecycle)

            //设置图片集合
            banner.setImages(it.map { item ->
                item.app_image
            })
            banner.setBannerTitles(it.map { item ->
                item.name
            })
            banner.start()
        })
    }

    private fun initBanner() {
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
}