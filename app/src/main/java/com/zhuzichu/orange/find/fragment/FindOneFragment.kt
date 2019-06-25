package com.zhuzichu.orange.find.fragment

import android.os.Bundle
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentFindOneBinding
import com.zhuzichu.orange.find.viewmodel.FindOneViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 10:00
 */
class FindOneFragment : BaseFragment<FragmentFindOneBinding, FindOneViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_find_one

    override fun bindVariableId(): Int = BR.viewModel

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.loadSelectItemList()
    }
}