package com.zhuzichu.orange.mine

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentMineBinding

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:40
 */
class MineFragment : BaseFragment<FragmentMineBinding, MineViewModel>() {
    override fun bindVariableId(): Int = BR.viewModel
    override fun setLayoutId(): Int = R.layout.fragment_mine
}