package com.zhuzichu.mvvm.utils

import java.net.URLDecoder
import java.net.URLEncoder

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 17:57
 */
/***
 * 解密
 * @param policy
 * @return
 */

private const val AES_KEY = "0987654321qazxcv"

fun decryptPolicy(policy: String): String {
    var p = policy
    try {
        if (p.isNotBlank()) {
            p = URLDecoder.decode(p, "UTF-8")
            val result = decrypt(p, AES_KEY)
            return result.toString()
        }
    } catch (e: Exception) {
    }

    return ""
}

/**
 * AES解密
 * @param policy
 * @return
 */
fun decryptAES(policy: String): String {
    try {
        if (policy.isNotBlank()) {
            val result = decrypt(policy, AES_KEY)
            return result.toString()
        }
    } catch (e: Exception) {
    }

    return ""
}

/***
 * 加密
 * @param policy
 * @return
 */
fun encryptPolicy(policy: String): String {
    try {
        if (policy.isNotBlank()) {
            return URLEncoder.encode(encrypt(policy, AES_KEY), "UTF-8")
        }
    } catch (e: Exception) {
    }

    return ""
}