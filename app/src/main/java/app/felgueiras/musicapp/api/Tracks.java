package app.felgueiras.musicapp.api;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Tracks POJO.
 */
public class Tracks implements Serializable {

    @SerializedName("track")
    public List<Track> tracks;

    public Tracks() {
    }

}
