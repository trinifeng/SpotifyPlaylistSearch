package com.example.spotifyplaylistsearch

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var usernameText: TextView
    lateinit var emailText: TextView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var frameLayout: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameText = findViewById(R.id.username_text)
        emailText = findViewById(R.id.email_text)
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        frameLayout = findViewById(R.id.frame_layout)

        sharedPreferences = this.getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE)
        var name = sharedPreferences.getString("display_name", "Couldn't display name")
        var email = sharedPreferences.getString("email", "Couldn't display email")

        usernameText.setText("Name: " + name)
        emailText.setText("Email: " + email)
    }
}