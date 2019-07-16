package com.zhuzichu.orange.home.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.mvvm.view.nicebanner.NicePagerAdapter
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentTalentBinding
import com.zhuzichu.orange.home.viewmodel.TalentViewModel
import kotlinx.android.synthetic.main.fragment_talent.*

class TalentFragment : BaseFragment<FragmentTalentBinding, TalentViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_talent

    override fun bindVariableId(): Int = BR.viewModel


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.loadData()
    }

    override fun initViewObservable() {

        _viewModel.uc.onLoadDataSuccess.observe(this, Observer {
            banner.setNiceViewPagerAdapter(NicePagerAdapter(_activity, it.map { item ->
                item.app_image
            }))
            toast(it.size.toString())
        })
    }
}