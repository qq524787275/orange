package com.zhuzichu.orange.setting.fragment

import androidx.fragment.app.FragmentManager
import com.zhuzichu.mvvm.view.dialog.BottomSheetFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentDialogShareBinding
import com.zhuzichu.orange.setting.viewmodel.ShareDialogViewModel

class ShareDialogFragment(fm: FragmentManager) :
    BottomSheetFragment<FragmentDialogShareBinding, ShareDialogViewModel>(fm) {
    override fun setLayoutId(): Int = R.layout.fragment_dialog_share

    override fun bindVariableId(): Int = BR.viewModel
}