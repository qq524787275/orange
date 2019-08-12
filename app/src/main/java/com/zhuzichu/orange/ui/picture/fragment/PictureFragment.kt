package com.zhuzichu.orange.ui.picture.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentPictureBinding
import com.zhuzichu.orange.ui.picture.PictureActivity.Companion.POSITION
import com.zhuzichu.orange.ui.picture.PictureActivity.Companion.URLS
import com.zhuzichu.orange.ui.picture.viewmodel.PictureViewModel

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-12
 * Time: 17:01
 */
class PictureFragment : BaseFragment<FragmentPictureBinding, PictureViewModel>() {

    private val list: List<String> by bindArgument(URLS)
    private val position: Int by bindArgument(POSITION)

    override fun setLayoutId(): Int = R.layout.fragment_picture

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        super.initView()

    }
}