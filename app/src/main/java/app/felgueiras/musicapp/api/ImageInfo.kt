package app.felgueiras.musicapp.api

import com.google.gson.annotations.SerializedName

import java.io.Serializable


class ImageInfo : Serializable {

    @SerializedName("#text")
    var text: String? = null

    var size: String? = null
}