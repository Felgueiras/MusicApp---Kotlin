package app.felgueiras.musicapp.presenter

import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.contracts.ModelContract
import app.felgueiras.musicapp.contracts.SongDetailContract
import app.felgueiras.musicapp.model.ModelCallback

class SongDetailPresenter(
    val model: ModelContract
) : BasePresenter<SongDetailContract.View>(), SongDetailContract.Presenter {


    override fun getArtistDetail(mbid: String) {

        model.getArtistDetail(this as Object, mbid, object : ModelCallback<Artist> {
            override fun onSuccess(artist: Artist?) {

                view!!.displayArtistInfo(artist!!)
            }

            // 4
            override fun onError() {
                view?.showNetworkError()
            }
        })
    }


}
