package com.zhuzichu.orange.setting.fragment

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhuzichu.mvvm.base.BaseSheetFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentDialogAddressBinding
import com.zhuzichu.orange.setting.viewmodel.AddressDialogViewModel
import com.zhuzichu.orange.setting.viewmodel.ItemAddressViewModel
import kotlinx.android.synthetic.main.fragment_dialog_address.*

class AddressDialogFragment(
    fm: FragmentManager,
    private var consumer: ((parameter: List<ItemAddressViewModel?>) -> Unit)? = null
) :
    BaseSheetFragment<FragmentDialogAddressBinding, AddressDialogViewModel>(fm) {
    private val titles = listOf("省份", "城市", "区县")

    override fun setLayoutId(): Int = R.layout.fragment_dialog_address

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        titles.forEach {
            tabs.addTab(tabs.newTab().setText(it))
        }
    }

    override fun initViewObservable() {
        _viewModel.uc.onSelectAddressChange.observe(this, Observer {
            val tab = tabs.getTabAt(it.type)
            tab?.text = it.name
            tabs.getTabAt(it.type + 1)?.select()

            _viewModel.currentList.forEachIndexed { index, item ->
                if (item == null) {
                    tabs.getTabAt(index)?.text = titles[index]
                }
            }
            _viewModel.isEnableSure.set(_viewModel.currentList[2] != null)
        })

        _viewModel.uc.onScrollToPostionEvent.observe(this, Observer {
            recycler.postDelayed({
                (recycler.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(it, 0)
            }, 150)
        })

        _viewModel.uc.onSelectSureEvent.observe(this, Observer {
            dialog?.cancel()
            view?.postDelayed({
                consumer?.invoke(_viewModel.currentList)
            }, 150)
        })
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        _viewModel.loadAddress()
    }
}