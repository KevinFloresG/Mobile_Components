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
import com.mobile.mobile_components.fragments.CourseFragment
import com.mobile.mobile_components.model.Sale
import com.mobile.mobile_components.model.CurrentData
import com.mobile.mobile_components.recyler_views.adapters.SalesAdapter

private const val FILTER_TYPE = "filter_type"

class CoursesRecyclerFragment : Fragment() {

    private var filter: Int? = null
    private lateinit var adapter: SalesAdapter
    private var db : DBHelper? = null
    private lateinit var list : ArrayList<Sale>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filter = it.getInt(FILTER_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sales_recycler, container, false)
        btnAddCourse(view)
        initRecycler(view)
        return view
    }

    private fun btnAddCourse(view: View){
        if(filter != 1)
            view.findViewById<Button>(R.id.add_course).visibility = View.INVISIBLE
        else
            view.findViewById<Button>(R.id.add_course).setOnClickListener {
                //changeFrag(null)
            }
    }

    private fun initRecycler(view: View){
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_courses)
        recycler.layoutManager = LinearLayoutManager(activity)
        db = context?.let { DBHelper(it) }
      /*  when(filter){
            1 -> list = db!!.getAllCourses()
            2 -> list = db!!.getCoursesByStudent(CurrentData.getCurrentUser()!!.id)
            3 -> list = db!!.getNotCoursesByStudent(CurrentData.getCurrentUser()!!.id)
        }*/
        list = ArrayList();
        list.add(Sale(1,"Papitas","Estan Ricas", 3.00, 123.0, 12.0, 82811234 ))
        list.add(Sale(2,"Papita","Estan Ricas", 3.00, 123.0, 12.0, 82811234 ))
        list.add(Sale(3,"Papit","Estan Ricas", 3.00, 123.0, 12.0, 82811234 ))
        list.add(Sale(4,"Papi","Estan Ricas", 3.00, 123.0, 12.0, 82811234 ))
        list.add(Sale(5,"Pap","Estan Ricas", 3.00, 123.0, 12.0, 82811234 ))
        adapter = SalesAdapter(list)
        recycler.adapter = adapter

        val search = view.findViewById<EditText>(R.id.search_courses)
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })

        var itemSwipe = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean { return false }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.RIGHT -> showDialog(viewHolder)
                    //ItemTouchHelper.LEFT -> goToUpdate(viewHolder)
                }
            }
        }
        var swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(recycler)
    }

    private fun goToUpdate(viewHolder: RecyclerView.ViewHolder){
        if(filter==1)
            //changeFrag(list[viewHolder.adapterPosition])
        adapter.notifyItemChanged(viewHolder.adapterPosition)
    }
/*
    private fun changeFrag(course: Sale?){
        val fragment: Fragment = CourseFragment.newInstance(course)
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.hide(this)
        transaction.add(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }*/

    private fun showDialog(viewHolder: RecyclerView.ViewHolder){
        val builder = AlertDialog.Builder(activity)
        var title = ""; var msg = "";
        when(filter){
            1 -> {
                title = "Eliminar Curso"
                msg = "¿Está seguro de Eliminar este Curso?"
            }
            2 -> {
                title = "Desmatricular Curso"
                msg = "¿Está seguro de Desmatricular este Curso?"
            }
            3 -> {
                title = "Matricular Curso"
                msg = "¿Está seguro de Matricular este Curso?"
            }
        }
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton("Confirmar"){ _, _ ->
            val position = viewHolder.adapterPosition
           /* when(filter){
                1 -> db!!.deleteCourse(list[position].id)
                2 -> db!!.unregisterCourse(CurrentData.getCurrentUser()!!.id, list[position].id)
                3 -> db!!.registerCourse(CurrentData.getCurrentUser()!!.id, list[position].id)
            }*/
            list.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
        builder.setNegativeButton("Cancelar"){ _, _ ->
            adapter.notifyItemChanged(viewHolder.adapterPosition)
        }
        builder.show()
    }

    private fun filter(string: String){
        val filtered = ArrayList<Sale>()
        list.forEach{
            if (it.id.toString().lowercase().contains(string.lowercase())){
                filtered.add(it)
            }
        }
        adapter.updateList(filtered)
    }

    companion object {
        @JvmStatic
        fun newInstance(filter : Int) =
            CoursesRecyclerFragment().apply {
                arguments = Bundle().apply {
                    putInt(FILTER_TYPE, filter)
                }
            }
    }
}