package com.zhuzichu.orange.login.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentForgetBinding
import com.zhuzichu.orange.login.viewmodel.ForgetViewModel

class ForgetFragment : BaseFragment<FragmentForgetBinding, ForgetViewModel>() {

    override fun setLayoutId(): Int = R.layout.fragment_forget

    override fun bindVariableId(): Int = BR.viewModel
}
