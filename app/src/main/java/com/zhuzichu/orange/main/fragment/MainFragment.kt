package com.zhuzichu.orange.main.fragment

import androidx.fragment.app.Fragment
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.utils.toColor
import com.zhuzichu.mvvm.widget.BottomTabView
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentMainBinding
import com.zhuzichu.orange.home.fragment.HomeFragmnet
import com.zhuzichu.orange.main.adapter.MainFragmentPageAdapter
import com.zhuzichu.orange.main.viewmodel.MainViewModel
import com.zhuzichu.orange.mine.fragment.MineFragment
import com.zhuzichu.orange.sort.fragment.SortFragment
import kotlinx.android.synthetic.main.fragment_main.*
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-12
 * Time: 15:20
 */
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_main

    override fun bindVariableId(): Int = BR.viewModel

    private val mFragments = listOf<Fragment>(
        HomeFragmnet(),
        SortFragment(), MineFragment()
    )

    override fun initView() {
        val navigationController = bottom.custom()
            .addItem(
                newItem(
                    R.mipmap.main_tab_home_page_normal,
                    R.mipmap.main_tab_home_page_selected, "主页"
                )
            )
            .addItem(
                newItem(
                    R.mipmap.main_tab_project_normal,
                    R.mipmap.main_tab_project_selected, "分类"
                )
            )
            .addItem(
                newItem(
                    R.mipmap.main_tab_mine_normal,
                    R.mipmap.main_tab_mine_selected, "我的"
                )
            ).build()

        content.offscreenPageLimit = mFragments.size
        content.adapter = MainFragmentPageAdapter(childFragmentManager, mFragments)

        navigationController.setupWithViewPager(content)
    }

    //创建一个Item
    private fun newItem(drawable: Int, checkedDrawable: Int, text: String): BaseTabItem {
        val normalItemView = BottomTabView(activity)
        normalItemView.initialize(drawable, checkedDrawable, text)
        normalItemView.setTextDefaultColor(R.color.colorSecondText.toColor())
        normalItemView.setTextCheckedColor(R.color.colorPrimary.toColor())
        return normalItemView
    }
}