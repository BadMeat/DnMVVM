package com.dolan.arif.dncatapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Bencoleng on 07/10/2019.
 */

@Database(entities = [Cat::class], version = 1)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao

    companion object {
        @Volatile
        private var instance: CatDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, CatDatabase::class.java, "catdatabase"
        ).build()
    }
}