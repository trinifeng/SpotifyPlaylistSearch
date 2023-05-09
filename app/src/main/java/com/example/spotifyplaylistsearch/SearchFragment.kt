package com.example.spotifyplaylistsearch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class SearchFragment : Fragment() {

    lateinit var playlistButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        playlistButton = view.findViewById(R.id.playlist_button)

        playlistButton.setOnClickListener {
            val intent = Intent(activity, Playlists::class.java)
            startActivity(intent)
        }
        return view
    }
}