package com.example.mybread.view.produk

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybread.R
import com.example.mybread.model.bread.Bread

class BreadAdapter(
    private val breadList: ArrayList<Bread>,
    private val onClick: (Bread) -> Unit
) : RecyclerView.Adapter<BreadAdapter.BreadViewHolder>() {

    inner class BreadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val txtDesc: TextView = view.findViewById(R.id.txtDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreadViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bread, parent, false)
        return BreadViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BreadViewHolder, position: Int) {
        val bread = breadList[position]
        holder.txtName.text = bread.name
        holder.txtPrice.text = "Rp ${bread.price}"
        holder.txtDesc.text = bread.desc

        holder.itemView.setOnClickListener {
            onClick(bread)
        }
    }

    override fun getItemCount(): Int = breadList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateBreadList(newList: List<Bread>) {
        breadList.clear()
        breadList.addAll(newList)
        notifyDataSetChanged()
    }
}
