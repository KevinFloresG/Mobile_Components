package com.mobile.mobile_components.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mobile.mobile_components.R
import com.mobile.mobile_components.model.CurrentData
import com.mobile.mobile_components.model.Sale
import com.mobile.mobile_components.recyler_views.recyclers.SalesRecyclerFragment

class SaleRegistrationFragment : Fragment() {

    private var btnCapture: Button? = null
    private var imgCapture: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_sale_registration, container, false)

        val product = rootView.findViewById<View>(R.id.srProduct) as TextView
        val price = rootView.findViewById<View>(R.id.srPrice) as TextView
        val description = rootView.findViewById<View>(R.id.srDescription) as TextView
        imgCapture = rootView.findViewById<View>(R.id.srPPhoto) as ImageView

        val registerBtn = rootView.findViewById(R.id.registerSaleBtn) as Button
        val btnCapture = rootView.findViewById(R.id.addPhotoBtn) as Button
        btnCapture!!.setOnClickListener {
            val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cInt, Image_Capture_Code)
        }


        registerBtn.setOnClickListener(View.OnClickListener {
            val sProduct = product.text.toString()
            val sPrice = price.text.toString()
            val sDescription = description.text.toString()

            // Stop and show alert if an input is empty
            if( sProduct == "" || sPrice == "" || sDescription =="") {
                Toast.makeText(
                    context,
                    "Todos los campos deben estar llenos.",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }

            val frag = SalesRecyclerFragment()
            CurrentData.addSale(Sale(CurrentData.getSaleIdFromSequence(),sProduct,
                sDescription,sPrice.toDouble(),0.0,0.0, CurrentData.getCurrentUser()!!.phone.toInt()))
            val fragment = activity?.supportFragmentManager?.beginTransaction()
            fragment?.replace(R.id.fragment_container, frag)?.commit()
        })

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Image_Capture_Code) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val bp = data!!.extras!!["data"] as Bitmap?
                imgCapture!!.setImageBitmap(bp)
            } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val Image_Capture_Code = 1
    }


}