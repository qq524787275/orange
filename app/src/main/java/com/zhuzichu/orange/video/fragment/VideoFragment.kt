package com.zhuzichu.orange.video.fragment

import android.os.Bundle
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.zhuzichu.mvvm.BR
import com.zhuzichu.mvvm.base.BaseTopBarFragment
import com.zhuzichu.orange.databinding.FragmentVideoBinding
import com.zhuzichu.orange.video.viewmodel.VideoViewModel


class VideoFragment : BaseTopBarFragment<FragmentVideoBinding, VideoViewModel>() {
    override fun setLayoutId(): Int = com.zhuzichu.orange.R.layout.fragment_video

    override fun bindVariableId(): Int = BR.viewModel

    override fun initView() {
        setTitle("视频秀")
    }

    override fun onSupportInvisible() {
        GSYVideoManager.onPause()
    }

    override fun onSupportVisible() {
        GSYVideoManager.onResume()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        _viewModel.laodVideoData()
    }

    override fun onBackPressedSupport(): Boolean {
        if (GSYVideoManager.backFromWindowFull(context)) {
            return true
        }
        return false
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }
}