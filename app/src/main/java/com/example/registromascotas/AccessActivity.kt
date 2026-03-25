package com.example.registromascotas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class AccessActivity : AppCompatActivity() {

    private lateinit var acsCorreo: EditText
    private lateinit var acsContrasena: EditText
    private lateinit var acsBtn: Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_access)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.title = "Entrada"
        initVariables()
        acsBtn.setOnClickListener {
            validateData()
        }
    }

    private fun initVariables(){
        acsCorreo = findViewById(R.id.correo_acceder)
        acsContrasena = findViewById(R.id.contrasena_acceder)
        acsBtn = findViewById(R.id.btn_acceder)
        auth = FirebaseAuth.getInstance()
    }

    private fun validateData() {
        val correoAcceso = acsCorreo.text.toString().trim()
        val contrasenaAcceso = acsContrasena.text.toString().trim()

        when {
            correoAcceso.isEmpty() -> {
                acsCorreo.error = "Ingresa tu correo"
                acsCorreo.requestFocus()
            }

            contrasenaAcceso.isEmpty() -> {
                acsContrasena.error = "Ingresa tu contraseña"
                acsContrasena.requestFocus()
            }

            else -> {
                loginUser(correoAcceso, contrasenaAcceso)
            }
        }
    }

    private fun loginUser(correoAcceso: String, contrasenaAcceso: String){
        auth.signInWithEmailAndPassword(correoAcceso, contrasenaAcceso)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Has iniciado sesión correctamente", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, SuccessActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    val errorMessage = task.exception?.message ?: "Error desconocido"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }

}