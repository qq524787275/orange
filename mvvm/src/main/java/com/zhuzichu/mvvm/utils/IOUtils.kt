package com.zhuzichu.mvvm.utils

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 16:11
 */
object IOUtils {
    @Throws(IOException::class)
    fun toByteArray(input: InputStream): ByteArray {
        val output = ByteArrayOutputStream()
        write(input, output)
        output.close()
        return output.toByteArray()
    }

    @Throws(IOException::class)
    fun write(inputStream: InputStream, outputStream: ByteArrayOutputStream) {
        var len: Int
        val buffer = ByteArray(4096)
        while (inputStream.read(buffer).also { len = it } != -1) outputStream.write(buffer, 0, len)
    }

}