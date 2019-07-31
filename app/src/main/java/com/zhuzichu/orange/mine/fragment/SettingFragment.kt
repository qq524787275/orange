package com.zhuzichu.orange.mine.fragment

import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentSettingBinding
import com.zhuzichu.orange.mine.viewmodel.SettingViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-31
 * Time: 14:40
 */
class SettingFragment : BaseTopbarBackFragment<FragmentSettingBinding, SettingViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_setting

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setTitle("设置")

    }
}