package com.example.registromascotas

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SuccessActivity : AppCompatActivity() {

    private lateinit var txtNombreUsuario: TextView
    private lateinit var txtNombreMascota: TextView

   // private lateinit var txtInicial: TextView

    private lateinit var txtInicialToolbar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_success)
        //supportActionBar?.hide()

        // Configurar Toolbar
       // val toolbar = findViewById<Toolbar>(R.id.toolbar)
      //  setSupportActionBar(toolbar)
        val toolbar = findViewById<Toolbar>(R.id.toolbarSuccess)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#7DCFB6")))
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        initVariables()
        showData()
    }


    // Adicion de menu superior
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    // Aumentar parte opcion de cerar sesion en parte superior
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                //deslogueo
                FirebaseAuth.getInstance().signOut()
                val  intent = Intent(this@SuccessActivity, SplashActivity::class.java)
                //val intent = Intent(this@MainActivity, StartActivity::class.java)
                Toast.makeText(applicationContext, "Saliste de tu sesion", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun initVariables(){
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario)
        txtNombreMascota = findViewById(R.id.txtNombreMascota)
        txtInicialToolbar = findViewById(R.id.txtInicialToolbar)
        //txtInicial = findViewById(R.id.txtInicial)
    }

    private fun showData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return



        val reference = FirebaseDatabase.getInstance()
            .getReference("Usuarios")
            .child(uid)

        reference.get().addOnSuccessListener {
            val nombreUser = it.child("username").value.toString()
            val nombrePet = it.child("petname").value.toString()
            //avatar de inicio de sesion
            val inicial = nombreUser.firstOrNull()?.toString()?.uppercase()

            // Mostrar datos
            txtNombreUsuario.text = "Bienvenid@, $nombreUser" + " " + "✨"
            txtNombreMascota.text = "Mascota registrada: $nombrePet" + " "+"\uD83D\uDC3E"

            // Inicial (avatar)
            //txtInicial.text = inicial
           // txtInicialToolbar.text = "S"
            txtInicialToolbar.text = inicial

        }.addOnFailureListener {
            txtNombreUsuario.text = "Error al cargar datos de usuario"
            txtNombreMascota.text = "Error al cargar datos de mascota"
        }
    }


}