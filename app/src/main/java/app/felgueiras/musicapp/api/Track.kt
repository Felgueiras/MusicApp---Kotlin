package app.felgueiras.musicapp.api

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Track POJO.
 */
class Track : Serializable {

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("@attr")
    lateinit var rank: Rank

    lateinit var artist: Artist

    var duration: Int = 0

    var listeners: Int = 0

    lateinit var image: List<ImageInfo>


    class Rank : Serializable {

        var rank: Int = 0
    }

}
