package com.example.spotifyplaylistsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginScreen : AppCompatActivity() {

    lateinit var authButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        authButton = findViewById(R.id.auth_button)

        authButton.setOnClickListener {

        }
    }
}