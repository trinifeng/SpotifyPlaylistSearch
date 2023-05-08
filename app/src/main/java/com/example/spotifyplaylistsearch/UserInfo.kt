package com.example.spotifyplaylistsearch

import android.content.SharedPreferences
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import org.json.JSONObject


class UserInfo(queue: RequestQueue,
               sharedPreferences: SharedPreferences) {

    private val URL = "https://api.spotify.com/v1/me"
    private var sharedPreferences: SharedPreferences
    private var queue: RequestQueue
    private lateinit var user: User

    init {
        this.queue = queue
        this.sharedPreferences = sharedPreferences
    }

    fun getUser(): User {
        return user
    }

    operator fun get(callBack: VolleyCallBack) {
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(URL, null,
            Response.Listener<JSONObject> { response: JSONObject ->
                val gson = Gson()
                user = gson.fromJson(
                    response.toString(),
                    User::class.java
                )
                callBack.onSuccess()
            },
            Response.ErrorListener { error: VolleyError? -> get(callBack) }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                val token: String? = sharedPreferences.getString("token", "")
                val auth = "Bearer $token"
                headers["Authorization"] = auth
                return headers
            }
        }
        queue.add(jsonObjectRequest)
    }

}