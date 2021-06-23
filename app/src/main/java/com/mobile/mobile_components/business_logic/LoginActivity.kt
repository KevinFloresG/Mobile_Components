package com.mobile.mobile_components.business_logic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mobile.mobile_components.data.DBHelper
import com.mobile.mobile_components.databinding.ActivityLoginBinding
import com.mobile.mobile_components.model.CurrentData

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var db : DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        binding.loginBtn.setOnClickListener {
            executeLogin()
        }
    }

    private fun executeLogin(){
        val username = binding.lUsername.text.toString()
        if(username.isBlank()){
            Toast.makeText(this, "Indique un Usuario", Toast.LENGTH_SHORT).show()
            return
        }
        val user = db?.getStudentById(username.trim())
        if(user == null){
            Toast.makeText(this, "Usuario no Registrado", Toast.LENGTH_SHORT).show()
            return
        }
        CurrentData.setCurrentUser(user)
        startActivity(Intent(this, MainActivity::class.java))
    }

}