package com.example.spotifyplaylistsearch

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Song(var songId: String,
           var title: String,
           var artist: String) {

    //private var URL = "https://api.spotify.com/v1/tracks/$songId"
    init {

    }

    fun sameSong(checkTitle: String, checkArtist: String): Boolean {
        return checkTitle.equals(title) && checkArtist.equals(artist)
    }
}