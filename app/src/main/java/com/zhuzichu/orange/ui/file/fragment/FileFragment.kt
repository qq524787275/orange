package com.zhuzichu.orange.ui.file.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.os.Environment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhuzichu.mvvm.base.BaseTopbarBackFragment
import com.zhuzichu.mvvm.permissions.RxPermissions
import com.zhuzichu.mvvm.utils.bindArgument
import com.zhuzichu.mvvm.utils.toast
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentFileBinding
import com.zhuzichu.orange.ui.file.FileActivity
import com.zhuzichu.orange.ui.file.viewmodel.FileViewModel
import kotlinx.android.synthetic.main.fragment_file.*
import java.io.File

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-14
 * Time: 10:25
 */
class FileFragment : BaseTopbarBackFragment<FragmentFileBinding, FileViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_file

    override fun bindVariableId(): Int = BR.viewModel

    private val path: String? by bindArgument(FileActivity.PATH)

    @Suppress("DEPRECATION")
    @SuppressLint("CheckResult")
    override fun initView() {
        setTitle("文件管理器")

        nav.layoutManager = LinearLayoutManager(requireContext()).apply { orientation = RecyclerView.HORIZONTAL }

        var rootFile = File(Environment.getExternalStorageDirectory().absoluteFile.toURI())
        path?.let {
            rootFile = File(it)
        }

        RxPermissions(this)
            .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe { granted ->
                if (granted) {
                    _viewModel.loadFileList(rootFile)
                } else {
                    "权限被拒绝".toast()
                }
            }

    }

    override fun initViewObservable() {
        _viewModel.onAddFileNavEvent.observe(this, Observer {
            post {
                nav.scrollToPosition(_viewModel.navList.size)
            }
        })
    }
}