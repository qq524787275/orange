package com.zhuzichu.orange.find.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentFindTwoBinding
import com.zhuzichu.orange.find.viewmodel.FindTwoViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 10:00
 */
class FindTwoFragment : BaseFragment<FragmentFindTwoBinding, FindTwoViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_find_two

    override fun bindVariableId(): Int = BR.viewModel
}