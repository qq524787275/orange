package com.zhuzichu.orange.setting.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.bean.UserInfoBean
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.http.AppRetrofit.preference
import com.zhuzichu.mvvm.utils.bindToException
import com.zhuzichu.mvvm.utils.bindToLifecycle
import com.zhuzichu.mvvm.utils.bindToSchedulers
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentSettingUserBinding
import com.zhuzichu.orange.main.fragment.MainFragment
import com.zhuzichu.mvvm.repository.NetRepositoryImpl
import com.zhuzichu.orange.setting.viewmodel.SettingUserViewModel
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-31
 * Time: 15:40
 */
class SettingUserFragment : BaseTopbarBackFragment<FragmentSettingUserBinding, SettingUserViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_setting_user

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setTitle("账号设置")

        addRightIcon(R.mipmap.ic_save) {
            "保存".toast()
        }
    }

    @SuppressLint("CheckResult")
    override fun initVariable() {
        NetRepositoryImpl.getUserInfo()
            .bindToLifecycle(this)
            .bindToSchedulers()
            .bindToException()
            .subscribe(
                {
                    AppGlobal.userInfo.set(it.data)
                },
                {
                    _viewModel.handleThrowable(it)
                }
            )
    }

    override fun initViewObservable() {
        _viewModel.uc.onShowLogoutSnackbarEvent.observe(this, Observer {
            Snackbar.make(_contentView, "确定要退出么?", Snackbar.LENGTH_SHORT).apply {
                _viewModel.color.colorPrimary.value?.let { color -> this.setActionTextColor(color) }
                this.setAction("确定") {
                    preference.token = null
                    AppGlobal.userInfo.set(UserInfoBean())
                    AppGlobal.isLogin.set(false)
                    _viewModel.startFragment(MainFragment(), launchMode = ISupportFragment.SINGLETASK)
                }.show()
            }
        })
    }
}