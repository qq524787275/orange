package com.zhuzichu.orange.home.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentBrandBinding
import com.zhuzichu.orange.home.viewmodel.BrandViewModel

class BrandFragment : BaseFragment<FragmentBrandBinding, BrandViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_brand

    override fun bindVariableId(): Int = BR.viewModel
}