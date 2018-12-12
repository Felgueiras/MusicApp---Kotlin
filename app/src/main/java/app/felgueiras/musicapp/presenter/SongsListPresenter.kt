package app.felgueiras.musicapp.presenter

import android.util.Log
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.model.CallAPIModel

class SongsListPresenter(private val view: SongsList) {

    private val model = CallAPIModel()


    fun getSongsList() {

        model.getSongsList(this)
    }

    fun displaySongs(track: MutableList<Track>) {

        view.displaySongs(track)
    }

    interface SongsList {

//        fun updateUserInfoTextView(info: String)

        fun displaySongs(track: MutableList<Track>)

//        fun hideProgressBar()

    }
}
