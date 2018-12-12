package app.felgueiras.musicapp.presenter

import android.provider.SyncStateContract
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.model.CallAPIModel

class SongsListPresenter(private val view: SongsList) {

    private val model = CallAPIModel


    fun getSongsList(country: String) {

        model.makeAPICall(this as Object, country, Constants.CALL_SONGS)
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
