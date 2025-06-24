package com.example.mybread.util

import android.content.Context
import android.content.SharedPreferences
import com.example.mybread.model.user.User

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val USERNAME = "USERNAME"
        const val PASSWORD = "PASSWORD"
        const val IS_LOGIN = "IS_LOGIN"
        const val ROLE = "ROLE"
    }

    fun saveLoginSession(username: String, password: String, role: String) {
        prefs.edit().apply {
            putString(USERNAME, username)
            putString(PASSWORD, password)
            putString(ROLE, role)
            putBoolean(IS_LOGIN, true)
            apply()
        }
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(IS_LOGIN, false)

    fun getUsername(): String? = prefs.getString(USERNAME, null)

    fun getPassword(): String? = prefs.getString(PASSWORD, null)

    fun getRole(): String? = prefs.getString(ROLE, null)

    fun getUsernameProfile(): User {
        val username = prefs.getString(USERNAME, "") ?: ""
        return User(username = username, password = "", name = "", latitude = "", longtitude = "", nomor_hp = "", role = "")
    }

    fun savePassBaru(newPassword: String) {
        prefs.edit().putString(PASSWORD, newPassword).apply()
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun getAllPrefs(): Map<String, *> = prefs.all
}
