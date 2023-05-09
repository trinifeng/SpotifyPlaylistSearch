package com.example.spotifyplaylistsearch

import android.content.SharedPreferences
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import org.json.JSONObject


class UserInfo(queue: RequestQueue,
               sharedPreferences: SharedPreferences) {

    private val URL = "https://api.spotify.com/v1/me"
    private var sharedPreferences: SharedPreferences
    private var queue: RequestQueue
    private lateinit var user: User
    lateinit var myEditor: SharedPreferences.Editor

    init {
        this.queue = queue
        this.sharedPreferences = sharedPreferences
    }

    fun getUser(): User {
        return user
    }

    operator fun get(callBack: VolleyCallBack) {
        // We use JsonObjectRequest method of volley library to
        // retrieve a JSONObject response body at a given URL
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(URL,
            null, Response.Listener { response: JSONObject ->
                // initialize the GSON library
                val gson = Gson()

                // serialize the response into user object
                user = gson.fromJson(
                    response.toString(),
                    User::class.java
                )

                // indicate successful call
                callBack.onSuccess()
            }, Response.ErrorListener { error: VolleyError? -> get(callBack) }) {
            // We need to add headers to the call for authorization
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
        // add the JSON object request call to
        // the queue of volley library to make the call
        queue.add(jsonObjectRequest)


    }

}