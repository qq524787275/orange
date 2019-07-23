package com.zhuzichu.orange.shopdetail.fragment

import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.view.banner.ScaleLayoutManager
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

    private fun initBanner() {
        val scaleLayoutManager = ScaleLayoutManager(_activity, 1)
        scaleLayoutManager.minScale = 0.9f
        banner.layoutManager = scaleLayoutManager
    }

    override fun initViewObservable() {

    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        banner.start()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        banner.pause()
    }
}