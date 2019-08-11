package com.zhuzichu.orange.mine.viewmodel

import com.zhuzichu.mvvm.base.BaseViewModel
import com.zhuzichu.mvvm.base.ItemViewModel
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.camerax.CameraActivity
import com.zhuzichu.orange.camerax.fragment.CameraFragment
import com.zhuzichu.orange.flutter.BallGameFlutterActivity
import com.zhuzichu.orange.flutter.MainFlutterFragment
import com.zhuzichu.orange.setting.fragment.AddressDialogFragment
import com.zhuzichu.orange.setting.fragment.CropImageFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-25
 * Time: 09:51
 */
class ItemSmallProgramViewModel(
    viewModel: SmallProgramViewModel,
    var id: Int,
    var title: String
) : ItemViewModel<BaseViewModel>(viewModel) {
    val color = ColorGlobal
    val onItemClick = BindingCommand<Any>({
        when (id) {
            0 -> {
                (0 / 0).toString().toast()
            }
            1 -> {
                viewModel.startFragment(MainFlutterFragment())
            }
            2 -> {
                val addressFragment = AddressDialogFragment(viewModel._fragment.childFragmentManager)
                addressFragment.show()
            }
            3 -> {
                viewModel.startActivity(CameraActivity::class.java)
            }
            4 -> {
                viewModel.startActivity(clz = BallGameFlutterActivity::class.java)
            }
            else -> {
            }
        }
    })
}