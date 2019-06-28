package com.zhuzichu.mvvm.databinding.video

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.zhuzichu.mvvm.global.glide.GlideApp

@BindingAdapter(value = ["videoUrl", "videoImage"], requireAll = false)
fun bindVideo(videoPlayer: StandardGSYVideoPlayer, url: String, imageurl: String) {
    videoPlayer.initUIState()
    videoPlayer.setUp(url, true, null)
    val imageView = ImageView(videoPlayer.context)
    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
    videoPlayer.thumbImageView = imageView
    videoPlayer.titleTextView.visibility = View.GONE
    videoPlayer.backButton.visibility = View.GONE
    videoPlayer.isShowFullAnimation = true
    videoPlayer.fullscreenButton.setOnClickListener {
        videoPlayer.startWindowFullscreen(videoPlayer.context, true, true)
    }
    GlideApp.with(imageView).load(imageurl).into(imageView)
    //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
}