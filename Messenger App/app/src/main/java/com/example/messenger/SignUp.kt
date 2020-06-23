package com.example.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity(), TextWatcher {

    var mAuth: FirebaseAuth? = null
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR   // 3shan yezher elkalam beleswed badal elabyad

        mAuth = FirebaseAuth.getInstance()

        editTextTextPersonName.addTextChangedListener(this)
        editTextTextEmailAddress.addTextChangedListener(this)
        editTextTextPassword.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        button2.isEnabled = editTextTextPersonName.text.trim().isNotEmpty()
                && editTextTextEmailAddress.text.trim().isNotEmpty()
                && editTextTextPassword.text.trim().isNotEmpty()
    }


    fun SignUp(view: View) {
        val name = editTextTextPersonName.text.toString().trim()
        val email = editTextTextEmailAddress.text.toString().trim()
        val password = editTextTextPassword.text.toString().trim()

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

        mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(applicationContext, "done sign up", Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE

                val new_user = User(name)
                val uid= mAuth!!.currentUser?.uid
                db.document("users/$uid").set(new_user)

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