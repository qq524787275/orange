package com.zhuzichu.orange.login.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentPhoneLoginBinding
import com.zhuzichu.orange.login.viewmodel.PhoneLoginViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-29
 * Time: 16:12
 */
class PhoneLoginFragment : BaseFragment<FragmentPhoneLoginBinding, PhoneLoginViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_phone_login

    override fun bindVariableId(): Int = BR.viewModel
}