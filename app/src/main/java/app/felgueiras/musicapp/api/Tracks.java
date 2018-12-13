package app.felgueiras.musicapp.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Track POJO.
 */
@Root(name = "tracks", strict = false)
public class Tracks implements Serializable {

    @Element(name = "track")
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
