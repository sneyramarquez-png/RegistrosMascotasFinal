package com.example.registromascotas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AuthActivity : AppCompatActivity() {

    private lateinit var btnCrearCuenta: Button
    private lateinit var btnAcceder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Funcionalidades del boton de creacion de cuentas y acceder
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        btnAcceder = findViewById(R.id.btnAcceder)

        btnCrearCuenta.setOnClickListener {
            navigateTo(CreateAccountActivity::class.java,"Creacion de cuenta")
        }
        btnAcceder.setOnClickListener {
            navigateTo(AccessActivity::class.java, "Ha iniciado sesion")
        }


    }

    private fun navigateTo(activity: Class<*>, mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, activity))
    }
}