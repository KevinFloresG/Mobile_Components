package com.mobile.mobile_components.business_logic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.mobile.mobile_components.R
import com.mobile.mobile_components.data.DBHelper
import com.mobile.mobile_components.databinding.ActivityMainBinding
import com.mobile.mobile_components.databinding.NavigationDrawerHeaderBinding
import com.mobile.mobile_components.model.CurrentData
import com.mobile.mobile_components.model.Student
import com.mobile.mobile_components.recyler_views.recyclers.CoursesRecyclerFragment
import com.mobile.mobile_components.recyler_views.recyclers.StudentsRecyclerFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var db : DBHelper? = null
    private lateinit var binding : ActivityMainBinding
    private lateinit var bindingNav : NavigationDrawerHeaderBinding
    private lateinit var user : Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DBHelper(this)
        user = CurrentData.getCurrentUser()!!
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingNav = NavigationDrawerHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userTxt = "${user.name} ${user.lastname1} ${user.lastname2}\n" +
                "${user.id}\nEdad: ${user.age}"
        val headerView: View  = binding.navMenu.getHeaderView(0)
        val loggedUserTextView = headerView.findViewById(R.id.loggedUserLabel) as TextView
        loggedUserTextView.text = userTxt

        setSupportActionBar(binding.content.toolbar)
        val toggle = ActionBarDrawerToggle(this,
            binding.drawerLayout , binding.content.toolbar,
            R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val menu = binding.navMenu.menu
        binding.navMenu.setNavigationItemSelectedListener(this)
        if(user.id == "admin"){
            menu.findItem(R.id.matriculate_curse).isVisible = false
            menu.findItem(R.id.my_courses).title = "Cursos"
            changeFragment("Todos los Cursos", CoursesRecyclerFragment.newInstance(1))
        }else{
            menu.findItem(R.id.students).isVisible = false
            changeFragment("Mis Cursos Matriculados", CoursesRecyclerFragment.newInstance(2))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.matriculate_curse ->{
                changeFragment("Matricular Cursos", CoursesRecyclerFragment.newInstance(3))
            }
            R.id.my_courses ->{
                var t = "Mis Cursos Matriculados"
                var f = 2
                if(CurrentData.getCurrentUser()?.id == "admin"){
                    t = "Todos los Cursos"
                    f = 1
                }
                changeFragment(t, CoursesRecyclerFragment.newInstance(f))
            }
            R.id.students -> changeFragment("Estudiantes", StudentsRecyclerFragment())
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