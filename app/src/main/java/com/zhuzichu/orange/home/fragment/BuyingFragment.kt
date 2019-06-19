package com.zhuzichu.orange.home.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentBuyingBinding
import com.zhuzichu.orange.home.viewmodel.BuyingViewModel

class BuyingFragment : BaseFragment<FragmentBuyingBinding, BuyingViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_buying

    override fun bindVariableId(): Int = BR.viewModel
}