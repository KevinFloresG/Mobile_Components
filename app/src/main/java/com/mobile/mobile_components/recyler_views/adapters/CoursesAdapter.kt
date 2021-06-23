package com.mobile.mobile_components.recyler_views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.mobile_components.R
import com.mobile.mobile_components.model.Course

class CoursesAdapter(
    private var list : List<Course>
    ) : RecyclerView.Adapter<CoursesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_row, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val l = list[position]
        holder.id.text = l.id
        holder.description.text = "Descripción: ${l.description}"
        holder.credits.text = "Créditos: ${l.credits}"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: ArrayList<Course>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val id : TextView = view.findViewById(R.id.class_id)
        val description : TextView = view.findViewById(R.id.class_description)
        val credits : TextView = view.findViewById(R.id.class_credits)
    }
}