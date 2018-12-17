package app.felgueiras.musicapp.api


import java.io.Serializable

/**
 * POJO for Last fm API call response
 */
class LastFMResponse : Serializable {

    /**
     * Tracks info.
     */
    lateinit var tracks: Tracks

    /**
     * Artist info.
     */
    lateinit var artist: Artist

}
