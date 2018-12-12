package app.felgueiras.musicapp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.presenter.TrackDetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity(), TrackDetailPresenter.TrackDetails {




    override fun displayArtistInfo(artist: Artist) {
        // TODO - pass info to View
        Log.d(Constants.TAG, "" + artist.bio.content)
        artistNameDetail.text = artist.name
        bio.text = artist.bio.summary
        genre.text = artist.tags.tag[0].name
    }

    private var presenter: TrackDetailPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        // Get the Intent that started this activity and extract the string
        val intent = intent
        val track: Track = intent.getSerializableExtra("TRACK") as Track
        Log.d(Constants.TAG, track.name)

        // TODO - make another call
        // TODO - artist info

        presenter = TrackDetailPresenter(this)

        // TODO - loading indicator

        // call API
        presenter!!.getArtistDetail(track.artist.mbid)
    }


}
