package com.example.mybread.model.bread

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BreadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bread: Bread)

    @Query("SELECT * FROM bread WHERE id = :id LIMIT 1")
    fun searchById(id: Int): Bread

    @Query("SELECT * FROM bread")
    fun show(): List<Bread>

    @Update
    fun update(bread: Bread)

    @Delete
    fun delete(bread: Bread)
}
