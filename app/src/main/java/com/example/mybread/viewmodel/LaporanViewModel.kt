package com.example.mybread.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mybread.model.detailpesanan.DetailPesanan
import com.example.mybread.model.detailpesanan.DetailPesananDatabase
import com.example.mybread.model.pesanan.PesananDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

data class PesananWithDetail(
    val pesananId: Int,
    val userId: Int,
    val date: String,
    val status: String,
    val details: List<DetailPesanan>
)

class LaporanViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val laporanLD = MutableLiveData<List<PesananWithDetail>>()
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun fetchAllLaporan() {
        launch {
            val pesananDao = PesananDatabase(getApplication()).userDao()
            val detailDao = DetailPesananDatabase(getApplication()).breadDao()

            val allPesanan = pesananDao.show()
            val laporan = allPesanan.map { pesanan ->
                val details = detailDao.getDetailsByPesanan(pesanan.id)
                PesananWithDetail(
                    pesananId = pesanan.id,
                    userId = pesanan.userId,
                    date = pesanan.date,
                    status = pesanan.status,
                    details = details
                )
            }

            laporanLD.postValue(laporan)
        }
    }

    fun updateStatus(pesananId: Int, newStatus: String) {
        launch {
            PesananDatabase(getApplication()).userDao().updateStatus(pesananId, newStatus)
            fetchAllLaporan()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
