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
    private var list : List<Sale>
    ) : RecyclerView.Adapter<SalesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sale_row, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val l = list[position]
        holder.id.text = l.id.toString()
        holder.description.text = "Descripción: ${l.desc}"
        holder.price.text = "Créditos: ${l.price}"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: ArrayList<Sale>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val id : TextView = view.findViewById(R.id.class_id)
        val description : TextView = view.findViewById(R.id.class_description)
        val price : TextView = view.findViewById(R.id.class_credits)
    }
}