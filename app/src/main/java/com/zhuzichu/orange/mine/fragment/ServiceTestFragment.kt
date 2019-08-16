package com.zhuzichu.orange.mine.fragment

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding3.view.clicks
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.base.OkBinder
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.IOrangeService
import com.zhuzichu.orange.OrangeService
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentServiceTestBinding
import com.zhuzichu.orange.mine.viewmodel.ServiceTestViewModel
import kotlinx.android.synthetic.main.fragment_service_test.*

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-16
 * Time: 13:44
 */
class ServiceTestFragment : BaseTopbarBackFragment<FragmentServiceTestBinding, ServiceTestViewModel>() {

    private var orangeService: IOrangeService? = null
    private val intent by lazy {
        Intent(requireContext(), OrangeService::class.java)
    }

    private val conn = object : ServiceConnection {
        override fun onServiceDisconnected(componentName: ComponentName?) {
            "远程服务连接失败".toast()
        }

        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            "远程服务连接成功".toast()
            orangeService = OkBinder.proxy(iBinder!!, IOrangeService::class.java)
        }

    }

    override fun setLayoutId(): Int = R.layout.fragment_service_test

    override fun bindVariableId(): Int = BR.viewModel

    @SuppressLint("CheckResult")
    override fun initView() {
        setTitle("远程服务测试")

        start.clicks().subscribe {
            ContextCompat.startForegroundService(requireContext(), intent)
        }

        bind.clicks().subscribe {
            _activity.bindService(intent, conn, Context.BIND_AUTO_CREATE)
        }

        fun1.clicks().subscribe {
            orangeService ?: let {
                "未连接到远程服务".toast()
                return@subscribe
            }
            orangeService?.getTestString()?.toast()
        }

        unbind.clicks().subscribe {
            _activity.unbindService(conn)
        }

        stop.clicks().subscribe {
            _activity.stopService(intent)
        }

    }
}