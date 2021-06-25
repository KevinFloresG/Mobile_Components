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

        val bundle = intent.extras
        val msg = bundle?.getString("msg")
        // Show message if this activity was started by another. For example if a user was registered without problems or
        // if the user logs out.
        if (msg != null) {
            Toast.makeText(this, "$msg", Toast.LENGTH_LONG).show()
        }
    }



    private fun executeLogin(){
        val username = binding.lUsername.text.toString()
        val password = binding.lPassword.text.toString()

        if(username.isBlank() || password.isBlank()){
            Toast.makeText(this, "Uno de los campos esta vacío.", Toast.LENGTH_SHORT).show()
            return
        }

        val user = CurrentData.login(username,password)
        if(user == null){
            Toast.makeText(this, "El correo, la clave o ambos son incorrectos.", Toast.LENGTH_SHORT).show()
            return
        }
        CurrentData.setCurrentUser(user)
        startActivity(Intent(this, MainActivity::class.java))
    }

}