package app.felgueiras.musicapp.contracts

import app.felgueiras.musicapp.api.Artist


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
    }

    /**
     * Operations offered from Presenter to View
     */
    interface Presenter {
        fun getArtistDetail(mbid: String)
    }
}
