package app.felgueiras.musicapp.api


import java.io.Serializable


/**
 * Artist POJO.
 */
class Artist : Serializable {

    override fun toString(): String {
        return "Artist: ${name!!}\nBio: ${bio.summary}"
    }

    var name: String? = null

    lateinit var mbid: String

    lateinit var image: List<ImageInfo>

    lateinit var stats: Stats

    lateinit var similar: Similar

    lateinit var tags: Tags

    lateinit var bio: Bio

    inner class Similar : Serializable {

        lateinit var artist: List<Artist>
    }

    inner class Bio : Serializable {

        lateinit var summary: String
        lateinit var content: String
    }

    inner class Tags : Serializable {


        lateinit var tag: List<Tag>
    }

    inner class Tag : Serializable {

        lateinit var name: String
    }


    inner class Stats : Serializable {

        var listeners: Int = 0

        var playcount: Int = 0
    }

}
