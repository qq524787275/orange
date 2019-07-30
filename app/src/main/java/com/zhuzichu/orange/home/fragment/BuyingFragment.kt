package com.zhuzichu.orange.home.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.mvvm.bean.BuyTimeBean
import com.zhuzichu.orange.databinding.FragmentBuyingBinding
import com.zhuzichu.orange.home.viewmodel.BuyingViewModel

class BuyingFragment : BaseFragment<FragmentBuyingBinding, BuyingViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_buying

    override fun bindVariableId(): Int = BR.viewModel

    private val titles = listOf(
        BuyTimeBean(1, "00:00", "昨日开抢"),
        BuyTimeBean(2, "10:00", "昨日开抢"),
        BuyTimeBean(3, "12:00", "昨日开抢"),
        BuyTimeBean(4, "15:00", "昨日开抢"),
        BuyTimeBean(5, "20:00", "昨日开抢"),
        BuyTimeBean(6, "00:00", "已开抢"),
        BuyTimeBean(7, "10:00", "正在快抢中"),
        BuyTimeBean(8, "12:00", "即将开抢"),
        BuyTimeBean(9, "15:00", "即将开抢"),
        BuyTimeBean(10, "20:00", "即将开抢"),
        BuyTimeBean(11, "00:00", "明日开抢"),
        BuyTimeBean(12, "10:00", "明日开抢"),
        BuyTimeBean(13, "12:00", "明日开抢"),
        BuyTimeBean(14, "15:00", "明日开抢"),
        BuyTimeBean(15, "20:00", "明日开抢")
    )
}