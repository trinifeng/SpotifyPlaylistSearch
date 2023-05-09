package com.example.spotifyplaylistsearch

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileFragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var usernameText: TextView
    lateinit var idText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        usernameText = view.findViewById(R.id.username_text)
        idText = view.findViewById(R.id.id_text)
        sharedPreferences = this.requireActivity().getSharedPreferences(getString(R.string.shared_pref_key),
            AppCompatActivity.MODE_PRIVATE
        )
        var name = sharedPreferences.getString("display_name", "Couldn't display name")
        var email = sharedPreferences.getString("email", "Couldn't display email")

        usernameText.setText("Name: " + name)
        idText.setText("ID: " + id)

        return view
    }
}