package com.zhuzichu.orange.find.viewmodel

import androidx.databinding.ObservableField
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.utils.getFavoriteCollectTime
import com.zhuzichu.orange.bean.SubjectBean

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
}