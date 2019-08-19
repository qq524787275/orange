package com.zhuzichu.mvvm.utils

import java.io.File

object FileUtils {
    /**
     * 文件或者文件夹是否存在.
     */
    fun fileExists(filePath: String): Boolean {
        val file = File(filePath)
        return file.exists()
    }

    /**
     * 删除指定文件夹下所有文件, 不保留文件夹.
     */
    fun delAllFile(path: String): Boolean {
        val file = File(path)
        return delAllFile(file)
    }

    fun delAllFile(file: File): Boolean {
        val flag = false
        if (!file.exists()) {
            return flag
        }
        if (file.isFile) {
            file.delete()
            return true
        }
        val files: Array<File?>? = file.listFiles()
        for (i in files!!.indices) {
            val exeFile = files[i]!!
            if (exeFile.isDirectory) {
                delAllFile(exeFile.absolutePath)
            } else {
                exeFile.delete()
            }
        }
        file.delete()
        return flag
    }
}