package com.mobile.mobile_components.recyler_views.recyclers

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.mobile_components.R
import com.mobile.mobile_components.model.CurrentData
import com.mobile.mobile_components.model.Sale
import com.mobile.mobile_components.recyler_views.adapters.SalesAdapter

class SalesRecyclerFragment : Fragment(), SalesAdapter.ClickListener {

    private lateinit var adapter: SalesAdapter
    private var list = CurrentData.getSales()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sales_recycler, container, false)
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.SEND_SMS), 1)
        initRecycler(view)
        return view
    }

    private fun initRecycler(view: View){
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_courses)
        recycler.layoutManager = LinearLayoutManager(activity)
        adapter = SalesAdapter(list, this)
        recycler.adapter = adapter

        val search = view.findViewById<EditText>(R.id.search)
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
                    ItemTouchHelper.RIGHT -> doCall(viewHolder)
                    ItemTouchHelper.LEFT -> showDialog(viewHolder)
                }
            }
        }
        var swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(recycler)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                adapter.updateList(list)
            }
        }
    }

    private fun doCall(viewHolder: RecyclerView.ViewHolder){
        adapter.notifyItemChanged(viewHolder.adapterPosition)
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CALL_PHONE),
                        101
                    )
                    return
                }
            }
            val callIntent = Intent(Intent.ACTION_CALL)
            val phone = list[viewHolder.adapterPosition].phone.toString()
            callIntent.data = Uri.parse("tel:${phone}")
            startActivity(callIntent)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun sendMsg(msg : String, sale : Sale){
        try {
            val sm = SmsManager.getDefault()
            sm.sendTextMessage(
                sale.phone.toString(),
                null,
                msg,
                null,
                null
            )
            Toast.makeText(context, "Mensaje enviado exitosamente", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Falló el encio del mensaje, intente de nuevo.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showDialog(viewHolder: RecyclerView.ViewHolder){
        val alert = AlertDialog.Builder(activity)
        alert.setTitle("Enviar un Mensaje")
        val input = EditText(context)
        alert.setView(input)
        alert.setPositiveButton("Enviar") { _, _ ->
            val sale = list[viewHolder.adapterPosition]
            sendMsg(input.text.toString(), sale)
            adapter.notifyItemChanged(viewHolder.adapterPosition)
        }
        alert.setNegativeButton("Cancelar") { _, _ ->
            adapter.notifyItemChanged(viewHolder.adapterPosition)
        }
        alert.show()
    }

    private fun filter(string: String){
        val filtered = ArrayList<Sale>()
        list.forEach{
            if (it.product.lowercase().contains(string.lowercase())){
                filtered.add(it)
            }
        }
        adapter.updateList(filtered)
    }

    override fun onItemClick(sale: Sale) {
        val alert = AlertDialog.Builder(activity)
        alert.setTitle("Ubicación")
        alert.setMessage("Longitud: ${sale.longitude}\nLatitude: ${sale.latitude}")
        alert.setNegativeButton("Cancelar") { _, _ ->}
        alert.show()
    }

}