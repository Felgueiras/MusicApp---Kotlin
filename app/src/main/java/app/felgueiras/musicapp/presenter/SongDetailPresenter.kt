package app.felgueiras.musicapp.presenter

import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.contracts.SongsDetailContract
import app.felgueiras.musicapp.model.CallAPIModel
import app.felgueiras.musicapp.view.DetailActivity

class SongDetailPresenter(private val view: DetailActivity) : SongsDetailContract.Presenter {

    private val model = CallAPIModel


    override fun getArtistDetail(mbid: String) {

        model.makeAPICall(this as Object, mbid, Constants.CALL_ARTIST)
    }

    fun displayArtistDetails(artist: Artist) {

        view.displayArtistInfo(artist)
    }
}
