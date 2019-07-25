package com.zhuzichu.orange.mine.fragment

import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentFlutterLearnBinding
import com.zhuzichu.orange.mine.viewmodel.FlutterLearnViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-25
 * Time: 09:32
 */
class FlutterLearnFragment : BaseTopbarBackFragment<FragmentFlutterLearnBinding, FlutterLearnViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_flutter_learn

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setTitle("FLutter学习")
    }
}