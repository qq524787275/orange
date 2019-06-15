package com.zhuzichu.mvvm.http.exception;

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 16:32
 */
class ResponseThrowable : RuntimeException {
    lateinit var msg: String
    var code: Int = -1

    constructor() {}

    constructor(msg: String) {
        this.msg = msg
    }

    constructor(msg: String, code: Int) {
        this.msg = msg
        this.code = code
    }
}