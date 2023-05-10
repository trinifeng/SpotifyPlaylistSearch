package com.example.spotifyplaylistsearch

import android.content.Context
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

class Playlist(val playlistId: String,
               val name: String,
               val context: Context) {
    private var URL = "https://api.spotify.com/v1/playlists/$playlistId/tracks"
    var songs: ArrayList<Song> = ArrayList<Song>()
    init {

        val requestQueue: RequestQueue = Volley.newRequestQueue(context)

        //fetching playlist data with JSON
        val accessTokenRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, URL, JSONObject(),
            Response.Listener<JSONObject?> {

                val data = it.getJSONArray("items")
                System.out.println(data.toString())
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
            }, Response.ErrorListener {
                Log.e("TAG", "RESPONSE IS $it")
            }) {

            //adding headers to authenticate
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val sharedPreferences = context.getSharedPreferences("Spotify", AppCompatActivity.MODE_PRIVATE)

                // get the auth token from shared preferences
                val token = sharedPreferences.getString("token", "")
                val auth = "Bearer $token"

                // add it in headers
                headers["Authorization"] = auth
                return headers
            }
        }

        requestQueue.add(accessTokenRequest)


    }
}