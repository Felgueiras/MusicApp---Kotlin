package app.felgueiras.musicapp.contracts

import app.felgueiras.musicapp.api.Track

interface SongsListContract {

    /**
     * Operations offered from View to Presenter
     */
    interface View {
        /**
         * Display songs in UI as list.
         */
        fun displaySongs(track: MutableList<Track>)
    }

    /**
     * Operations offered from Presenter to View
     */
    interface Presenter {
        /**
         * Get list of songs to be displayed.
         */
        fun getSongsList(country: String)
    }
}
