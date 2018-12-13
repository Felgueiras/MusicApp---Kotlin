package app.felgueiras.musicapp.api

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

import java.io.Serializable

/**
 * Track POJO.
 */
@Root(name = "track", strict = false)
class Track : Serializable {

    lateinit var name: String

    lateinit var artist: Artist

    var duration: Int = 0

    var listener: Int = 0

    lateinit var image: List<Artist.ImageInfo>

    //    public Tracks(String mChannel) {
    //        this.name = mChannel;
    //    }
    //
    //    public String getName() {
    //        return name;
    //    }
    //
    //    public void setName(String name) {
    //        this.name = name;
    //    }
}
