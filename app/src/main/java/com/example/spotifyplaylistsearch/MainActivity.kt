package com.example.spotifyplaylistsearch

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.gson.Gson
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val URL = "https://api.spotify.com/v1/me"
    lateinit var requestQueue: RequestQueue
    lateinit var sharedPreferences: SharedPreferences
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var frameLayout: FrameLayout
    lateinit var myEditor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        frameLayout = findViewById(R.id.frame_layout)

        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)

        @Throws(AuthFailureError::class)
        fun getHeaders(): Map<String, String> {
            val headers: MutableMap<String, String> = HashMap()

            // get the auth token from shared preferences
            val token = sharedPreferences.getString("token", "")
            val auth = "Bearer $token"

            // add it in headers
            headers["Authorization"] = auth
            return headers
        }

        val accessTokenRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, URL, JSONObject(),
            Response.Listener<JSONObject?> {
                myEditor = sharedPreferences.edit()
                myEditor.apply {
                    myEditor.putString("display_name", it.getString("display_name"))
                    myEditor.putString("id", it.getString("id"))
                    commit()
                }
            }, Response.ErrorListener {
                Log.e("TAG", "RESPONSE IS $it")
                // in this case we are simply displaying a toast message.
                Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT)
                    .show()
            }) {
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

        val profileFragment = ProfileFragment()
        val topSongsFragment = TopSongsFragment()
        val searchFragment = SearchFragment()

        setCurrentFragment(profileFragment)

        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when(it.itemId){
                R.id.myProfileMenuItem -> setCurrentFragment(profileFragment)
                R.id.mySearchMenuItem -> setCurrentFragment(searchFragment)
                R.id.myTopSongsMenuItem -> setCurrentFragment(topSongsFragment)
            }
            true
        })
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}