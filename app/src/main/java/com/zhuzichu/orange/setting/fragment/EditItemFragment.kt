package com.zhuzichu.orange.setting.fragment

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentEditItemBinding
import com.zhuzichu.orange.setting.viewmodel.EditItemViewModel
import kotlinx.android.synthetic.main.fragment_edit_item.*

class EditItemFragment(
    private var consumer: ((parameter: String?, fragment: EditItemFragment) -> Unit)? = null
) : BaseTopbarBackFragment<FragmentEditItemBinding, EditItemViewModel>() {

    private val title: String by bindArgument(TITLE)
    private val text: String by bindArgument(TEXT)
    private val tips: String by bindArgument(TIPS)
    private val maxLength: Int by bindArgument(MAXLENGTH)

    companion object {
        const val TITLE = "title"
        const val TEXT = "text"
        const val TIPS = "tips"
        const val MAXLENGTH = "maxLength"
    }

    override fun setLayoutId(): Int = R.layout.fragment_edit_item

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setTitle(title)
        _viewModel.text.set(text)
        edit.post {
            edit.setSelection(edit.text?.length ?: 0)
            edit.filters = arrayOf(InputFilter.LengthFilter(maxLength))
        }
        _viewModel.tips.value = tips
        showSoftInput(edit)
        edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (_viewModel.text.get().isNullOrBlank()) {
                    _viewModel.isEnableSure.set(false)
                    return
                }
                if (text == _viewModel.text.get()) {
                    _viewModel.isEnableSure.set(false)
                } else {
                    _viewModel.isEnableSure.set(true)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
        })
    }

    override fun initViewObservable() {
        _viewModel.onSureEvent.observe(this, Observer {
            consumer?.invoke(it, this)
        })
    }

    override fun onDestroyView() {
        hideSoftInput()
        super.onDestroyView()
    }
}