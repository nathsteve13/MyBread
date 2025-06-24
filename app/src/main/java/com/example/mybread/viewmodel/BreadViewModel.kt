package com.example.mybread.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mybread.model.bread.Bread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.example.mybread.model.bread.BreadDatabase

class BreadViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val breadLD = MutableLiveData<List<Bread>>()
    val breadSingleLD = MutableLiveData<Bread>()

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        launch {
            val db = BreadDatabase(getApplication())
            breadLD.postValue(db.breadDao().show())
        }
    }

    fun load(id: Int) {
        launch {
            val db = BreadDatabase(getApplication())
            breadSingleLD.postValue(db.breadDao().searchById(id))
        }
    }

    fun insert(bread: Bread) {
        launch {
            val id = BreadDatabase(getApplication()).breadDao().insert(bread)
            refresh()
        }
    }

    fun update(bread: Bread) {
        launch {
            BreadDatabase(getApplication()).breadDao().update(bread)
            refresh()
        }
    }
}
