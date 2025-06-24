package com.example.mybread.view.pesan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybread.R
import com.example.mybread.model.pesanan.Pesanan

class PesananAdapter(
    private val pesananList: ArrayList<Pesanan>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<PesananAdapter.PesananViewHolder>() {

    class PesananViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtId = view.findViewById<TextView>(R.id.txtPesananId)
        val txtDate = view.findViewById<TextView>(R.id.txtPesananDate)
        val txtStatus = view.findViewById<TextView>(R.id.txtPesananStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pesanan, parent, false)
        return PesananViewHolder(view)
    }

    override fun onBindViewHolder(holder: PesananViewHolder, position: Int) {
        val pesanan = pesananList[position]
        holder.txtId.text = "ID: ${pesanan.id}"
        holder.txtDate.text = pesanan.date
        holder.txtStatus.text = pesanan.status
        holder.itemView.setOnClickListener { onClick(pesanan.id) }
    }

    override fun getItemCount(): Int = pesananList.size

    fun updatePesananList(newList: List<Pesanan>) {
        pesananList.clear()
        pesananList.addAll(newList)
        notifyDataSetChanged()
    }
}
