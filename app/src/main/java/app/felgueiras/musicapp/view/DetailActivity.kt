package app.felgueiras.musicapp.view

import android.content.Intent
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

    lateinit var artist: Artist
    lateinit var track: Track

    override fun displayArtistInfo(artist: Artist) {

        this.artist = artist

        // TODO - pass all info to View
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
        track = intent.getSerializableExtra("TRACK") as Track
        Log.d(Constants.TAG, track.name)

        presenter = TrackDetailPresenter(this)

        // TODO - loading indicator

        // call API
        presenter!!.getArtistDetail(track.artist.mbid)

        sendData.setOnClickListener {
            // TODO - set text to send
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, artist.toString() + track.toString())
                type = "text/plain"
            }
            startActivity(sendIntent)
        }
    }


}
