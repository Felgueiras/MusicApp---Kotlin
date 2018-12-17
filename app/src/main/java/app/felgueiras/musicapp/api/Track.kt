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

    override fun toString(): String {
        return "Song: ${name}\nListeners: ${convertListeners()}\n"
    }

    fun convertListeners(): String {
        if (listeners / 1000000 > 1) {
            return String.format("%d M", listeners / 1000000);
        } else {
            return String.format("%d K", listeners / 1000);
        }
    }

    fun convertDuration(): String {
        return String.format("(%02d:%02d)", duration / 60, duration % 60);
    }

}
