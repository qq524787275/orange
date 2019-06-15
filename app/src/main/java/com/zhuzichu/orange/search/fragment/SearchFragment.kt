package com.zhuzichu.orange.search.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentSearchBinding
import com.zhuzichu.orange.search.viewmodel.SearchViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_search

    override fun bindVariableId(): Int = BR.viewModel
}