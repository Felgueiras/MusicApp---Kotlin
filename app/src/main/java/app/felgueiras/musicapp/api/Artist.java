package app.felgueiras.musicapp.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "artist", strict = false)
public class Artist implements Serializable {

    @Element(name = "name")
    public String name;

    @Element(name = "mbid")
    public String mbid;

    @Element(name = "image")
    public List<ImageInfo> image;

//    public String getmChannel() {
//        return name;
//    }

    public Artist() {
    }

    @Root(name = "image", strict = false)
    public class ImageInfo implements Serializable{

        // TODO - access this field
        @Element(name = "#text")
        public String text;

        @Element(name = "size")
        public String size;

        public ImageInfo() {
        }
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
