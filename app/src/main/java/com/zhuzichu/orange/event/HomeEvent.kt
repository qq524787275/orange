package com.zhuzichu.orange.event

class HomeEvent {
    companion object {
        val ENTER_TOW_LEVEL = 1
        val EXIT_TOW_LEVEL = 2
    }

    class OnTowLevelEvent(var state: Int)
}