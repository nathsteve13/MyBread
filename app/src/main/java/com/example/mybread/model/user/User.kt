package com.example.mybread.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "role")
    val role: String,

    @ColumnInfo(name = "latitude")
    val latitude: String,

    @ColumnInfo(name = "longtitude")
    val longtitude: String,

    @ColumnInfo(name = "nomor_hp")
    val nomor_hp: String,
)
