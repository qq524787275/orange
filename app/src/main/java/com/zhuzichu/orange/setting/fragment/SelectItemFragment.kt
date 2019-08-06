package com.zhuzichu.orange.setting.fragment

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseSheetFragment
import com.zhuzichu.mvvm.utils.dip2px
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentSelectItemBinding
import com.zhuzichu.orange.setting.viewmodel.ItemSelectViewModel
import com.zhuzichu.orange.setting.viewmodel.SelectItemViewModel
import kotlinx.android.synthetic.main.fragment_select_item.*

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-06
 * Time: 14:09
 */
class SelectItemFragment(
    fm: FragmentManager,
    private val consumer: ((parameter: SelectItemViewModel) -> List<ItemSelectViewModel>),
    private var selectItem: ((parameter: ItemSelectViewModel) -> Unit)? = null

) : BaseSheetFragment<FragmentSelectItemBinding, SelectItemViewModel>(fm) {
    override fun setLayoutId(): Int = R.layout.fragment_select_item

    override fun bindVariableId(): Int = BR.viewModel

    private lateinit var list: List<ItemSelectViewModel>

    override fun initView() {
        list = consumer.invoke(_viewModel)
        recycler.layoutParams.height = dip2px(50f) * list.size
        _viewModel.list.value = list
    }

    override fun initViewObservable() {
        _viewModel.onSelectItemEvent.observe(this, Observer {
            selectItem?.invoke(it)
            dismiss()
        })
    }
}