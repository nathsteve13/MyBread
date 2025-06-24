package com.example.mybread.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mybread.model.detailpesanan.DetailPesanan
import com.example.mybread.model.detailpesanan.DetailPesananDatabase
import com.example.mybread.model.pesanan.Pesanan
import com.example.mybread.model.pesanan.PesananDatabase
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext
class PesananViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val pesananLD = MutableLiveData<List<Pesanan>>()
    val detailPesananLD = MutableLiveData<List<DetailPesanan>>()

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun buatPesanan(userId: Int, breadId: Int, quantity: Int, pricePerItem: Int, latitude: String, longitude: String) {
        launch {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val pesanan = Pesanan(
                userId = userId,
                date = date,
                status = "pending",
                latitude = latitude,
                longitude = longitude
            )

            val pesananDb = PesananDatabase(getApplication())
            val pesananId = pesananDb.userDao().insertPesanan(pesanan).toInt()

            val subtotal = quantity * pricePerItem
            val detail = DetailPesanan(
                pesananId = pesananId,
                breadId = breadId,
                quantity = quantity,
                subtotal = subtotal
            )

            val detailDb = DetailPesananDatabase(getApplication())
            detailDb.breadDao().insertDetail(detail)
        }
    }

    fun getPesananByUser(userId: Int) {
        launch {
            val pesananList = PesananDatabase(getApplication())
                .userDao()
                .getPesananByUser(userId)
            pesananLD.postValue(pesananList)
        }
    }

    fun getDetailByPesanan(pesananId: Int) {
        launch {
            val details = DetailPesananDatabase(getApplication())
                .breadDao()
                .getDetailsByPesanan(pesananId)
            detailPesananLD.postValue(details)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
