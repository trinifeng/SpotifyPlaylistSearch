package com.example.spotifyplaylistsearch

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class LoginScreen : AppCompatActivity() {

    lateinit var authButton: Button
    lateinit var requestQueue: RequestQueue
    val REQUEST_CODE = 1234
    val SCOPES = "user-read-email,user-read-private,playlist-modify-private,playlist-modify-public"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        authButton = findViewById(R.id.auth_button)

        //Initialize shared preferences and volley request queue
        sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE)
        requestQueue = Volley.newRequestQueue(this)

        //Authenticate Spotify app
        var builder = AuthorizationRequest.Builder(getString(R.string.CLIENT_ID), AuthorizationResponse.Type.TOKEN, getString(R.string.REDIRECT_URI))
        builder.setScopes(arrayOf(SCOPES))
        val request = builder.build()

        authButton.setOnClickListener {

            //Open Login Activity web page
            AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)
            //Toast.makeText(this, "Login was opened", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Check if result comes from Spotify login
        if (requestCode == REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, data)

            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    myEditor = sharedPreferences.edit()
                    myEditor.apply {
                        myEditor.putString("token", response.accessToken)
                        apply()
                    }
                    Toast.makeText(this, "Spotify login successful", Toast.LENGTH_SHORT).show()
                    Intent(this@LoginScreen, MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
                AuthorizationResponse.Type.ERROR -> Log.d("LoginActivity", response.error)
                else -> Log.d("LoginActivity", response.toString())
            }
        }
    }
}