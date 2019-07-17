package com.zhuzichu.orange.shopdetail.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseFragment
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

    }

    override fun initViewObservable() {
        _viewModel.uc.onLoadDataCompletedEvent.observe(this, Observer {
            //设置图片集合
            if(!it.taobao_image.isNullOrBlank()){
                banner.setImages(it.taobao_image.split(","))
            }else{
                banner.setImages(listOf(it.itempic))
            }
            banner.start(lifecycle)
        })
    }

}