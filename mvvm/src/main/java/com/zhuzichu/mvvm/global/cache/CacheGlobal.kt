package com.zhuzichu.mvvm.global.cache

import android.text.format.Formatter
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.utils.isSDCardMounted
import java.io.File

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-24
 * Time: 15:08
 */
object CacheGlobal {
    private const val GLIDE_CACHE_DIR_NAME = "/glide_cache"

    private const val HTTP_CACHE_DIR_NAME = "/http_response_cache"

    private const val CAMERA_CACHE_DIR_NAME = "/camera_cache"

    private const val DOWNLOAD_CACHE_DIR_NAME = "/download_cache"

    fun initDir() {
        getGlideCacheDir()
        getHttpCacheDir()
        getCameraCacheDir()
        getDownLoadCacheDir()
    }

    private fun getDiskCacheDir(last: String): File {
        val path: String = if (isSDCardMounted()) {
            AppGlobal.context.externalCacheDir.toString() + last
        } else {
            AppGlobal.context.cacheDir.toString() + last
        }
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absoluteFile
    }

    fun getDownLoadCacheDir(): File {
        return getDiskCacheDir(DOWNLOAD_CACHE_DIR_NAME)
    }

    fun getCameraCacheDir(): File {
        return getDiskCacheDir(CAMERA_CACHE_DIR_NAME)
    }

    /**
     * 获取http缓存目录
     *
     * @param context context
     * @return 缓存目录
     */
    fun getHttpCacheDir(): File {
        return getDiskCacheDir(HTTP_CACHE_DIR_NAME)
    }

    /**
     * 获取http缓存大小
     *
     * @param context context
     * @return 缓存大小
     */
    fun getHttpCacheFileSizeStr(): String {
        val fileSize = getHttpCacheFileSizeNum()
        return Formatter.formatFileSize(AppGlobal.context, fileSize)
    }

    fun getHttpCacheFileSizeNum(): Long {
        var fileSize: Long = 0
        val file = getHttpCacheDir()
        file.listFiles()?.let {
            for (childFile in it) {
                fileSize += childFile.length()
            }
        }
        return fileSize
    }


    /**
     * 获取glide缓存目录
     *
     * @param context context
     * @return 缓存目录
     */
    fun getGlideCacheDir(): File {
        return getDiskCacheDir(GLIDE_CACHE_DIR_NAME)
    }

    /**
     * 获取Glide缓存大小
     *
     * @param context context
     * @return 缓存大小
     */
    fun getGlideCacheFileSizeStr(): String {
        val fileSize = getGlideCacheFileSizeNum()
        return Formatter.formatFileSize(AppGlobal.context, fileSize)
    }

    fun getGlideCacheFileSizeNum(): Long {
        var fileSize: Long = 0
        val file = getGlideCacheDir()
        file.listFiles()?.let {
            for (childFile in it) {
                fileSize += childFile.length()
            }
        }
        return fileSize
    }

}