package com.example.testdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.SipAddress
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Signup : AppCompatActivity() {

    private lateinit var tvEmail: EditText
    private lateinit var tvPassword : EditText
    private lateinit var btnRegister : Button
    private lateinit var tvAddress : EditText
    private lateinit var tvName : EditText


    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth
        auth = Firebase.auth

        tvEmail = findViewById(R.id.tvEmail)
        tvPassword = findViewById(R.id.tvPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {

            val sEmail = tvEmail.text.toString().trim()
            val sPassword = tvPassword.text.toString().trim()


            auth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        saveData()
                        auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {

                            Toast.makeText(this,"Please Verify your Email",Toast.LENGTH_SHORT).show()


                        }?.addOnFailureListener{
                            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                        }


                        val user = auth.currentUser
                        updateUI(user)
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

    }


    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this,Dashboard::class.java)
        startActivity(intent)
    }


    private fun saveData(){

        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        tvAddress = findViewById(R.id.tvAddress)
        tvPassword = findViewById(R.id.tvPassword)
        btnRegister = findViewById(R.id.btnRegister)



            val sName = tvName.text.toString().trim()
            val sEmail = tvEmail.text.toString().trim()
            val sAddress = tvAddress.text.toString().trim()
            val sPassword = tvPassword.text.toString().trim()

            val userMap = hashMapOf(
                "name" to sName,
                "address" to sAddress,
                "email" to sEmail,
                "password" to sPassword
            )

            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            db.collection("user").document(userId).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()

                    tvName.text.clear()
                    tvAddress.text.clear()
                    tvEmail.text.clear()
                    tvPassword.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }



    }



}


