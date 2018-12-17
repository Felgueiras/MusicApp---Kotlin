package app.felgueiras.musicapp.api

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * ImageInfo POJO.
 */
class ImageInfo : Serializable {

    /**
     * Image url.
     */
    @SerializedName("#text")
    var url: String? = null

    /**
     * Size (small, medium, large, extralarge)
     */
    var size: String? = null
}