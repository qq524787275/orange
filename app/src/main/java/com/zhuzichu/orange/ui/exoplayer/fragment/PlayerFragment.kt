package com.zhuzichu.orange.ui.exoplayer.fragment

import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentPlayerBinding
import com.zhuzichu.orange.ui.exoplayer.viewmodel.PlayerViewModel
import kotlinx.android.synthetic.main.fragment_player.*


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-09-03
 * Time: 15:39
 */
class PlayerFragment : BaseFragment<FragmentPlayerBinding, PlayerViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_player

    override fun bindVariableId(): Int = BR.viewModel

    lateinit var player: SimpleExoPlayer

    fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
            requireContext(),
            DefaultRenderersFactory(requireContext()),
            DefaultTrackSelector(),
            DefaultLoadControl()
        )
        playerView.player=player
        player.playWhenReady=true
    }
}