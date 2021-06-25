package com.mobile.mobile_components.recyler_views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.mobile_components.R
import com.mobile.mobile_components.model.User

class StudentAdapter(
    private var list : List<User>
    ) : RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_row, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val l = list[position]/*
        holder.id.text = "Identificación: ${l.id}"
        holder.name.text = "${l.name} ${l.lastname1} ${l.lastname2}"
        holder.age.text = "Edad: ${l.age}"*/
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: ArrayList<User>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val id : TextView = view.findViewById(R.id.std_id)
        val name : TextView = view.findViewById(R.id.std_name)
        val age : TextView = view.findViewById(R.id.std_age)
    }
}