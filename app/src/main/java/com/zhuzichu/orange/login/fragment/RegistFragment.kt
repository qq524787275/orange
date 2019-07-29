package com.zhuzichu.orange.login.fragment

import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.utils.toast
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

    override fun initViewObservable() {
        super.initViewObservable()
        _viewModel.uc.onClickCodeEvent.observe(this, Observer {
            timeButton.start(this)
        })
    }
}