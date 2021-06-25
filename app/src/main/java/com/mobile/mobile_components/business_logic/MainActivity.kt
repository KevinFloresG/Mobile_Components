package com.mobile.mobile_components.business_logic

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.mobile.mobile_components.R
import com.mobile.mobile_components.databinding.ActivityMainBinding
import com.mobile.mobile_components.databinding.NavigationDrawerHeaderBinding
import com.mobile.mobile_components.fragments.SaleRegistrationFragment
import com.mobile.mobile_components.model.CurrentData
import com.mobile.mobile_components.model.User
import com.mobile.mobile_components.recyler_views.recyclers.SalesRecyclerFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var bindingNav : NavigationDrawerHeaderBinding
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //user = CurrentData.getCurrentUser()!!
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingNav = NavigationDrawerHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*
        val userTxt = "${user.name} ${user.lastname} \n" +
                "${user.email}\n"
        val headerView: View  = binding.navMenu.getHeaderView(0)
        val loggedUserTextView = headerView.findViewById(R.id.loggedUserLabel) as TextView
        loggedUserTextView.text = userTxt
        */
        setSupportActionBar(binding.content.toolbar)
        val toggle = ActionBarDrawerToggle(this,
            binding.drawerLayout , binding.content.toolbar,
            R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val menu = binding.navMenu.menu
        binding.navMenu.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.garage_sales->{
                changeFragment("Ventas de Garage", SalesRecyclerFragment())
            }
            R.id.register_sale->{
                changeFragment("Registrar venta",SaleRegistrationFragment())
            }
            R.id.logOut -> logOut()
            else -> return false
        }
        return true
    }

    private fun changeFragment(title : String, frag : Fragment){
        supportActionBar?.title = title
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, frag).commit()
    }

    private fun logOut(){
        CurrentData.setCurrentUser(null)
        startActivity(Intent(this, LoginActivity::class.java))
    }
}