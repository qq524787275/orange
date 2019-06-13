package com.zhuzichu.orange.home

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentHomeBinding

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 13:27
 */
class HomeFragmnet : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun bindVariableId(): Int = BR.viewModel

}