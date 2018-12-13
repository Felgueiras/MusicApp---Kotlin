package app.felgueiras.musicapp.presenter

import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.SongsListContract
import app.felgueiras.musicapp.model.CallAPIModel

class SongsListPresenter(private val view: SongsListContract.View) : SongsListContract.Presenter {

    private val model = CallAPIModel

    override fun getSongsList(country: String) {
        model.makeAPICall(this as Object, country, Constants.CALL_SONGS)
    }

    fun displaySongs(track: MutableList<Track>) {
        view.displaySongs(track)
    }
}
