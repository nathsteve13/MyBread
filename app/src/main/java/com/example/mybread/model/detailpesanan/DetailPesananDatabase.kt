package com.example.mybread.model.detailpesanan

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybread.model.bread.Bread
import com.example.mybread.model.bread.BreadDao
import com.example.mybread.util.DB_NAME

@Database(entities = [DetailPesanan::class], version = 5)
abstract class DetailPesananDatabase : RoomDatabase() {

    abstract fun breadDao(): DetailPesananDao

    companion object {
        @Volatile
        private var instance: DetailPesananDatabase? = null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DetailPesananDatabase::class.java, "detail_pesanan.db"
        )

            .fallbackToDestructiveMigration()
            .build()

        operator fun invoke(context: Context): DetailPesananDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}