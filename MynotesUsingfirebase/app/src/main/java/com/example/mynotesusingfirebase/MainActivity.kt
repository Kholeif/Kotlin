package com.example.mynotesusingfirebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_note.view.*
import kotlinx.android.synthetic.main.add_note.view.button
import kotlinx.android.synthetic.main.add_note.view.editTextTextMultiLine
import kotlinx.android.synthetic.main.add_note.view.editTextTextPersonName
import kotlinx.android.synthetic.main.delete_update.*
import kotlinx.android.synthetic.main.delete_update.view.*
import kotlinx.android.synthetic.main.ticket.*
import kotlinx.android.synthetic.main.ticket.view.*
import kotlinx.android.synthetic.main.ticket.view.textView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var notes_list = ArrayList<note>()
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("Notes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listview.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick( parent: AdapterView<*>? , view: View? , position: Int , id: Long ) {
                val note = notes_list.get(position)
                val intent = Intent(applicationContext , Note_Activity::class.java)
                intent.putExtra("title",note.title)
                intent.putExtra("des",note.des)
                startActivity(intent)
            }
        }

        listview.onItemLongClickListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemLongClickListener {
            override fun onItemLongClick( parent: AdapterView<*>? , view: View? , position: Int , id: Long): Boolean {
                val alertBuilder = AlertDialog.Builder(this@MainActivity)
                val view = layoutInflater.inflate(R.layout.delete_update , null)
                alertBuilder.setView(view)
                val alertDialog = alertBuilder.create()

                val note = notes_list.get(position)
                val id = note.id
                view.editTextTextPersonName.setText(note.title)
                view.editTextTextMultiLine.setText(note.des)
                view.button.setOnClickListener{
                    val title = view.editTextTextPersonName.text.toString()
                    val des = view.editTextTextMultiLine.text.toString()
                    val updated_note = note(id!!,title,des,getCurrentDate())
                    myRef.child(id).setValue(updated_note)
                    alertDialog.dismiss()
                }

                view.button2.setOnClickListener{
                    myRef.child(id!!).removeValue()
                    alertDialog.dismiss()
                }

                alertDialog.show()
                return false
            }

            override fun onItemClick( parent: AdapterView<*>? , view: View? , position: Int , id: Long ) {}
        }
    }

    override fun onStart() {
        super.onStart()

        myRef.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {
                notes_list.clear()
                for(i in p0.children){
                    val note = i.getValue(note::class.java)
                    notes_list.add(0 , note!!)
                }
                val noteAdapter = NoteAdapter(applicationContext , notes_list)
                listview.adapter = noteAdapter
            }

            override fun onCancelled(p0: DatabaseError) {}

        })
    }





    fun add(view: View) {
        val alertBuilder = AlertDialog.Builder(this )
        val Add_note_view = layoutInflater.inflate(R.layout.add_note , null)
        alertBuilder.setView(Add_note_view)
        val alertDialog = alertBuilder.create()
        alertDialog.show()

        Add_note_view.button.setOnClickListener{
            val title = Add_note_view.editTextTextPersonName.text.toString()
            val des = Add_note_view.editTextTextMultiLine.text.toString()

            if(title.isNotEmpty() && des.isNotEmpty()) {
                val id = myRef.push().key
                val note = note(id!! , title , des , getCurrentDate())
                myRef.child(id).setValue(note)
                alertDialog.dismiss()
            }
            else{
                Toast.makeText(this,"Empty",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getCurrentDate() : String {
        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("EEEE hh:mm a ")
        val strdate = mdformat.format(calendar.time)
        return strdate
    }

}