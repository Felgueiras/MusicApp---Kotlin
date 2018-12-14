package app.felgueiras.musicapp.api

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * ImageInfo POJO.
 */
class ImageInfo : Serializable {

    @SerializedName("#text")
    var url: String? = null

    var size: String? = null
}