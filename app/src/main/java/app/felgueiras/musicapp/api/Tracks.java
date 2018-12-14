package app.felgueiras.musicapp.api;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Track POJO.
 */
public class Tracks implements Serializable {

    @SerializedName("track")
    public List<Track> track;

//    public String getmChannel() {
//        return name;
//    }

    public Tracks() {
    }

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
