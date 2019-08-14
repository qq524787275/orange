package com.zhuzichu.orange.ui.file

import com.zhuzichu.mvvm.base.BaseActivity
import com.zhuzichu.orange.ui.file.fragment.FileFragment
import me.yokeyword.fragmentation.ISupportFragment

class FileActivity : BaseActivity() {

    companion object {
        const val PATH = "PATH"
    }

    override fun setRootFragment(): ISupportFragment {
        val fileFragment = FileFragment()
        fileFragment.arguments = intent.extras
        return fileFragment
    }
}
