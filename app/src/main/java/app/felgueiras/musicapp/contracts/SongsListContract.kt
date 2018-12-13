package app.felgueiras.musicapp.contracts

import app.felgueiras.musicapp.api.Track

interface SongsListContract {

    /**
     * Operations offered from View to Presenter
     */
    interface View {
        fun displaySongs(track: MutableList<Track>)
    }

    /**
     * Operations offered from Presenter to View
     */
    interface Presenter {
        fun getSongsList(country: String)
    }
}
