package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
    }
    override fun onStart() {
        super.onStart()

        val current_user = mAuth!!.currentUser
        if (current_user != null)
        {
            verify_email()
        }
    }






    fun SignUp(view: View) {
        val intent = Intent(this , SignUp::class.java)
        startActivity(intent)
    }

    fun Login(view: View) {
        val email = editTextTextEmailAddress.text.toString()
        val password = editTextTextPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()){
            progressBar2.visibility = View.VISIBLE
            mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful) {
                    progressBar2.visibility = View.GONE
                    verify_email()
                }
                else{
                    Toast.makeText(applicationContext,it.exception!!.message, Toast.LENGTH_LONG).show()
                    progressBar2.visibility = View.GONE
                }
            }
        }
        else{
            Toast.makeText(applicationContext,"Empty", Toast.LENGTH_LONG).show()
        }
    }
    fun verify_email(){
        val current_user = mAuth!!.currentUser
        if(current_user!!.isEmailVerified){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(applicationContext,"Verify your email first", Toast.LENGTH_LONG).show()
        }
    }
}