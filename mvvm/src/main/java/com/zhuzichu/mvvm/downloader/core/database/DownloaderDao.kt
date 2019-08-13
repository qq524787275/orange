package com.zhuzichu.mvvm.downloader.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhuzichu.mvvm.downloader.core.model.DownloaderData

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    21/6/2019
 * Email:   alirezat775@gmail.com
 */
@Dao
internal interface DownloaderDao {

    @Query("SELECT * FROM DownloaderData WHERE url IS :url")
    fun getDownloadByUrl(url: String): DownloaderData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewDownload(vararg item: DownloaderData)

    @Query("UPDATE DownloaderData SET status= :success, percent=:percent, size=:downloadedSize, totalSize=:totalSize WHERE url IS :url")
    fun updateDownload(url: String, success: Int, percent: Int, downloadedSize: Int, totalSize: Int)
}