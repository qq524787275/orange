package com.zhuzichu.orange.find.fragment

import com.zhuzichu.mvvm.base.BaseTopBarFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentFindBinding
import com.zhuzichu.orange.find.viewmodel.FindViewModel

class FindFragment : BaseTopBarFragment<FragmentFindBinding, FindViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_find

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setTitle("发现")
    }
}