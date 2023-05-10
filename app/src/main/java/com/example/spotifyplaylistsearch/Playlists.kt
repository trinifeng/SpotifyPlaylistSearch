package com.example.spotifyplaylistsearch

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

//Class to display Playlists that a specific song is found in.

class Playlists : AppCompatActivity() {

    //endpoint to fetch playlists
    private var URL = "https://api.spotify.com/v1/me/playlists?limit=1&offset=0"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myEditor: SharedPreferences.Editor
    lateinit var nameOfPlaylist: TextView
    //lateinit var playlist: Playlist
    lateinit var nameOfSong: TextView
    lateinit var nameOfArtist: TextView
    lateinit var checkIfSongText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlists)

        nameOfPlaylist = findViewById(R.id.name_of_playlist)
        nameOfSong = findViewById(R.id.name_of_song)
        nameOfArtist = findViewById(R.id.name_of_artist)
        checkIfSongText = findViewById(R.id.check_if_song_text)

        sharedPreferences = this.getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE)

        nameOfSong.setText(sharedPreferences.getString("song_name", "No Song Found"))
        nameOfArtist.setText(sharedPreferences.getString("song_artist", "No Artist Found"))

        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)

        //fetching playlist data with JSON
        val accessTokenRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, URL, JSONObject(),
            Response.Listener<JSONObject?> {
                myEditor = sharedPreferences.edit()
                myEditor.apply {
                    var playlistId = ""
                    var playlistName = ""
                    val data = it.getJSONArray("items")
                    for (i in 0 until data.length()) {
                        val jsonObject1: JSONObject = data.getJSONObject(i)
                        playlistId = jsonObject1.optString("id")
                        playlistName = jsonObject1.optString("name")
                    }
                    /* HOW TO IMPLEMENT:
                    get playlist id
                    add each song in the playlist by id and name by "GetPlaylistItems"
                    */
                    myEditor.putString("playlist_id", playlistId)
                    myEditor.putString("playlist_name", playlistName)
                    commit()
                }
            }, Response.ErrorListener {
                Log.e("TAG", "RESPONSE IS $it")
                // in this case we are simply displaying a toast message.
                Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT)
                    .show()
            }) {

            //adding headers to authenticate
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()

                // get the auth token from shared preferences
                val token = sharedPreferences.getString("token", "")
                val auth = "Bearer $token"

                // add it in headers
                headers["Authorization"] = auth
                return headers
            }
        }

        requestQueue.add(accessTokenRequest)

        nameOfPlaylist.setText(sharedPreferences.getString("playlist_name", "No Playlist Found"))
        Toast.makeText(this, sharedPreferences.getString("playlist_name", "No Playlist Found"), Toast.LENGTH_SHORT).show()

        var songs = ArrayList<Song>()

        URL = "https://api.spotify.com/v1/playlists/${sharedPreferences.getString("playlist_id","none")}/tracks"

        //fetching playlist data with JSON
        val songTokenRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, URL, JSONObject(),
            Response.Listener<JSONObject?> {

                Toast.makeText(this, "fetching data...", Toast.LENGTH_SHORT).show()
                val data = it.getJSONArray("items")
                Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()
                for (i in 0 until data.length()) {
                    val jsonObject1: JSONObject = data.getJSONObject(i)
                    val trackData = jsonObject1.optJSONObject("track")
                    val songId = trackData.getString("id")
                    val songName = trackData.getString("name")
                    val artistData = trackData.getJSONArray("artists")
                    var artistName = ""
                    for (i in 0 until artistData.length()) {
                        val jsonObject1: JSONObject = artistData.getJSONObject(i)
                        artistName = jsonObject1.optString("name")
                    }
                    songs.add(Song(songId, songName, artistName))
                }
                for (i in 0..songs.size - 1) {
                    if(songs.get(i).sameSong(nameOfSong.text.toString(), nameOfArtist.text.toString())) {
                        checkIfSongText.setText("Song IS in playlist")
                        break
                    }
                }
            }, Response.ErrorListener {
                Log.e("TAG", "RESPONSE IS $it")
            }) {

            //adding headers to authenticate
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()

                // get the auth token from shared preferences
                val token = sharedPreferences.getString("token", "")
                val auth = "Bearer $token"

                // add it in headers
                headers["Authorization"] = auth
                return headers
            }
        }

        requestQueue.add(songTokenRequest)


    }
}