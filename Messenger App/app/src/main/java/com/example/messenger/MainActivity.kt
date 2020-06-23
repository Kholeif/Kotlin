package com.example.messenger

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.messenger.Fragments.ChatFragment
import com.example.messenger.Fragments.MoreFragment
import com.example.messenger.Fragments.PeopleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var mAuth: FirebaseAuth? = null

    private val mChatFragment = ChatFragment()
    private val mPeopleFragment = PeopleFragment()
    private val mMoreFragment = MoreFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR   // 3shan yezher elkalam beleswed badal elabyad
        setSupportActionBar(toolbar)
        supportActionBar!!.title=""
        mAuth = FirebaseAuth.getInstance()
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        setFragment(mChatFragment)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.sign_out) {
            mAuth!!.signOut()
            val intent = Intent(this, SignIn::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.chat -> {
                setFragment(mChatFragment)
                return true
            }
            R.id.people -> {
                setFragment(mPeopleFragment)
                return true
            }
            R.id.more -> {
                setFragment(mMoreFragment)
                return true
            }
            else -> return false
        }
    }
    private fun setFragment(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.lallo , fragment)
        transaction.commit()
    }

    fun gotoprofile(view: View) {
        val intent = Intent(this,Profile::class.java)
        startActivity(intent)
    }
}