package app.felgueiras.musicapp.contracts

import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.model.ModelCallback


/**
 * Methods to be implemented by the Model.
 */
interface ModelContract {

    fun getSongs(presenter: Any?, parameter: String, callback: ModelCallback<List<Track>>)

    fun getArtistDetail(presenter: Any?, parameter: String, callback: ModelCallback<Artist>)


}
