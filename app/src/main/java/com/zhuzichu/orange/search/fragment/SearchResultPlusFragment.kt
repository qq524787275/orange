package com.zhuzichu.orange.search.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentSearchResultPlusBinding
import com.zhuzichu.orange.search.viewmodel.SearchResultPlusViewModel
import kotlinx.android.synthetic.main.fragment_search_result_plus.*

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-19
 * Time: 14:14
 */
class SearchResultPlusFragment : BaseTopbarBackFragment<FragmentSearchResultPlusBinding, SearchResultPlusViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_search_result_plus
    override fun bindVariableId(): Int = BR.viewModel

    companion object {
        const val KEYWORD = "KEYWORD"
    }

    private val keyWord: String by bindArgument(KEYWORD)

    override fun initView() {
        setTitle(keyWord)
        addRightIcon(R.mipmap.ic_top) {

        }
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        _viewModel.loadData(keyWord)
    }

    override fun initViewObservable() {
        _viewModel.uc.finishLoadmore.observe(this, Observer {
            refresh.finishLoadMore()
        })

        _viewModel.uc.finishRefreshing.observe(this, Observer {
            refresh.finishRefresh()
            refresh.setNoMoreData(false)
        })

        _viewModel.uc.finishLoadMoreWithNoMoreData.observe(this, Observer {
            refresh.finishLoadMore()
            refresh.setNoMoreData(true)
        })
    }
}