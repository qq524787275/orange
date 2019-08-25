package com.zhuzichu.orange.search.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentSearchResultBinding
import com.zhuzichu.orange.search.viewmodel.SearchResultViewModel
import kotlinx.android.synthetic.main.fragment_search_result.*

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-19
 * Time: 14:14
 */
class SearchResultFragment : BaseTopbarBackFragment<FragmentSearchResultBinding, SearchResultViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_search_result
    override fun bindVariableId(): Int = BR.viewModel

    companion object {
        const val KEYWORD = "KEYWORD"
    }

    private val keyWord: String by bindArgument(KEYWORD)

    override fun initView() {
        setTitle(keyWord)
        addRightIcon(R.mipmap.ic_top) {
            (recycler.layoutManager as GridLayoutManager).scrollToPositionWithOffset(0, 0)
        }
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        _viewModel.keyword = keyWord
        _viewModel.loadData()
    }

    override fun initViewObservable() {
        _viewModel.uc.finishLoadmore.observe(this, Observer {
            refresh.finishLoadMore()
        })

        _viewModel.uc.finishRefreshing.observe(this, Observer {
            refresh.finishRefresh()
            refresh.setNoMoreData(false)
            post {
                (recycler.layoutManager as GridLayoutManager).scrollToPositionWithOffset(0, 0)
            }
        })

        _viewModel.uc.finishLoadMoreWithNoMoreData.observe(this, Observer {
            refresh.finishLoadMore()
            refresh.setNoMoreData(true)
        })
    }
}