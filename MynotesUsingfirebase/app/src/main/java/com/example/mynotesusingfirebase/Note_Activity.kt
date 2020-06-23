package com.example.mynotesusingfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_note_.*

class Note_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_)

        textView3.text = intent.extras!!.getString("title")
        textView4.text = intent.extras!!.getString("des")
    }
}