package com.example.mybread.model.pesanan

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybread.util.DB_NAME

@Database(entities = [Pesanan::class], version = 1)
abstract class PesananDatabase : RoomDatabase() {

    abstract fun userDao(): PesananDao

    companion object {
        @Volatile
        private var instance: PesananDatabase? = null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PesananDatabase::class.java, DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

        operator fun invoke(context: Context): PesananDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}