package com.example.spotifyplaylistsearch

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class SearchFragment : Fragment() {

    lateinit var playlistButton: Button
    lateinit var songTitle: EditText
    lateinit var artistName: EditText
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myEditor: SharedPreferences.Editor
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
        songTitle = view.findViewById(R.id.song_name)
        artistName = view.findViewById(R.id.artist_name)

        playlistButton.setOnClickListener {
            val intent = Intent(activity, Playlists::class.java)
            sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getString(R.string.shared_pref_key), MODE_PRIVATE)
            myEditor = sharedPreferences.edit()
            myEditor.apply {
                myEditor.putString("song_name", songTitle.text.toString())
                myEditor.putString("song_artist", artistName.text.toString())
                commit()
            }
            startActivity(intent)
        }
        return view
    }
}