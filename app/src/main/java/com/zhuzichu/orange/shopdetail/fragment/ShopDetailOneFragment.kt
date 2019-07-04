package com.zhuzichu.orange.shopdetail.fragment

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.bumptech.glide.request.RequestOptions
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.global.glide.GlideApp
import com.zhuzichu.mvvm.view.banner.loader.ImageLoader
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentShopDetailOneBinding
import com.zhuzichu.orange.shopdetail.viewmodel.ShopDetailOneViewModel
import kotlinx.android.synthetic.main.fragment_shop_detail_one.*

class ShopDetailOneFragment(
    val itemid: String,
    val type: String
) : BaseFragment<FragmentShopDetailOneBinding, ShopDetailOneViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_shop_detail_one

    override fun bindVariableId(): Int = BR.viewModel

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.loadShopDetail(itemid)
    }

    override fun initView() {
        initBanner()
    }

    override fun initViewObservable() {
        _viewModel.uc.onLoadDataCompletedEvent.observe(this, Observer {
            //设置图片集合
            if(!it.taobao_image.isNullOrBlank()){
                banner.setImages(it.taobao_image.split(","))
            }else{
                banner.setImages(listOf(it.itempic))
            }
            banner.start()
        })
    }

    private fun initBanner() {
        banner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                GlideApp.with(imageView)
                    .load(path)
                    .apply(RequestOptions().placeholder(R.mipmap.ic_silent_plate_mix).error(R.mipmap.ic_silent_plate_mix))
                    .into(imageView)
            }

        })
        banner.isAutoPlay(true)
        banner.setDelayTime(1500)
    }
}