package com.example.spotifyplaylistsearch

class Playlist(val songs: Array<Song>,
               val playlistId: String,
               val name: String) {
    private val URL = "https://api.spotify.com/v1/me/playlists"

    init {

    }
}