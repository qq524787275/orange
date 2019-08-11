package com.zhuzichu.orange.setting.fragment

import android.graphics.Bitmap
import androidx.lifecycle.Observer
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.global.glide.GlideApp
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.mvvm.utils.logi
import com.zhuzichu.mvvm.view.crop.KropView
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentCropImageBinding
import com.zhuzichu.orange.setting.viewmodel.CropImageViewModel
import com.zhuzichu.orange.widget.KropTarget
import kotlinx.android.synthetic.main.fragment_crop_image.*

class CropImageFragment(
    private var consumer: ((parameter: Bitmap?) -> Unit)? = null
) : BaseTopbarBackFragment<FragmentCropImageBinding, CropImageViewModel>() {

    private val path: String by bindArgument(PATH)

    companion object {
        const val PATH = "path"
    }


    override fun setLayoutId(): Int = R.layout.fragment_crop_image

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setTitle("图片裁剪")

        val target = KropTarget(container, kropView, true)

        "CropImageFragment".logi("zzc")
        GlideApp.with(kropView).asBitmap().load(path).into(target)

    }

    override fun initViewObservable() {
        _viewModel.onClickSubmitEvent.observe(this, Observer {
            consumer?.invoke(kropView.getCroppedBitmap())
            pop()
        })
    }
}