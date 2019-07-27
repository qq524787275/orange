package com.zhuzichu.orange.db

import androidx.collection.SimpleArrayMap
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.orange.App.Companion.context


@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        private const val DATABASE_NAME = "orange-db-"
        private val databaseCache = SimpleArrayMap<String, AppDatabase>()

        fun getInstance(isAttachUser: Boolean = true): AppDatabase {
            var key = DATABASE_NAME.plus(AppGlobal.getAccount())
            if (!isAttachUser)
                key = DATABASE_NAME
            var appDatabase = databaseCache[key]
            if (appDatabase == null) {
                appDatabase = buildDatabase()
                databaseCache.put(key, appDatabase)
            }
            return appDatabase
        }

        private fun buildDatabase(): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME.plus(AppGlobal.getAccount()))
                .addCallback(object : RoomDatabase.Callback() {

                })
                .build()
        }
    }
}
