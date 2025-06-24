package com.example.mybread.model.detailpesanan

import androidx.room.*

@Entity
data class DetailPesanan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "pesanan_id")
    val pesananId: Int,

    @ColumnInfo(name = "bread_id")
    val breadId: Int,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "subtotal")
    val subtotal: Int
)
