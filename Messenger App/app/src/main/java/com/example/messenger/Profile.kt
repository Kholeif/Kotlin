package com.example.messenger

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.ByteArrayOutputStream


class Profile : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mStorageRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR   // 3shan yezher elkalam beleswed badal elabyad
        setSupportActionBar(toolbar_profile)
        supportActionBar!!.title = "Me"
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAuth = FirebaseAuth.getInstance()
        mStorageRef = FirebaseStorage.getInstance().getReference().child(mAuth?.currentUser!!.uid)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun choose_photo(view: View) {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("images/jpeg", "image/png"))
        }
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageView3.setImageURI(data.data)
            upload_image_to_firebase(data)
        }
    }

    private fun upload_image_to_firebase(data: Intent) {
        val selected_image_path = data.data
        val selected_image_bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selected_image_path)
        val output_stream = ByteArrayOutputStream()
        selected_image_bitmap.compress(Bitmap.CompressFormat.JPEG , 20 , output_stream)
        val selected_image_bytes = output_stream.toByteArray()


    }
}