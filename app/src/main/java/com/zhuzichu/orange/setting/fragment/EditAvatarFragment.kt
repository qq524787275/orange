package com.zhuzichu.orange.setting.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.AppGlobal.userInfo
import com.zhuzichu.mvvm.global.glide.GlideApp
import com.zhuzichu.mvvm.global.glide.GlideEngine
import com.zhuzichu.mvvm.permissions.RxPermissions
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.mvvm.view.imagezoom.ImageViewTouch
import com.zhuzichu.mvvm.view.imagezoom.ImageViewTouchBase
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.Constants
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentEditAvatarBinding
import com.zhuzichu.orange.setting.viewmodel.EditAvatarViewModel
import com.zhuzichu.orange.setting.viewmodel.ItemSelectViewModel


class EditAvatarFragment : BaseTopbarBackFragment<FragmentEditAvatarBinding, EditAvatarViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_edit_avatar
    override fun bindVariableId(): Int = BR.viewModel

    companion object {
        private const val REQUEST_CODE_CHOOSE = 111
    }

    override fun initView() {
        setTitle("编辑头像")
        val imageView = _contentView.findViewById<ImageViewTouch>(R.id.image)
        imageView.displayType = ImageViewTouchBase.DisplayType.FIT_TO_SCREEN
        addRightIcon(R.mipmap.ic_more) {
            val selectItemFragment = SelectItemFragment(childFragmentManager, consumer = {
                listOf(
                    ItemSelectViewModel(it, "拍照", 0, false),
                    ItemSelectViewModel(it, "从相册中选取", 1, false)
                )
            }) {
                if (it.value == 1) {
                    RxPermissions(this)
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .bindToLifecycle(_viewModel.getLifecycleProvider())
                        .subscribe { granted ->
                            if (granted) {
                                Matisse.from(this)
                                    .choose(MimeType.ofAll())
                                    .countable(true)
                                    .showSingleMediaType(true)
                                    .imageEngine(GlideEngine())
                                    .forResult(REQUEST_CODE_CHOOSE)
                            }
                        }
                }
            }
            selectItemFragment.show()
        }
        val avatarUrl = _viewModel.global.userInfo.get()?.avatarUrl
        if (avatarUrl.isNullOrBlank()) {
            GlideApp.with(imageView).load(R.mipmap.ic_user_avatar_two).into(imageView)
        } else {
            GlideApp.with(imageView).load(avatarUrl).into(imageView)
        }

    }

    @SuppressLint("CheckResult")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            val selected = Matisse.obtainPathResult(data)
            selected.size.toString().logi("zzc")
            NetRepositoryImpl.getAvatarToken()
                .bindToException()
                .bindToLifecycle(_viewModel.getLifecycleProvider())
                .bindToSchedulers()
                .doOnSubscribe { _viewModel.showLoadingDialog() }
                .doFinally { _viewModel.hideLoadingDialog() }
                .subscribe(
                    {
                        val path = selected[0]!!
                        val key = "avatar_".plus(userInfo.get()?.id.toString())
                        val token = it.data!!
                        AppGlobal.run {
                            _viewModel.showLoadingDialog()
                            getUploadManager()
                                .put(
                                    path, key, token,
                                    { _, info, response ->
                                        if (info.isOK) {
                                            "上传成功!".toast()
                                            response.logi("zzc")
                                            info.host.logi("zzc")
                                            info.response.logi("zzc")
                                            updateUserInfo(Constants.TYPE_AVATAR, key)
                                        } else {
                                            "上传失败！".toast()
                                            info.error.logi("zzc")
                                        }
                                        _viewModel.hideLoadingDialog()
                                    }, null
                                )
                        }
                        it.data?.toast()
                    },
                    {
                        _viewModel.handleThrowable(it)
                    }
                )
        }
    }

    @SuppressLint("CheckResult")
    fun updateUserInfo(type: Int, value: Any) {
        NetRepositoryImpl.updateUserInfo(type, value)
            .bindToException()
            .bindToLifecycle(_viewModel.getLifecycleProvider())
            .bindToSchedulers()
            .doOnSubscribe { _viewModel.showLoadingDialog() }
            .doFinally { _viewModel.hideLoadingDialog() }
            .subscribe(
                {
                    val data = it.data
                    userInfo.set(data.apply {
                        avatarUrl=Constants.APP_IMAGE_URL.plus(avatarUrl)
                    })
                },
                {
                    _viewModel.handleThrowable(it)
                }
            )
    }
}