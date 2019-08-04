package com.zhuzichu.mvvm.base

import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel

open class ItemViewModel<VM : AndroidViewModel>(@param:NonNull protected var viewModel: VM)