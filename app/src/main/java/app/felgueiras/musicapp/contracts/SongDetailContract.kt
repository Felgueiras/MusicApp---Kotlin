package app.felgueiras.musicapp.contracts

import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.Track


/**
 * Methods to be implemented in Song Detail.
 */
interface SongDetailContract {

    /**
     * Operations offered from View to Presenter
     */
    interface View {

        fun displayArtistInfo(artist: Artist)

        fun showNetworkError()

        fun displayFullBio()

        fun shareData(string: String)
    }

    /**
     * Operations offered from Presenter to View
     */
    interface Presenter {

        fun getArtistDetail(track: Track)

        fun bioTextClicked()

        fun handleShare()
    }
}
