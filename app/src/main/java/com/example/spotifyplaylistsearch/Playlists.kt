package com.example.spotifyplaylistsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//Class to display Playlists that a specific song is found in.

class Playlists : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlists)
    }
}