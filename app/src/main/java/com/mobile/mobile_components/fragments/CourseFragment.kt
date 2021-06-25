package com.mobile.mobile_components.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mobile.mobile_components.R
import com.mobile.mobile_components.model.Course
import com.mobile.mobile_components.recyler_views.recyclers.CoursesRecyclerFragment

private const val TYPE = "type"
private const val ID = "id"
private const val DESC = "desc"
private const val CREDITS = "credits"

class CourseFragment : Fragment() {

    private var type : Int? = null
    private var id : String? = null
    private var desc : String? = null
    private var credits : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(TYPE)
            credits = it.getInt(CREDITS)
            desc = it.getString(DESC)
            id = it.getString(ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_course, container, false)
        if(type == 1) updateFields(view)
        view.findViewById<Button>(R.id.btn_std).setOnClickListener { doCourse(view) }
        return view
    }

    private fun updateFields(view: View){
        val id = view.findViewById<EditText>(R.id.edit_id)
        id.setText(this.id)
        id.isEnabled = false
        view.findViewById<EditText>(R.id.desc_edit).setText(desc)
        view.findViewById<EditText>(R.id.new_age).setText(credits.toString())
        view.findViewById<Button>(R.id.btn_std).text = "Editar"
    }

    private fun doCourse(view: View){
        val id = view.findViewById<EditText>(R.id.edit_id).text
        val desc = view.findViewById<EditText>(R.id.desc_edit).text
        val credits = view.findViewById<EditText>(R.id.new_age).text
        if(id.isBlank()){
            Toast.makeText(context, "Ingresa un Id", Toast.LENGTH_SHORT).show()
            return
        }
        if(desc.isBlank()){
            Toast.makeText(context, "Ingresa una Descripción", Toast.LENGTH_SHORT).show()
            return
        }
        if(credits.isBlank()){
            Toast.makeText(context, "Ingresa el valor en Créditos", Toast.LENGTH_SHORT).show()
            return
        }/*
        if(type == 0){
            if(db!!.getCourse(id.toString()) != null){
                Toast.makeText(context, "Id ya Existente", Toast.LENGTH_SHORT).show()
                return
            }
            db!!.addCourse(Course(id.toString(), desc.toString(), credits.toString().toInt()))
        }
        else
            db!!.updateCourse(Course(id.toString(), desc.toString(), credits.toString().toInt()))*/
        val fragment: Fragment = CoursesRecyclerFragment.newInstance(1)
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(c: Course?) =
            CourseFragment().apply {
                arguments = Bundle().apply {
                    if(c == null){
                        putInt(TYPE, 0)
                    }else{
                        putInt(TYPE, 1)
                        putInt(CREDITS, c.credits)
                        putString(ID, c.id)
                        putString(DESC, c.description)
                    }
                }
            }
    }
}