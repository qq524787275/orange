package com.zhuzichu.orange.login.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentLoginBinding
import com.zhuzichu.orange.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-26
 * Time: 09:41
 */
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_login

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        super.initView()
        showSoftInput(username)
    }
}