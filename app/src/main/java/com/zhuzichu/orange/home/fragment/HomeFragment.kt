package com.zhuzichu.orange.home.fragment

import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import com.zhuzichu.mvvm.base.BaseTopBarFragment
import com.zhuzichu.mvvm.utils.initMagicIndicator
import com.zhuzichu.mvvm.utils.toColorById
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentHomeBinding
import com.zhuzichu.orange.home.adpater.HomeFragmentPageAdapter
import com.zhuzichu.orange.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-13
 * Time: 13:27
 */
class HomeFragment : BaseTopBarFragment<FragmentHomeBinding, HomeViewModel>() {
    val titles = listOf("榜单", "品牌", "快抢", "达人说")
    val fragments = listOf<Fragment>(RankingFragment(), TalentFragment(), BuyingFragment(), TalentFragment())
    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setStatusBarColor(R.color.colorBackground.toColorById())
        viewpager.adapter = HomeFragmentPageAdapter(childFragmentManager, fragments)
        viewpager.offscreenPageLimit = titles.size
        initMagicIndicator(
            activity, titles, viewpager, indicator
        )
    }

    override fun initViewObservable() {

    }

}