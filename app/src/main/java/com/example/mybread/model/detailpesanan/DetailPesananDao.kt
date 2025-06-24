package com.example.mybread.model.detailpesanan


import androidx.room.*

@Dao
interface DetailPesananDao {
    @Insert
    fun insertDetail(detail: DetailPesanan)

    @Insert
    fun insertAllDetails(details: List<DetailPesanan>)

    @Query("SELECT * FROM DetailPesanan WHERE pesanan_id = :pesananId")
    fun getDetailsByPesanan(pesananId: Int): List<DetailPesanan>

    @Query("DELETE FROM DetailPesanan WHERE pesanan_id = :pesananId")
    fun deleteByPesanan(pesananId: Int)
}
