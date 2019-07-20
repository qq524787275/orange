package com.zhuzichu.mvvm.utils

import java.util.regex.Pattern

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-02
 * Time: 14:49
 */

/**
 * 在指定url后追加参数
 * @param url
 * @param data 参数集合 key = value
 * @return
 */
private fun appendUrl(url: String, data: Map<String, Any>): String {
    var newUrl = url
    val param = StringBuffer()
    for (key in data.keys) {
        param.append(key + "=" + data[key]!!.toString() + "&")
    }
    var paramStr = param.toString()
    paramStr = paramStr.substring(0, paramStr.length - 1)
    if (newUrl.indexOf("?") >= 0) {
        newUrl += "&$paramStr"
    } else {
        newUrl += "?$paramStr"
    }
    return newUrl
}

/**
 * 获取指定url中的某个参数
 * @param url
 * @param name
 * @return
 */
fun getParamByUrl(url: String, name: String): String? {
    var u = url
    u += "&"
    val pattern = "([?&])#?$name=[a-zA-Z0-9]*(&)"

    val r = Pattern.compile(pattern)

    val m = r.matcher(u)
    return if (m.find()) {
        println(m.group(0))
        m.group(0)?.split("=".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()?.get(1)?.replace("&", "")
    } else {
        null
    }
}