package com.zhuzichu.orange.search.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseTopBarFragment
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentSearchResultBinding
import com.zhuzichu.orange.search.viewmodel.SearchResultViewModel
import kotlinx.android.synthetic.main.fragment_search_result.*
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 14:38
 */
class SearchResultFragment : BaseTopBarFragment<FragmentSearchResultBinding, SearchResultViewModel>() {
    companion object {
        const val KEYWORD = "keyword"
    }

    private val keyWord: String by bindArgument(KEYWORD)

    override fun setLayoutId(): Int = R.layout.fragment_search_result

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        sticky_layout.bindStickyHeader(sticky_header)
        sticky_layout.registerTypePinnedHeader(
            R.layout.item_search_layout
        ) { _, _ ->
            true
        }
        setTitle(keyWord)
        addRightIcon(R.mipmap.ic_search, View.OnClickListener {
            mViewModel.startFragment(SearchFragment(), launchMode = ISupportFragment.SINGLETASK)
        })
        mViewModel.showLoading()
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        mViewModel.searchShop(keyWord)
    }

    override fun initViewObservable() {
        mViewModel.uc.finishLoadmore.observe(this, Observer {
            refresh.finishLoadMore()
        })

        mViewModel.uc.finishRefreshing.observe(this, Observer {
            refresh.finishRefresh()
            refresh.setNoMoreData(false)
        })

        mViewModel.uc.finishLoadMoreWithNoMoreData.observe(this, Observer {
            refresh.finishLoadMore()
            refresh.setNoMoreData(true)
        })
    }
}