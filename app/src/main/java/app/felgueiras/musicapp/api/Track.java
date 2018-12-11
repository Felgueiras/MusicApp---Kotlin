package app.felgueiras.musicapp.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "track", strict = false)
public class Track implements Serializable {

    @Element(name = "name")
    public String name;

    @Element(name = "artist")
    public Artist artist;

//    public String getmChannel() {
//        return name;
//    }

    public Track() {
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
