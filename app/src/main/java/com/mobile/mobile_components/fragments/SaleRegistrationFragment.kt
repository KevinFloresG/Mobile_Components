package com.mobile.mobile_components.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.mobile.mobile_components.R
import com.mobile.mobile_components.model.CurrentData
import com.mobile.mobile_components.model.Sale
import com.mobile.mobile_components.recyler_views.recyclers.SalesRecyclerFragment

class SaleRegistrationFragment : Fragment() {

    private var imgCapture: ImageView? = null
    private var client : FusedLocationProviderClient? = null
    private var longitude = 0.0
    private var latitude = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sale_registration, container, false)

        // ======= GPS ============
        client = LocationServices.getFusedLocationProviderClient(activity)
        if(ContextCompat.checkSelfPermission(
                requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(requireActivity()
                    ,Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation()
        }else{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), 100)
        }
        // ========================

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
                sDescription,sPrice.toDouble(),latitude,longitude, CurrentData.getCurrentUser()!!.phone.toInt()))
            val fragment = activity?.supportFragmentManager?.beginTransaction()
            fragment?.replace(R.id.fragment_container, frag)?.commit()
        })

        return rootView
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() &&
                grantResults[0]+grantResults[1] == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation()
        }else{
            Toast.makeText(context, "Se necesitan permisos", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        var lm : LocationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            client?.lastLocation?.addOnCompleteListener{
                var location = it.result
                if(location != null){
                    longitude = location.longitude
                    latitude = location.latitude
                    Toast.makeText(context, "Lat: ${latitude} Long: ${longitude}",
                        Toast.LENGTH_LONG).show()
                }else{
                    var locationRequest = LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setFastestInterval(1000)
                        .setNumUpdates(1)
                    var locationCB  = object : LocationCallback(){
                        override fun onLocationResult(locationResult: LocationResult?) {
                            locationResult ?: return
                            for (location in locationResult.locations){
                                longitude = location.longitude
                                latitude = location.latitude
                                Toast.makeText(context, "Lat: ${latitude} Long: ${longitude}",
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    client!!.requestLocationUpdates(locationRequest, locationCB, Looper.myLooper())
                }
            }
        }else{
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
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