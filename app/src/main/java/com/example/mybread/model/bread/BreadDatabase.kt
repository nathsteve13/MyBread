package com.example.mybread.model.bread

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybread.model.bread.Bread
import com.example.mybread.model.bread.BreadDao
import com.example.mybread.util.DB_NAME

@Database(entities = [Bread::class], version = 5)
abstract class BreadDatabase : RoomDatabase() {

    abstract fun breadDao(): BreadDao

    companion object {
        @Volatile
        private var instance: BreadDatabase? = null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BreadDatabase::class.java, "bread.db"
        )

            .fallbackToDestructiveMigration()
            .build()

        operator fun invoke(context: Context): BreadDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}