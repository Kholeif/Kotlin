package com.example.messenger

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity(), TextWatcher {

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR   // 3shan yezher elkalam beleswed badal elabyad

        mAuth = FirebaseAuth.getInstance()
        editTextTextEmailAddress.addTextChangedListener(this)
        editTextTextPassword.addTextChangedListener(this)
    }
    override fun onStart() {
        super.onStart()
        val current_user = mAuth!!.currentUser
        if (current_user != null) {
//            verify_email()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        button.isEnabled = editTextTextEmailAddress.text.trim().isNotEmpty()
                && editTextTextPassword.text.trim().isNotEmpty()
    }

    fun gotoSignUp(view: View) {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }

    fun SignIn(view: View) {
        val email = editTextTextEmailAddress.text.toString()
        val password = editTextTextPassword.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextTextEmailAddress.error = "Please enter a valid email"
            editTextTextEmailAddress.requestFocus()
            return
        }
        if (password.length < 6) {
            editTextTextPassword.error = "6 characters required please"
            editTextTextPassword.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE
        mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                progressBar.visibility = View.GONE
//                    verify_email()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, it.exception!!.message, Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }
        }

    }

}