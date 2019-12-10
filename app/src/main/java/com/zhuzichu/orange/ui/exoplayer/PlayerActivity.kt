package com.zhuzichu.orange.ui.exoplayer

import com.zhuzichu.mvvm.base.BaseActivity
import com.zhuzichu.orange.ui.exoplayer.fragment.PlayerFragment
import me.yokeyword.fragmentation.ISupportFragment

class PlayerActivity : BaseActivity() {

    override fun setRootFragment(): ISupportFragment=PlayerFragment()
}
