package com.zhuzichu.mvvm.databinding.command


class BindingCommand<T>(
    private var execute: (() -> Unit)? = null,
    private var consumer: ((parameter: T) -> Unit)? = null,
    private var canExecute0: (() -> Boolean)? = null
) {
    fun execute() {
        if (canExecute0()) {
            execute?.invoke()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun execute(parameter: Any) {
        if (canExecute0()) {
            consumer?.invoke(parameter as T)
        }
    }

    private fun canExecute0(): Boolean {
        return canExecute0?.invoke() ?: true
    }
}
