package com.mobile.mobile_components.business_logic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mobile.mobile_components.databinding.ActivityRegistrationBinding
import com.mobile.mobile_components.model.CurrentData
import com.mobile.mobile_components.model.User

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btn = binding.btnRegister

        btn.setOnClickListener(View.OnClickListener {

            val i = Intent(this, LoginActivity::class.java)
            val name = binding.rName.text.toString()
            val lastName = binding.rLastName.text.toString()
            val email = binding.rEmail.text.toString()
            val phone = binding.rPhone.text.toString()
            val password = binding.rPassword.text.toString()
            val password2 = binding.rPassword2.text.toString()

            if(name == ""|| lastName == "" || phone == "" || email == "" || password == "" || password2 == "") {
                Toast.makeText(
                    this,
                    "Debe llenar todos los campos adecuadamente para completar el registro.",
                    Toast.LENGTH_LONG
                ).show()

                return@OnClickListener
            }

            if(CurrentData.getUserByEmail(email)!=null){
                Toast.makeText(
                    this,
                    "Ya existe un usuario con ese correo, utilice otro.",
                    Toast.LENGTH_LONG
                ).show()

                return@OnClickListener
            }

            if(password != password2){
                Toast.makeText(
                    this,
                    "Las contraseñas no coinciden.",
                    Toast.LENGTH_LONG
                ).show()

                return@OnClickListener
            }

            val user = User(email,password,name,lastName,phone)
            CurrentData.addUser(user)
            i.putExtra("msg", "Registro de Usuario Exitoso !")
            startActivity(i)

        })


    }
}