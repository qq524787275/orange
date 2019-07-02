package com.zhuzichu.orange.shopdetail.fragment

import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentShopDetailTwoBinding
import com.zhuzichu.orange.shopdetail.viewmodel.ShopDetailTwoViewModel

class ShopDetailTwoFragment(
    val itemid: String,
    val type: String
) : BaseFragment<FragmentShopDetailTwoBinding, ShopDetailTwoViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_shop_detail_two

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        _viewModel.showLoading()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.loadShopDetailDesc(itemid, type)
    }
}