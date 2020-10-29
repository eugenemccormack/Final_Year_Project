package com.example.collect_my_car

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

         override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


            register_button_register.setOnClickListener {

                register()

            }

             not_signed_up.setOnClickListener {

                 Log.d("MainActivity", "Show Login Activity")

                 val intent = Intent(this, LoginActivity::class.java)

                 startActivity(intent)

             }

    }

    private fun register(){

        val username = username_editText_register.text.toString()
        val email = email_editText_register.text.toString()
        val password = password_editText_register.text.toString()

        if(email.isEmpty()){

            Toast.makeText(this, "ERROR - Please Enter a Email Address", Toast.LENGTH_SHORT).show()

            return

        }

        else if(password.isEmpty()){

            Toast.makeText(this, "ERROR - Please Enter a Password", Toast.LENGTH_SHORT).show()

            return

        }

        Log.d("MainActivity", "Email is : " + email)
        Log.d("MainActivity", "Password is : $password")

        //Firebase Authentication to Create a User with Email and Password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{

                if(!it.isSuccessful) return@addOnCompleteListener

                val uid = FirebaseAuth.getInstance().uid ?: ""
                val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

                val user = User(uid, username_editText_register.text.toString(), email_editText_register.text.toString())

                ref.setValue(user)

                Log.d("Main", "Successfully Create User with UID: ${it.result?.user!!.uid}")
            }

            .addOnFailureListener{

                Log.d("Main", "ERROR - Failed to Create User : ${it.message}")

                Toast.makeText(this, "ERROR - Fail to Create User : ${it.message}", Toast.LENGTH_SHORT).show()

            }
    }

    private class User(val uid: String, val username: String, val email: String) {

    }
}