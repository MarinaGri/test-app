package com.example.simple_app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private lateinit var pref: SharedPreferences
private const val APP_PREFERENCES = "user_storage"

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

        val userPhoto: ImageView = findViewById(R.id.profile_image)
        val tvFullName: TextView = findViewById(R.id.user_full_name)
        val tvEmail: TextView = findViewById(R.id.tv_email)

        val fullName = intent.getStringExtra("full_name")
        val email = intent.getStringExtra("email")
        val photo = intent.getIntExtra("photo", 0)
        userPhoto.setImageResource(photo)
        tvFullName.text = fullName
        tvEmail.text = email

        val exit: Button = findViewById(R.id.btn_exit)
        exit.setOnClickListener {
            pref.edit().clear().apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}