package app.felgueiras.musicapp.presenter

import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.contracts.SongsDetailContract
import app.felgueiras.musicapp.model.ModelCallback
import app.felgueiras.musicapp.model.Model
import app.felgueiras.musicapp.view.DetailActivity

class SongDetailPresenter(
    val view: DetailActivity,
    val model: Model
) : SongsDetailContract.Presenter {


    override fun getArtistDetail(mbid: String) {

        model.getArtistDetail(this as Object, mbid, Constants.CALL_ARTIST, object : ModelCallback<Artist> {
            override fun onSuccess(artist: Artist?) {

                view.displayArtistInfo(artist!!)
            }

            // 4
            override fun onError() {
                // TODO - show error
//                view?.showError()
            }
        })
    }


}
