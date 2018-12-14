package app.felgueiras.musicapp.api;


import java.io.Serializable;

/**
 * POJO for Last fm API call response
 */
public class LastFMResponse implements Serializable {

    public Tracks tracks;

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
