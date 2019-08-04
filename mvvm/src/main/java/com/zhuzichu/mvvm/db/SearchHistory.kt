package com.zhuzichu.mvvm.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*


@Entity(
    tableName = "search_history",
    indices = [Index("keyWord")]
)
data class SearchHistory(
    @PrimaryKey
    var keyWord: String,
    var lastTime: Calendar = Calendar.getInstance()
)