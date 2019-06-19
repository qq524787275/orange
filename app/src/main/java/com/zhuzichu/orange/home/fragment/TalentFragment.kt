package com.zhuzichu.orange.home.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentTalentBinding
import com.zhuzichu.orange.home.viewmodel.TalentViewModel

class TalentFragment : BaseFragment<FragmentTalentBinding, TalentViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_talent

    override fun bindVariableId(): Int = BR.viewModel
}