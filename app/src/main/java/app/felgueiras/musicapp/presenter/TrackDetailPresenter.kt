package app.felgueiras.musicapp.presenter

import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.model.CallAPIModel
import app.felgueiras.musicapp.view.DetailActivity

class TrackDetailPresenter(private val view: DetailActivity) {

    private val model = CallAPIModel


    fun getArtistDetail(mbid: String) {

        model.makeAPICall(this as Object, mbid, Constants.CALL_ARTIST)
    }

    fun displayArtistDetails(artist: Artist) {

        view.displayArtistInfo(artist)
    }

    interface TrackDetails {


        fun displayArtistInfo(artist: Artist)


    }
}
