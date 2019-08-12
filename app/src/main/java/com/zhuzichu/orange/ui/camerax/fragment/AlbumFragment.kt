package com.zhuzichu.orange.ui.camerax.fragment

import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.global.cache.CacheGlobal
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import com.zhuzichu.orange.databinding.FragmentAlbumBinding
import com.zhuzichu.orange.ui.camerax.viewmodel.AlbumViewModel
import com.zhuzichu.orange.ui.camerax.viewmodel.ItemAlbumViewModel

class AlbumFragment : BaseFragment<FragmentAlbumBinding, AlbumViewModel>() {

    override fun setLayoutId(): Int = R.layout.fragment_album

    override fun bindVariableId(): Int = BR.viewModel


    override fun initView() {
        super.initView()

        CacheGlobal.getCameraCacheDir().listFiles { file ->
            arrayOf("JPG").contains(file.extension.toUpperCase())
        }?.sorted()?.reversed()?.let {
            _viewModel.list.update(it.map { item ->
                ItemAlbumViewModel(_viewModel, item)
            })
        }
    }
}