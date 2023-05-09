package com.example.spotifyplaylistsearch

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    private val URL = "https://api.spotify.com/v1/me/playlists?limit=1&offset=0"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var myEditor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlists)

        sharedPreferences = this.getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE)

        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)

        //fetching playlist data with JSON
        val accessTokenRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, URL, JSONObject(),
            Response.Listener<JSONObject?> {
                myEditor = sharedPreferences.edit()
                myEditor.apply {
                    myEditor.putInt("playlist_limit", it.getInt("limit"))
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
        //check to make sure we are fetching the correct playlist data - we are, i just need to figure out how to parse it
        Toast.makeText(this, sharedPreferences.getInt("playlist_limit", 0).toString(), Toast.LENGTH_SHORT).show()

        requestQueue.add(accessTokenRequest)
    }
}