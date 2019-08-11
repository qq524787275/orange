package com.zhuzichu.orange.category.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.zhuzichu.mvvm.base.BaseTopbarFragment
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.category.viewmodel.CategoryViewModel
import com.zhuzichu.orange.databinding.FragmentCategoryBinding
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 16:40
 */
class CategoryFragment : BaseTopbarFragment<FragmentCategoryBinding, CategoryViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_category
    override fun bindVariableId(): Int = BR.viewModel
    override fun setTitle(): String = "超级分类"

    override fun initView() {
        _viewModel.showLoading()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _viewModel.loadShopSort()
    }

    override fun initViewObservable() {
        setErrorCommand(BindingCommand({
            _viewModel.showLoading()
            _viewModel.loadShopSort()
        }))

        _viewModel.uc.rightRecyclerToTop.observe(this, Observer {
            (right_recycler.layoutManager as GridLayoutManager).scrollToPositionWithOffset(0, 0)
        })
    }
}
