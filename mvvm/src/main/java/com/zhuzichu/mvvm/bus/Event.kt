package com.zhuzichu.mvvm.bus

class Event {
    /**
     * 网络状态改变事件
     */
    class NetChangedEvent(var preNetStatus: Int, var curNetStatus: Int)

    class ThemeChangeEvent
}