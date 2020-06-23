package com.example.firebase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.analytics.FirebaseAnalytics
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mAuth: FirebaseAuth?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        //Authentication
        mAuth = FirebaseAuth.getInstance()
    }

    fun Login(view: View) {
        val email = editText.text.toString()
        val password = editText2.text.toString()
        loginToFirebase(email,password)
    }

    fun loginToFirebase(email:String , password:String){
        mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"done", Toast.LENGTH_LONG).show()
                val currentUser =mAuth!!.currentUser
                Log.d("Login",currentUser!!.uid)
            }else{
                Toast.makeText(applicationContext,"failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}