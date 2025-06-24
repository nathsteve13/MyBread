package com.example.mybread.view.pesan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybread.R
import com.example.mybread.model.detailpesanan.DetailPesanan

class DetailPesananAdapter(private val details: ArrayList<DetailPesanan>) :
    RecyclerView.Adapter<DetailPesananAdapter.DetailViewHolder>() {

    class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtBreadId = view.findViewById<TextView>(R.id.txtBreadId)
        val txtQty = view.findViewById<TextView>(R.id.txtDetailQuantity)
        val txtSubtotal = view.findViewById<TextView>(R.id.txtDetailSubtotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_pesanan, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = details[position]
        holder.txtBreadId.text = "Bread ID: ${item.breadId}"
        holder.txtQty.text = "Qty: ${item.quantity}"
        holder.txtSubtotal.text = "Subtotal: Rp ${item.subtotal}"
    }

    override fun getItemCount(): Int = details.size

    fun updateDetailList(newList: List<DetailPesanan>) {
        details.clear()
        details.addAll(newList)
        notifyDataSetChanged()
    }
}
