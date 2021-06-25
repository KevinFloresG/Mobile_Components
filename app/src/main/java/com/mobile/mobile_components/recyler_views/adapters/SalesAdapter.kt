package com.mobile.mobile_components.recyler_views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.mobile_components.R
import com.mobile.mobile_components.model.Sale

class SalesAdapter(
    private var list : List<Sale>,
    private val clickListener: ClickListener
    ) : RecyclerView.Adapter<SalesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sale_row, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sale = list[position]
        holder.product.text = sale.product
        holder.description.text = "Descripción: ${sale.desc}"
        holder.price.text = "Precio: ${sale.price}"
        holder.phone.text = "Contacto: ${sale.phone}"
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(sale)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: ArrayList<Sale>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val product : TextView = view.findViewById(R.id.product)
        val description : TextView = view.findViewById(R.id.description)
        val price : TextView = view.findViewById(R.id.price)
        val phone : TextView = view.findViewById(R.id.phone)
    }

    interface ClickListener{
        fun onItemClick(sale: Sale)
    }
}