package com.example.registromascotas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class CreateAccountActivity : AppCompatActivity() {

    private lateinit var nombreUsuario: EditText
    private lateinit var correoUsuario: EditText
    private lateinit var contrasenaUsuario: EditText
    private lateinit var contrasenaUsuario1: EditText

    private lateinit var nombreMascota: EditText

    private lateinit var btnCreateAccount: Button
    private lateinit var auth: FirebaseAuth

    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_createaccount)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            supportActionBar?.hide()
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initVariables()
        btnCreateAccount.setOnClickListener {
            Log.i("CreateAccount", "Se hizo click en el botón crear cuenta")
            validateData()
//          val intent = Intent(this, MainActivity::class.java)
//          startActivity(intent)
        }

    }

    private fun initVariables(){
        nombreUsuario = findViewById(R.id.campoNombre)
        correoUsuario = findViewById(R.id.campoCorreo)
        contrasenaUsuario = findViewById(R.id.campoContrasena)
        contrasenaUsuario1 = findViewById(R.id.campoContrasena1)
        nombreMascota = findViewById(R.id.campoNombreMascota)
        btnCreateAccount = findViewById(R.id.btnGuardar)
        auth = FirebaseAuth.getInstance()
    }

    private fun validateData(){
        val nombre = nombreUsuario.text.toString().trim()
        val correo = correoUsuario.text.toString().trim()
        val contrasena = contrasenaUsuario.text.toString().trim()
        val contrasena1 = contrasenaUsuario1.text.toString().trim()
        val nombreMascota1 = nombreMascota.text.toString().trim()
       // val email = emailUser.text.toString().trim()
       // val password =  passwordUser.text.toString().trim()
        //val confirmPaswword = password1User.text.toString().trim()

        when {
            nombre.isEmpty() -> {
                nombreUsuario.error = "Ingresa su nombre"
                nombreUsuario.requestFocus()
            }
            correo.isEmpty() -> {
                correoUsuario.error = "Ingresa correo electronico"
                correoUsuario.requestFocus()
            }

            contrasena.isEmpty() -> {
                contrasenaUsuario.error = "Ingresa la contraseña"
                contrasenaUsuario.requestFocus()
            }
            contrasena1.isEmpty() -> {
                contrasenaUsuario1.error = "Ingresa nuevamente contraseña"
                contrasenaUsuario1.requestFocus()
            }

            nombreMascota1.isEmpty() -> {
                nombreMascota.error = "Ingresa el nombre de su mascota"
                nombreMascota.requestFocus()
            }

            contrasena != contrasena1 -> {
                contrasenaUsuario1.error = "Contraseñas no coinciden"
                contrasenaUsuario1.requestFocus()
            }

            else ->{
                crearCuenta(correo, contrasena)
            }
        }
    }


    private fun crearCuenta(correo: String, contrasena: String) {
        auth.createUserWithEmailAndPassword(correo, contrasena).addOnSuccessListener {
            val uid = auth.currentUser?.uid ?: return@addOnSuccessListener
            reference = FirebaseDatabase.getInstance()
                .getReference("Usuarios")
                .child(uid)

            val nameText = nombreUsuario.text.toString().trim()
            val emailText = correoUsuario.text.toString().trim()
            val namePetText = nombreMascota.text.toString().trim()
            val userMap = hashMapOf(
                "uid" to uid,
                "username" to nameText,
                "email" to emailText,
                "petname" to namePetText,   //campo requerido nuevo nombre de mascota
                "search" to nameText.lowercase()
            )

            reference.setValue(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CreateAccountActivity, SuccessActivity::class.java)
                    //val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Database error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } .addOnFailureListener {
            //Log.e("RegisterActivity", "Auth error: ${it.message}")
            Toast.makeText(this, "Auth error: ${it.message}", Toast.LENGTH_SHORT).show()
        }

    }

}