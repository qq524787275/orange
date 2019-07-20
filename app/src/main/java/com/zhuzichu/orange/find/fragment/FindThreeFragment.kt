package com.zhuzichu.orange.find.fragment

import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.view.banner.ScaleLayoutManager
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentFindThreeBinding
import com.zhuzichu.orange.find.viewmodel.FindThreeViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 10:01
 */
class FindThreeFragment : BaseFragment<FragmentFindThreeBinding, FindThreeViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_find_three

    override fun bindVariableId(): Int = BR.viewModel


    override fun initView() {
        _viewModel.showLoading()
        setErrorCommand(BindingCommand({
            _viewModel.loadSubjectData()
        }))
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.loadSubjectData()

    }

}