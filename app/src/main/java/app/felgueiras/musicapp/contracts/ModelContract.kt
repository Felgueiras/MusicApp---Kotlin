package app.felgueiras.musicapp.contracts

import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.model.ModelCallback

interface ModelContract {

    /**
     * Operations offered from Model to Presenter
     */
    interface Model {
        fun getSongs(presenter: Any?, parameter: String, callType: String, callback: ModelCallback<List<Track>>)
        fun getArtistDetail(presenter: Any?, parameter: String, callType: String, callback: ModelCallback<Artist>)
    }




}
