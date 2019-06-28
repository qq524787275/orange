package com.zhuzichu.orange.find.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentFindTwoBinding
import com.zhuzichu.orange.find.viewmodel.FindTwoViewModel
import kotlinx.android.synthetic.main.fragment_find_two.*

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 10:00
 */
class FindTwoFragment : BaseFragment<FragmentFindTwoBinding, FindTwoViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_find_two

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        _viewModel.showLoading()
        setErrorCommand(BindingCommand({
            _viewModel.loadSubjefctHostData()
        }))
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.loadSubjefctHostData()
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