package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.editTextTextEmailAddress
import kotlinx.android.synthetic.main.activity_sign_up.editTextTextPassword

class SignUp : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()
    }


    fun Register(view: View) {
        val email = editTextTextEmailAddress.text.toString()
        val password = editTextTextPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()){
            progressBar.visibility = View.VISIBLE
            mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful) {
                    send_email_verification()
                }
                else{
                    Toast.makeText(applicationContext,it.exception.toString(), Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            }
        }
        else{
            Toast.makeText(applicationContext,"Empty", Toast.LENGTH_LONG).show()
        }
    }
    fun send_email_verification(){
        val current_user = mAuth!!.currentUser
        current_user!!.sendEmailVerification().addOnCompleteListener{
            if(it.isSuccessful){
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext,it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}