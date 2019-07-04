package com.zhuzichu.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.zhuzichu.mvvm.R

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-04
 * Time: 15:35
 */
abstract class BaseTopbarBackFragment<V : ViewDataBinding, VM : BaseViewModel> : BaseTopbarFragment<V, VM>(),
    View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        addLeftIcon(R.drawable.back_black, this)
        return view
    }

    override fun onClick(view: View?) {
        activity?.onBackPressed()
    }
}