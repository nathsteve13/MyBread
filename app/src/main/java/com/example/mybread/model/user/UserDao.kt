package com.example.mybread.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    fun login(username: String, password: String): User

    @Query("UPDATE user SET latitude = :lat, longtitude = :lon WHERE username = :username")
    fun updateLocation(username: String, lat: String, lon: String)

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    fun getUserByUsername(username: String): User

    @Query("UPDATE user SET password = :newPassword WHERE username = :username")
    fun updatePassword(username: String, newPassword: String)
}
