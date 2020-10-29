package com.example.collect_my_car

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity(){

    companion object{

        val MESSGAE = "Message"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {

            signIn()

        }

        not_signed_up.setOnClickListener {

            finish()

        }


    }

    private fun signIn(){

        val email = email_editText_login.text.toString()
        val password = password_editText_login.text.toString()

        Log.d("Login", "Attempt Login with Email / Password : $email/***")

        if(email.isEmpty()){

            Toast.makeText(this, "ERROR - Please Enter a Email Address", Toast.LENGTH_SHORT).show()

            return

        }

        else if(password.isEmpty()){

            Toast.makeText(this, "ERROR - Please Enter a Password", Toast.LENGTH_SHORT).show()

            return

        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)

            .addOnCompleteListener{

                if(!it.isSuccessful) return@addOnCompleteListener

                Log.d("Main", "Successfully Signed in with User with UID: ${it.result?.user!!.uid}")

                val intent = Intent(this, HomeActivity::class.java)

                intent.putExtra(MESSGAE, email)

                startActivity(intent)
            }

            .addOnFailureListener{

                Log.d("Main", "ERROR - Failed to Sign in with User : ${it.message}")

                Toast.makeText(this, "ERROR - Failed to Sign in with User : ${it.message}", Toast.LENGTH_SHORT).show()

            }
    }


}