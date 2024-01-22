package com.example.testdemo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Dashboard : AppCompatActivity() {

    private lateinit var logout : Button
    private lateinit var btnSaveData : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        logout = findViewById(R.id.btnLogout)

        logout.setOnClickListener {
            Firebase.auth.signOut()

            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }


    }
}