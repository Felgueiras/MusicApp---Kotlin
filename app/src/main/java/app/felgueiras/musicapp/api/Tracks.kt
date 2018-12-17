package app.felgueiras.musicapp.api


import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Tracks POJO.
 */
class Tracks : Serializable {

    @SerializedName("track")
    var tracks: List<Track>? = null

}
