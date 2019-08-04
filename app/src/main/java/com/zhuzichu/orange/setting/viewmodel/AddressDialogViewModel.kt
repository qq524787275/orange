package com.zhuzichu.orange.setting.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableBoolean
import com.zhuzichu.mvvm.bean.AddressBean
import com.zhuzichu.mvvm.bus.event.SingleLiveEvent
import com.zhuzichu.mvvm.databinding.command.BindingCommand
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.*
import com.zhuzichu.mvvm.view.dialog.BottomSheetViewModel
import com.zhuzichu.orange.BR
import com.zhuzichu.orange.R
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffObservableList

class AddressDialogViewModel(application: Application) : BottomSheetViewModel(application) {
    val color = ColorGlobal

    val list =
        AsyncDiffObservableList(itemDiffOf<ItemAddressViewModel> { oldItem, newItem -> (oldItem.id == newItem.id) && (oldItem.name == newItem.name) })

    val itemBind = itemBindingOf<Any>(BR.item, R.layout.item_address)

    val currentList = mutableListOf<ItemAddressViewModel?>(
        null, null, null
    )

    val onClickSure = BindingCommand<Any>({
        if (isEnableSure.get()) {
            uc.onSelectSureEvent.call()
        }
    })

    private lateinit var address: List<AddressBean>

    val uc = UIChangeObservable()

    val isEnableSure = ObservableBoolean()

    inner class UIChangeObservable {
        val onSelectAddressChange = SingleLiveEvent<ItemAddressViewModel>()
        val onScrollToPostionEvent = SingleLiveEvent<Int>()
        val onSelectSureEvent = SingleLiveEvent<Any>()
    }


    val onTabSelected = BindingCommand<Int>(consumer = {
        if (!::address.isInitialized)
            return@BindingCommand
        when (it) {
            0 -> {
                val data = address.mapIndexed { index, item ->
                    val isSelected = currentList[0]?.position == index
                    if (isSelected) {
                        uc.onScrollToPostionEvent.value = index
                    }
                    ItemAddressViewModel(this@AddressDialogViewModel, item.id, item.name, index, 0, isSelected)
                }
                list.update(data)
            }

            1 -> {
                if (currentList[0] == null) {
                    "请您先选择省份".toast()
                    list.update(listOf())
                    return@BindingCommand
                }

                val data = address[currentList[0]?.position!!].cityList.mapIndexed { index, item ->
                    val isSelected = currentList[1]?.position == index
                    if (isSelected) {
                        uc.onScrollToPostionEvent.value = index
                    }
                    ItemAddressViewModel(this@AddressDialogViewModel, item.id, item.name, index, 1, isSelected)
                }
                list.update(data)
            }

            2 -> {
                if (currentList[1] == null) {
                    "请您先选择省份和城市".toast()
                    list.update(listOf())
                    return@BindingCommand
                }
                val data = address[currentList[0]?.position!!].cityList[currentList[1]?.position!!]
                    .districtList.mapIndexed { index, item ->
                    val isSelected = currentList[2]?.position == index
                    if (isSelected) {
                        uc.onScrollToPostionEvent.value = index
                    }
                    ItemAddressViewModel(this@AddressDialogViewModel, item.id, item.name, index, 2, isSelected)
                }
                list.update(data)
            }
            else -> {
            }
        }
    })

    @SuppressLint("CheckResult")
    fun loadAddress() {
        AppGlobal.getAddress()
            .bindToSchedulers()
            .bindToLifecycle(getLifecycleProvider())
            .subscribe({
                address = it
                val data = address.mapIndexed { index, item ->
                    ItemAddressViewModel(this@AddressDialogViewModel, item.id, item.name, index, 0, false)
                }
                list.update(data)
            }, {
                it.printStackTrace()
                handleThrowable(it)
            })
    }


    fun selectAddress(taget: ItemAddressViewModel) {
        when (taget.type) {
            0 -> {
                currentList[0] = taget
                currentList[1] = null
                currentList[2] = null
                val data = address[currentList[0]?.position!!].cityList.mapIndexed { index, item ->
                    ItemAddressViewModel(this@AddressDialogViewModel, item.id, item.name, index, 1, false)
                }
                list.update(data)
            }
            1 -> {
                currentList[1] = taget
                currentList[2] = null
                val data = address[currentList[0]?.position!!].cityList[currentList[1]?.position!!]
                    .districtList.mapIndexed { index, item ->
                    ItemAddressViewModel(this@AddressDialogViewModel, item.id, item.name, index, 2, false)
                }
                list.update(data)
            }
            2 -> {
                currentList[2] = taget
                val data = address[currentList[0]?.position!!].cityList[currentList[1]?.position!!]
                    .districtList.mapIndexed { index, item ->
                    ItemAddressViewModel(
                        this@AddressDialogViewModel,
                        item.id,
                        item.name,
                        index,
                        2,
                        index == currentList[2]?.position
                    )
                }

                list.update(data)
            }
            else -> {
            }
        }
        uc.onSelectAddressChange.value = taget
    }
}