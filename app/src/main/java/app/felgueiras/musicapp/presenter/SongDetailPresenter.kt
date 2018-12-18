package app.felgueiras.musicapp.presenter

import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.ModelContract
import app.felgueiras.musicapp.contracts.SongDetailContract
import app.felgueiras.musicapp.model.ModelCallback

/**
 * Handle displaying of Artist detail.
 */
class SongDetailPresenter(
    val model: ModelContract
) : BasePresenter<SongDetailContract.View>(), SongDetailContract.Presenter {

    lateinit var artist: Artist
    lateinit var track: Track

    override fun handleShare() {
        view!!.shareData(track.toString() + artist.toString())
    }


    /**
     * Get further info about an artist.
     */
    override fun getArtistDetail(track: Track) {
        this.track = track

        // call model
        model.getArtistDetail(this as Any, track.artist.mbid, object : ModelCallback<Artist> {
            override fun onSuccess(artist: Artist?) {
                this@SongDetailPresenter.artist = artist!!
                view!!.displayArtistInfo(artist!!)
            }

            override fun onError() {
                view?.showNetworkError()
            }
        })
    }

    /**
     * User wants to know more info about artist
     */
    override fun bioTextClicked() {
        view?.displayFullBio();
    }


}
