package com.example.messenger.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.messenger.R

class PeopleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val ourtitle = activity!!.findViewById<TextView>(R.id.our_title)
        ourtitle.text="People"
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people, container, false)
    }
}