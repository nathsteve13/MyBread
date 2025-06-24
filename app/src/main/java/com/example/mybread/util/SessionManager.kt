package com.example.mybread.util

import android.content.Context
import android.content.SharedPreferences
import com.example.mybread.model.user.User

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "USER_ID"
        const val USERNAME = "USERNAME"
        const val PASSWORD = "PASSWORD"
        const val ROLE = "ROLE"
        const val IS_LOGIN = "IS_LOGIN"
    }

    fun saveLoginSession(userId: Int, username: String, password: String, role: String) {
        prefs.edit().apply {
            putInt(USER_ID, userId)
            putString(USERNAME, username)
            putString(PASSWORD, password)
            putString(ROLE, role)
            putBoolean(IS_LOGIN, true)
            apply()
        }
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(IS_LOGIN, false)

    fun getUserId(): Int = prefs.getInt(USER_ID, -1)

    fun getUsername(): String = prefs.getString(USERNAME, "") ?: ""

    fun getPassword(): String? = prefs.getString(PASSWORD, null)

    fun getRole(): String? = prefs.getString(ROLE, null)

    fun getUserProfile(): User {
        val username = getUsername()
        return User(username = username, password = "", name = "", latitude = "", longtitude = "", nomor_hp = "", role = "")
    }

    fun saveNewPassword(newPassword: String) {
        prefs.edit().putString(PASSWORD, newPassword).apply()
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    fun getAllPrefs(): Map<String, *> = prefs.all
}
