package com.zhuzichu.orange.home.fragment

import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentRankingBinding
import com.zhuzichu.orange.home.viewmodel.RankingViewModel

class RankingFragment : BaseFragment<FragmentRankingBinding, RankingViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_ranking

    override fun bindVariableId(): Int = BR.viewModel

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.updateData(1)
    }
}