package com.mobile.mobile_components.recyler_views.recyclers

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.mobile_components.R
import com.mobile.mobile_components.data.DBHelper
import com.mobile.mobile_components.fragments.StudentFragment
import com.mobile.mobile_components.model.Student
import com.mobile.mobile_components.recyler_views.adapters.StudentAdapter

class StudentsRecyclerFragment : Fragment() {

    private lateinit var adapter: StudentAdapter
    private var db : DBHelper? = null
    private lateinit var list : ArrayList<Student>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_students_recycler, container, false)
        view.findViewById<Button>(R.id.add_student).setOnClickListener { changeFrag() }
        initRecycler(view)
        return view
    }

    private fun changeFrag(){
        val fragment: Fragment = StudentFragment()
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.hide(this)
        transaction.add(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initRecycler(view: View){
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_students)
        recycler.layoutManager = LinearLayoutManager(activity)
        db = context?.let { DBHelper(it) }
        list = db!!.getAllStudents()
        adapter = StudentAdapter(list)
        recycler.adapter = adapter

        val search = view.findViewById<EditText>(R.id.search_students)
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })

        var itemSwipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean { return false }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDialog(viewHolder)
            }
        }
        var swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(recycler)
    }

    private fun filter(string: String){
        val filtered = ArrayList<Student>()
        var name : String
        list.forEach{
            name = "${it.name} ${it.lastname1} ${it.lastname2}"
            if (name.lowercase().contains(string.lowercase())){
                filtered.add(it)
            }
        }
        adapter.updateList(filtered)
    }

    private fun showDialog(viewHolder: RecyclerView.ViewHolder){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Eliminar Estudiante")
        builder.setMessage("¿Está seguro de Eliminar este Estudiante?")
        builder.setPositiveButton("Confirmar"){ _, _ ->
            val position = viewHolder.adapterPosition
            db!!.deleteStudent(list[position].id)
            list.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
        builder.setNegativeButton("Cancelar"){ _, _ ->
            adapter.notifyItemChanged(viewHolder.adapterPosition)
        }
        builder.show()
    }

}