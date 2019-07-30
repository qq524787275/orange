package com.zhuzichu.orange.find.viewmodel

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.mvvm.bean.SubjectBean
import com.zhuzichu.orange.repository.NetRepositoryImpl

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-25
 * Time: 17:02
 */
class ItemThreeViewModel(
    viewModel: FindThreeViewModel,
    var subjectBean: SubjectBean
) : ItemViewModel<FindThreeViewModel>(viewModel) {
    val time = ObservableField<String>()
        .apply {
            set(
                "活动时间："
                    .plus(getFavoriteCollectTime(subjectBean.activity_start_time))
                    .plus("-")
                    .plus(getFavoriteCollectTime(subjectBean.activity_end_time))
            )
        }

    val onItemClick = BindingCommand<Any>( {
        loadData(subjectBean.id)
    })

    @SuppressLint("CheckResult")
    fun loadData(id: String) {
        NetRepositoryImpl.getSubjectItemList(id)
            .compose(bindToLifecycle(viewModel.getLifecycleProvider()))
            .compose(schedulersTransformer())
            .compose(exceptionTransformer())
            .subscribe(
                {
                    it.data.toast()
                }, {
                    viewModel.handleThrowable(it)
                }
            )
    }
}