package com.example.mybread.model.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybread.util.DB_NAME

@Database(entities = [User::class], version = 4)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java, "user.db"
        )

            .fallbackToDestructiveMigration()
            .build()

        operator fun invoke(context: Context): UserDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}