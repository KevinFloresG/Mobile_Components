package com.mobile.mobile_components.business_logic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mobile.mobile_components.databinding.ActivityLoginBinding
import com.mobile.mobile_components.model.CurrentData

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.loginBtn.setOnClickListener {
            executeLogin()
        }

        val signInBtn = binding.signInBtn

        // Setting Sign In Button On Click Listener
        signInBtn.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegistrationActivity::class.java
                )
            )
        })
    }



    private fun executeLogin(){
        val username = binding.lUsername.text.toString()
        if(username.isBlank()){
            Toast.makeText(this, "Indique un Usuario", Toast.LENGTH_SHORT).show()
            return
        }
        val user = null //= db?.getStudentById(username.trim())
        if(user == null){
            Toast.makeText(this, "Usuario no Registrado", Toast.LENGTH_SHORT).show()
            return
        }
        CurrentData.setCurrentUser(user)
        startActivity(Intent(this, MainActivity::class.java))
    }

}