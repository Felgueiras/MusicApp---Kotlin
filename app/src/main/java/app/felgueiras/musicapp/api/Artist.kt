package app.felgueiras.musicapp.api

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

import java.io.Serializable


/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "artist", strict = false)
class Artist : Serializable {

    override fun toString(): String {
        return "Artist: ${name!!}\nBio: ${bio.summary}"
    }

    var name: String? = null

    lateinit var mbid: String

    @Element(name = "image")
    var image: List<ImageInfo>? = null

    lateinit var stats: Stats

    lateinit var similar: Similar

    lateinit var tags: Tags

    lateinit var bio: Bio

    @Root(name = "image", strict = false)
    inner class ImageInfo : Serializable {

        // TODO - access this field
        @Element(name = "#text")
        var text: String? = null

        @Element(name = "size")
        var size: String? = null
    }

    @Root(name = "similar", strict = false)
    inner class Similar : Serializable {

        @Element(name = "artist")
        lateinit var artist: List<Artist>
    }

    @Root(name = "bio", strict = false)
    inner class Bio : Serializable {

        lateinit var summary: String
        lateinit var content: String
    }

    @Root(name = "tags", strict = false)
    inner class Tags : Serializable {

        @Element(name = "tag")
        lateinit var tag: List<Tag>
    }

    @Root(name = "tag", strict = false)
    inner class Tag : Serializable {

        @Element(name = "name")
        lateinit var name: String
    }


    @Root(name = "stats", strict = false)
    inner class Stats : Serializable {

        @Element(name = "listeners")
        var listeners: Int = 0

        @Element(name = "playcount")
        var playcount: Int = 0
    }

}
