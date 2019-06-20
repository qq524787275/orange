package com.zhuzichu.orange.home.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentRankingBinding
import com.zhuzichu.orange.home.viewmodel.RankingViewModel
import kotlinx.android.synthetic.main.fragment_ranking.*

class RankingFragment : BaseFragment<FragmentRankingBinding, RankingViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_ranking

    override fun bindVariableId(): Int = BR.viewModel

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.updateData(1)
    }

    override fun initVariable() {
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