package app.felgueiras.musicapp.api

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Track POJO.
 */
class Track : Serializable {

    @SerializedName("name")
    lateinit var name: String

    lateinit var artist: Artist
    var duration: Int = 0

    var listener: Int = 0

    lateinit var image: List<ImageInfo>

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
