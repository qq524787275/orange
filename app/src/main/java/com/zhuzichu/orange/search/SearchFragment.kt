package com.zhuzichu.orange.search

import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.mvvm.utils.toast
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
    companion object {
        const val KEYWORD = "keyword"
    }

    private val keyWord: String by bindArgument(KEYWORD)


    override fun setLayoutId(): Int = R.layout.fragment_search

    override fun bindVariableId(): Int = BR.viewModel

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        mViewModel.searchShop(keyWord)
    }
}