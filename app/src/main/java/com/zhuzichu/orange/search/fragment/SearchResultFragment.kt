package com.zhuzichu.orange.search.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Fade
import com.zhuzichu.mvvm.App
import com.zhuzichu.mvvm.base.BaseTopBarFragment
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.mvvm.widget.DetailTransition
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
        mViewModel.showLoading()
        sticky_layout.bindStickyHeader(sticky_header)
        sticky_layout.registerTypePinnedHeader(
            R.layout.item_search_layout
        ) { _, _ ->
            true
        }
        setTitle(keyWord)
        addRightIcon(R.mipmap.ic_top, View.OnClickListener {
            (recycler.layoutManager as GridLayoutManager).scrollToPositionWithOffset(0, 0)
        })
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

        mViewModel.uc.clickGoSearchEvent.observe(this, Observer {
            val fragment = SearchFragment()
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                exitTransition = Fade()
                fragment.enterTransition = Fade()
                fragment.sharedElementReturnTransition = DetailTransition()
                fragment.sharedElementEnterTransition = DetailTransition()

                // 25.1.0以下的support包,Material过渡动画只有在进栈时有,返回时没有;
                // 25.1.0+的support包，SharedElement正常
                extraTransaction()
                    .addSharedElement(sticky_header, App.context.resources.getString(R.string.transition_search))
                    .start(fragment, ISupportFragment.SINGLETASK)
            } else {
                mViewModel.startFragment(fragment, launchMode = ISupportFragment.SINGLETASK)
            }
        })
    }
}