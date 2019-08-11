package com.zhuzichu.orange.setting.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.global.AppGlobal.getUploadManager
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
import com.zhuzichu.orange.camerax.CameraActivity
import com.zhuzichu.orange.databinding.FragmentEditAvatarBinding
import com.zhuzichu.orange.setting.viewmodel.EditAvatarViewModel
import com.zhuzichu.orange.setting.viewmodel.ItemSelectViewModel
import java.io.ByteArrayOutputStream


class EditAvatarFragment : BaseTopbarBackFragment<FragmentEditAvatarBinding, EditAvatarViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_edit_avatar
    override fun bindVariableId(): Int = BR.viewModel

    private lateinit var imageView: ImageViewTouch

    companion object {
        private const val REQUEST_CODE_CHOOSE = 111
    }

    override fun initView() {
        setTitle("编辑头像")
        imageView = _contentView.findViewById(R.id.image)
        imageView.displayType = ImageViewTouchBase.DisplayType.FIT_TO_SCREEN
        addRightIcon(R.mipmap.ic_more) {
            val selectItemFragment = SelectItemFragment(childFragmentManager, consumer = {
                listOf(
                    ItemSelectViewModel(it, "拍照", 0, false),
                    ItemSelectViewModel(it, "从相册中选取", 1, false)
                )
            }) {
                checkPermissions(it.value.toString().toInt())
            }
            selectItemFragment.show()
            "执行啦show".logi("zzc")
        }
        val avatarUrl = _viewModel.global.userInfo.value?.avatarUrl
        updateImage(avatarUrl)
    }


    @SuppressLint("CheckResult")
    fun checkPermissions(value: Int) {
        when (value) {
            0 -> {
                //拍照权限
                RxPermissions(this)
                    .request(Manifest.permission.CAMERA)
                    .subscribe { granted ->
                        if (granted) {
                            _viewModel.startActivity(CameraActivity::class.java)
                        } else {
                            "权限被拒绝".toast()
                        }
                    }
            }

            1 -> {
                //数据读写权限
                RxPermissions(this)
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (granted) {
                            startPicChoose()
                        } else {
                            "权限被拒绝".toast()
                        }
                    }
            }
            else -> {
            }
        }
    }


    private fun startPicChoose() {
        Matisse.from(this)
            .choose(MimeType.ofAll())
            .countable(true)
            .showSingleMediaType(true)
            .imageEngine(GlideEngine())
            .forResult(REQUEST_CODE_CHOOSE)
    }


    private fun updateImage(avatarUrl: String?) {
        if (avatarUrl.isNullOrBlank()) {
            GlideApp.with(imageView).load(R.mipmap.ic_user_avatar_two).into(imageView)
        } else {
            GlideApp.with(imageView).load(avatarUrl).into(imageView)
        }
    }

    override fun initViewObservable() {
        userInfo.observe(this, Observer {
            updateImage(it.avatarUrl)
        })
    }

    @SuppressLint("CheckResult")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            val selected = Matisse.obtainPathResult(data)
            val fragment = CropImageFragment { bitmap ->
                _viewModel.showLoadingDialog()
                NetRepositoryImpl.getAvatarToken()
                    .bindToException()
                    .bindToLifecycle(_viewModel.getLifecycleProvider())
                    .bindToSchedulers()
                    .subscribe(
                        {
                            val key =
                                "avatar_".plus(userInfo.value?.id.toString()).plus("_").plus(System.currentTimeMillis())
                            val token = it.data
                            uploadAvatar(bitmap, key, token)
                        }, {
                            _viewModel.handleThrowable(it)
                            _viewModel.hideLoadingDialog()
                        })
            }
            _viewModel.startFragment(
                fragment, bundle = bundleOf(
                    CropImageFragment.PATH to selected[0]
                )
            )
        }
    }


    private fun uploadAvatar(bitmap: Bitmap?, key: String, upToken: String) {
        if (bitmap == null) {
            "数据异常".toast()
            return
        }
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val datas = baos.toByteArray()
        getUploadManager().put(
            datas, key, upToken,
            { _, info, _ ->
                if (info.isOK) {
                    updateUserInfo(Constants.TYPE_AVATAR, key)
                } else {
                    "修改头像失败".toast()
                    _viewModel.hideLoadingDialog()
                }
            }, null
        )
    }

    @SuppressLint("CheckResult")
    fun updateUserInfo(type: Int, value: Any) {
        NetRepositoryImpl.updateUserInfo(type, value)
            .bindToException()
            .bindToLifecycle(_viewModel.getLifecycleProvider())
            .bindToSchedulers()
            .subscribe(
                {
                    val data = it.data
                    "修改成功".toast()
                    userInfo.value = data
                    _viewModel.hideLoadingDialog()
                },
                {
                    "修改头像失败".toast()
                    _viewModel.hideLoadingDialog()
                }
            )
    }
}