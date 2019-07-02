package com.zhuzichu.orange.shopdetail.fragment

import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentShopDetailOneBinding
import com.zhuzichu.orange.shopdetail.viewmodel.ShopDetailOneViewModel

class ShopDetailOneFragment(
    val itemid: String,
    val type: String
) : BaseFragment<FragmentShopDetailOneBinding, ShopDetailOneViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_shop_detail_one

    override fun bindVariableId(): Int = BR.viewModel

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.loadShopDetail(itemid)
    }
}