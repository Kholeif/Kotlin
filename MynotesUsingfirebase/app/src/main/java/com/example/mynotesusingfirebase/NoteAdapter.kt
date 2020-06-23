package com.example.mynotesusingfirebase

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.ticket.view.*

class NoteAdapter(context: Context , notelist:ArrayList<note>) : ArrayAdapter<note>(context,0,notelist) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.ticket,parent,false)
        val note = getItem(position)
        view.textView.text=note!!.title
        view.textView2.text=note.timestamp
        return view
    }
}