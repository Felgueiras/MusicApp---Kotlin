package app.felgueiras.musicapp.api

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Track POJO.
 */
class Track : Serializable {

    lateinit var name: String

    @SerializedName("@attr")
    lateinit var rank: Rank

    lateinit var artist: Artist

    var duration: Int = 0

    var listeners: Int = 0

    @SerializedName("image")
    lateinit var images: List<ImageInfo>

    class Rank : Serializable {

        var rank: Int = 0
    }

}
