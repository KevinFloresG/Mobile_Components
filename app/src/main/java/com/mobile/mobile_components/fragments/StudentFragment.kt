package com.mobile.mobile_components.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mobile.mobile_components.R
import com.mobile.mobile_components.data.DBHelper
import com.mobile.mobile_components.model.Student
import com.mobile.mobile_components.recyler_views.recyclers.StudentsRecyclerFragment

class StudentFragment : Fragment() {

    private var db : DBHelper? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student, container, false)
        view.findViewById<Button>(R.id.btn_std).setOnClickListener { addStudent(view) }
        db = context?.let { DBHelper(it) }
        return view
    }

    private fun addStudent(view : View){
        val id = view.findViewById<EditText>(R.id.edit_id).text
        val name = view.findViewById<EditText>(R.id.new_name).text
        val last1 = view.findViewById<EditText>(R.id.lastname1).text
        val last2 = view.findViewById<EditText>(R.id.lastname2).text
        val age = view.findViewById<EditText>(R.id.new_age).text
        if(id.isBlank()){
            Toast.makeText(context, "Ingresa un Id", Toast.LENGTH_SHORT).show()
            return
        }
        if(name.isBlank()){
            Toast.makeText(context, "Ingresa un Nombre", Toast.LENGTH_SHORT).show()
            return
        }
        if(last1.isBlank()){
            Toast.makeText(context, "Ingresa el Primer Apellido", Toast.LENGTH_SHORT).show()
            return
        }
        if(last2.isBlank()){
            Toast.makeText(context, "Ingresa el Segundo Apellido", Toast.LENGTH_SHORT).show()
            return
        }
        if(age.isBlank()){
            Toast.makeText(context, "Ingresa la Edad", Toast.LENGTH_SHORT).show()
            return
        }
        if(db!!.getStudent(id.toString()) != null){
            Toast.makeText(context, "Id ya Existente", Toast.LENGTH_SHORT).show()
            return
        }
        else
            db!!.insertStudent(
                Student(id.toString(), name.toString(), last1.toString(), last2.toString(), age.toString().toInt())
            )
        val fragment: Fragment = StudentsRecyclerFragment()
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}