package com.zhuzichu.orange.search.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import com.zhuzichu.mvvm.base.BaseTopBarFragment
import com.zhuzichu.mvvm.utils.logi
import com.zhuzichu.mvvm.utils.toColor
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentSearchBinding
import com.zhuzichu.orange.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseTopBarFragment<FragmentSearchBinding, SearchViewModel>() {

    override fun setLayoutId(): Int = R.layout.fragment_search

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setStatusBarColor(R.color.colorBackground.toColor())
        showSoftInput(input)

        input.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                actionId.logi()
                if ((actionId == 0 || actionId == 3) && event != null) {
                    when (event.action) {
                        KeyEvent.ACTION_UP -> {
                            mViewModel.clickSearch.execute()
                        }
                        else -> {
                        }
                    }
                    return true
                }
                return false
            }
        })
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        mViewModel.loadHistoryData()
    }
}