package com.zhuzichu.orange.home.fragment

import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.view.banner.ScaleLayoutManager
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentTalentBinding
import com.zhuzichu.orange.home.viewmodel.TalentViewModel
import kotlinx.android.synthetic.main.fragment_talent.*

class TalentFragment : BaseFragment<FragmentTalentBinding, TalentViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_talent

    override fun bindVariableId(): Int = BR.viewModel


    override fun initView() {
        initBanner()
    }

    override fun onResume() {
        super.onResume()
        recycler.start()
    }

    override fun onPause() {
        super.onPause()
        recycler.pause()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.loadData()
    }

    private fun initBanner() {
        val scaleLayoutManager = ScaleLayoutManager(_activity, 1)
        scaleLayoutManager.minScale = 0.9f
        recycler.layoutManager = scaleLayoutManager
    }
}