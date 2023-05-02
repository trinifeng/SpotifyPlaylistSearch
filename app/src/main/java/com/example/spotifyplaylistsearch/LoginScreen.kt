package com.example.spotifyplaylistsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class LoginScreen : AppCompatActivity() {

    lateinit var authButton: Button
    lateinit var requestQueue: RequestQueue
    val REQUEST_CODE = 1234
    val SCOPES = "user-read-email,user-read-private,playlist-modify-private,playlist-modify-public"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        authButton = findViewById(R.id.auth_button)

        //Initialize shared preferences and volley request queue
        val sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE)
        requestQueue = Volley.newRequestQueue(this)

        //Authenticate Spotify app

        authButton.setOnClickListener {
            val myEditor = sharedPreferences.edit()

            myEditor.apply {
                apply()
            }

        }
    }
}