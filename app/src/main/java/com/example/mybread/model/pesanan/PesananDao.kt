package com.example.mybread.model.pesanan


import androidx.room.*

@Dao
interface PesananDao {
    @Insert
    fun insertPesanan(pesanan: Pesanan): Long

    @Query("SELECT * FROM Pesanan")
    fun show(): List<Pesanan>

    @Query("SELECT * FROM Pesanan WHERE user_id = :userId ORDER BY date DESC")
    fun getPesananByUser(userId: Int): List<Pesanan>

    @Query("SELECT * FROM Pesanan WHERE id = :id")
    fun getPesananById(id: Int): Pesanan

    @Query("UPDATE Pesanan SET status = :status WHERE id = :id")
    fun updateStatus(id: Int, status: String)

    @Delete
    fun deletePesanan(pesanan: Pesanan)

    @Query("DELETE FROM Pesanan WHERE user_id = :userId")
    fun deleteAllPesananByUser(userId: Int)
}
