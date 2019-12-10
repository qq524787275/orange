package com.zhuzichu.mvvm.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-28
 * Time: 17:53
 */


private const val defaultCharSet = "UTF-8"

// 加密
@Throws(Exception::class)
fun encrypt(sSrc: String, sKey: String?): String {
    requireNotNull(sKey) { "sKey can't be null." }
    // 判断Key是否为16位
    require(sKey.length == 16) { "Key长度不是16位" }
    val raw = sKey.toByteArray(charset(defaultCharSet))
    val skeySpec = SecretKeySpec(raw, "AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")// "算法/模式/补码方式"
    val iv = IvParameterSpec("0102030405060708".toByteArray())// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
    val encrypted = cipher.doFinal(sSrc.toByteArray(charset(defaultCharSet)))
    //return Base64.encodeBase64URLSafeString(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
    return Base64.encodeToString(encrypted, Base64.DEFAULT)// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
}

/**
 * 解密
 *
 * @param sSrc
 * @param sKey
 * @return
 * @throws Exception
 */

@Throws(Exception::class)
fun decrypt(sSrc: String, sKey: String?): String? {
    try {
        // 判断Key是否正确
        requireNotNull(sKey) { "sKey can't be null." }
        // 判断Key是否为16位
        require(sKey.length == 16) { "Key长度不是16位" }
        val raw = sKey.toByteArray(charset(defaultCharSet))
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = IvParameterSpec("0102030405060708".toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
        val encrypted1 = Base64.decode(sSrc, Base64.DEFAULT)// 先用base64解密
        return try {
            val original = cipher.doFinal(encrypted1)
            String(original, charset(defaultCharSet))
        } catch (e: Exception) {
            null
        }

    } catch (ex: Exception) {
        return null
    }
}
