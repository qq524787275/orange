package com.zhuzichu.orange.db

import android.util.ArrayMap
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zhuzichu.mvvm.App.Companion.context
import com.zhuzichu.mvvm.AppGlobal


@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        val DATABASE_NAME = "orange-db-"
        val databaseCache = ArrayMap<String, AppDatabase>()

        fun getInstance(): AppDatabase {
            var appDatabase = databaseCache[AppGlobal.getAccount()]
            if (appDatabase == null) {
                appDatabase = buildDatabase()
                databaseCache[AppGlobal.getAccount()] = appDatabase
            }
            return appDatabase
        }

        private fun buildDatabase(): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME + AppGlobal.getAccount())
                .addCallback(object : RoomDatabase.Callback() {

                })
                .build()
        }
    }
}
