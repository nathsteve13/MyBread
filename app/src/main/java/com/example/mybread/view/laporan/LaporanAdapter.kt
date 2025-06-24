package com.example.mybread.view.laporan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybread.R
import com.example.mybread.viewmodel.PesananWithDetail

class LaporanAdapter(
    private val laporanList: ArrayList<PesananWithDetail>,
    private val onAcceptClicked: (Int) -> Unit
) : RecyclerView.Adapter<LaporanAdapter.LaporanViewHolder>() {

    class LaporanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPesananId = view.findViewById<TextView>(R.id.txtPesananId)
        val txtUserId = view.findViewById<TextView>(R.id.txtUserId)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)
        val txtStatus = view.findViewById<TextView>(R.id.txtStatus)
        val layoutDetails = view.findViewById<LinearLayout>(R.id.layoutDetails)
        val btnAccept = view.findViewById<Button>(R.id.btnAccept)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laporan, parent, false)
        return LaporanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
        val item = laporanList[position]

        holder.txtPesananId.text = "ID: ${item.pesananId}"
        holder.txtUserId.text = "User: ${item.userId}"
        holder.txtDate.text = "Date: ${item.date}"
        holder.txtStatus.text = "Status: ${item.status}"

        // Kosongkan layout terlebih dahulu untuk menghindari duplikasi
        holder.layoutDetails.removeAllViews()

        val context = holder.itemView.context
        item.details.forEach { detail ->
            val detailView = LayoutInflater.from(context).inflate(R.layout.item_detail_pesanan, null)

            val txtBreadId = detailView.findViewById<TextView>(R.id.txtBreadId)
            val txtQty = detailView.findViewById<TextView>(R.id.txtDetailQuantity)
            val txtSubtotal = detailView.findViewById<TextView>(R.id.txtDetailSubtotal)

            txtBreadId.text = "Bread ID: ${detail.breadId}"
            txtQty.text = "Qty: ${detail.quantity}"
            txtSubtotal.text = "Subtotal: Rp ${detail.subtotal}"

            holder.layoutDetails.addView(detailView)
        }

        holder.btnAccept.visibility = if (item.status.lowercase() != "accepted") View.VISIBLE else View.GONE
        holder.btnAccept.setOnClickListener {
            onAcceptClicked(item.pesananId)
        }
    }

    override fun getItemCount(): Int = laporanList.size

    fun updateList(newList: List<PesananWithDetail>) {
        laporanList.clear()
        laporanList.addAll(newList)
        notifyDataSetChanged()
    }
}
