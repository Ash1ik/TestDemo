package com.example.testdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var etForgotPassword: TextView
    private lateinit var btnLogin : Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword : EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        btnLogin = findViewById(R.id.btnLogin)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)


        etForgotPassword = findViewById(R.id.etForgotPassword)


        etForgotPassword.setOnClickListener {
            intent = Intent(this,ResetPassword::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {

            val sEmail = etEmail.text.toString().trim()
            val sPassword = etPassword.text.toString().trim()

            auth.signInWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val verification = auth.currentUser?.isEmailVerified

                        if(verification == true){
                            val user = auth.currentUser
                            updateUI()
                        }
                        else{
                            Toast.makeText(this,"Please verify your Email",Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //updateUI(null)
                    }
                }

        }
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }



    }




    private fun updateUI() {
        val intent = Intent(this,Dashboard::class.java)
        startActivity(intent)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI()
        }
    }
}