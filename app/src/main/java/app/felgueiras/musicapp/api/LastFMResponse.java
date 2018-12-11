package app.felgueiras.musicapp.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "tracks", strict = false)
public class LastFMResponse implements Serializable {

    @Element(name = "tracks", required = false)
    public Tracks tracks;

    @Element(name = "artist", required = false)
    public Artist artist;
//    @Element(name = "user", required = false)
//    private User user;
//    @Element(name = "search", required = false)
//    private Search search;
//    @Element(required = false)
//    private UserStatus user_status;
//    @ElementList(name = "reviews", required = false)
//    private List<Review> reviews;

    public LastFMResponse() {
    }

//    public Author getAuthor() {
//        return author;
//    }
//
//    public Search getSearch() {
//        return search;
//    }
//
//    public List<Review> getReviews() {
//        return reviews;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public UserStatus getUser_status() {
//        return user_status;
//    }
}
