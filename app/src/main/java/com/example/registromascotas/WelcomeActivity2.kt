package com.example.registromascotas

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome2)
        showSplash()
    }

    fun showSplash(){
        object : CountDownTimer(5000,1000){
            override fun onFinish() {
                val intent = Intent(this@WelcomeActivity2, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }.start()
    }

}