package com.example.mybread.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mybread.model.user.User
import com.example.mybread.model.user.UserDatabase
import com.example.mybread.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val loginResultLD = MutableLiveData<User?>()
    val registerResultLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun register(user: User) {
        launch {
            val db = buildDb(getApplication())
            val existingUser = db.userDao().getUserByUsername(user.username)

            if (existingUser == null) {
                db.userDao().insertUser(user)
                registerResultLD.postValue(true)
            } else {
                registerResultLD.postValue(false)
            }
        }
    }

    fun updateUserLocation(username: String, latitude: String, longitude: String) {
        launch {
            val dao = UserDatabase(getApplication()).userDao()
            dao.updateLocation(username, latitude, longitude)
        }
    }


    fun login(username: String, password: String) {
        launch {
            val db = buildDb(getApplication())
            val user = db.userDao().login(username, password)
            loginResultLD.postValue(user)
        }
    }

    fun updatePassword(username: String, newPassword: String){
        launch {
            val db = buildDb(getApplication())
            db.userDao().updatePassword(username, newPassword)
        }
    }
}
