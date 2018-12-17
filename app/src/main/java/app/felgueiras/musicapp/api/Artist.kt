package app.felgueiras.musicapp.api


import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Artist POJO.
 */
class Artist : Serializable {

    override fun toString(): String {
        return "Artist: ${name!!}\nBio: ${bio.summary}"
    }

    /**
     * Name.
     */
    var name: String? = null

    /**
     * Unique ID
     */
    lateinit var mbid: String

    /**
     * Images (different sizes)
     */
    @SerializedName("image")
    lateinit var images: List<ImageInfo>


    /**
     * Similar artists.
     */
    lateinit var similar: Similar

    /**
     * Tags (genres)
     */
    lateinit var tags: Tags

    /**
     * Biography.
     */
    lateinit var bio: Bio

    inner class Similar : Serializable {

        lateinit var artist: List<Artist>
    }

    inner class Bio : Serializable {

        /**
         * Resumed bio.
         */
        lateinit var summary: String

        /**
         * Full bio.
         */
        lateinit var content: String
    }

    inner class Tags : Serializable {

        lateinit var tag: List<Tag>
    }

    inner class Tag : Serializable {

        /**
         * Genre.
         */
        lateinit var name: String
    }

}
