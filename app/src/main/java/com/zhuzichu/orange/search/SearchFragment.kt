package com.zhuzichu.orange.search

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentSearchBinding

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 14:38
 */
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_search

    override fun bindVariableId(): Int = BR.viewModel
}