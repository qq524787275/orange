package com.zhuzichu.orange.login.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentRegistBinding
import com.zhuzichu.orange.login.viewmodel.RegistViewModel
import kotlinx.android.synthetic.main.fragment_regist.*

class RegistFragment : BaseFragment<FragmentRegistBinding, RegistViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_regist

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        super.initView()
        showSoftInput(username)
    }
}