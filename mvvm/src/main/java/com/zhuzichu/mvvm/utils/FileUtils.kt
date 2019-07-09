package com.zhuzichu.mvvm.utils

import android.os.Environment
import android.util.Base64
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.zhuzichu.mvvm.global.AppGlobal
import java.io.File
import java.io.FileInputStream

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 16:15
 */
private const val AUDIO_CACHE_DIR_NAME = "audio"

private const val SIGN_IMAGE_CACHE_DIR_NAME = "sign_image"

private const val HTTP_CACHE_DIR_NAME = "http_response"

/**
 * 检查内部存储是否可用
 * @return
 */
val isExternalStorageEnable: Boolean
    get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

/**
 * 获取缓存文件夹
 *
 * @param context 上下文
 * @param dirName 文件夹名称
 * @return 缓存文件夹
 */
@Nullable
fun getCacheDir(@NonNull dirName: String): File {
    val rootDir = AppGlobal.context.externalCacheDir
    val cacheFile = File(rootDir, dirName)
    if (!cacheFile.exists()) {
        cacheFile.mkdir()
    }
    return cacheFile
}

/**
 * 获取音频缓存文件夹
 *
 * @return 音频缓存文件夹
 */
@Nullable
fun getAudioCacheDir(): File {
    return getCacheDir(AUDIO_CACHE_DIR_NAME)
}

/**
 * 获取图片缓存文件夹
 *
 * @param context 上下文
 * @return 图片缓存文件夹
 */
@Nullable
fun getSignImageCacheDir(): File {
    return getCacheDir(SIGN_IMAGE_CACHE_DIR_NAME)
}

/**
 * 获取网络请求缓存文件夹
 * @param context 上下文
 * @return 网络请求缓存文件夹
 */
@Nullable
fun getHttpImageCacheDir(): File {
    return getCacheDir(HTTP_CACHE_DIR_NAME)
}

/**
 * 将文件转化为字节数组字符串，并对其进行Base64编码处理
 * @return
 */
@Throws(Exception::class)
fun encodeBase64File(@NonNull path: String): String {
    val file = File(path)
    val inputFile = FileInputStream(file)
    val buffer = ByteArray(file.length().toInt())
    inputFile.read(buffer)
    inputFile.close()
    return Base64.encodeToString(buffer, Base64.DEFAULT)
}