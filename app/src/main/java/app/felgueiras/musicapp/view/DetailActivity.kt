package app.felgueiras.musicapp.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.SongDetailContract
import app.felgueiras.musicapp.model.Model
import app.felgueiras.musicapp.presenter.SongDetailPresenter
import app.felgueiras.musicapp.view.adapters.SimilarArtistsAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.artist_detail.*
import kotlinx.android.synthetic.main.view_loading.*

/**
 * Present song/artist info, share data.
 */
class DetailActivity : AppCompatActivity(), SongDetailContract.View {

    /**
     * Selected Artist.
     */
    lateinit var artist: Artist
    /**
     * Selected Track.
     */
    lateinit var track: Track
    /**
     * Reference to Presenter.
     */
    private var presenter = SongDetailPresenter(Model.getModel())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get the Intent that started this activity and extract the string
        track = intent.getSerializableExtra("TRACK") as Track
        // attach View to Presenter
        presenter.attachView(this)
        // fill TextViews
        artistName.text = track.artist.name
        song.text = track.name
        // convert seconds to minutes
        if (track.duration != 0) {
            duration.text = track.convertDuration()
        } else {
            duration.visibility = View.GONE
        }
        // millions / thousands
        val listenersCount = track.listeners
        listeners.text = "Listeners: " +track.convertListeners()

        // get Artist detail from Presenter
        presenter.getArtistDetail(track.artist.mbid)

        // trigger shared element transition (artist photo)
        artistPhoto.setTransitionName("artist")
        Glide.with(this).load(track.images[Constants.IMAGE_QUALITY].url).into(artistPhoto);

        // handle FAB click
        share.setOnClickListener {
            // start share intent
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, track.toString() + artist.toString())
                type = "text/plain"
            }
            startActivity(sendIntent)
        }
    }


    /**
     * Artist info loaded, display it.
     */
    override fun displayArtistInfo(artist: Artist) {
        // store reference to artist
        this.artist = artist

        // hide loading, show artist detail
        loading_view.visibility = View.GONE
        artist_detail.visibility = View.VISIBLE
        // remove <a> link to Last.fm from summary
        val bioSummary = artist.bio.summary.split("<a ")[0]
        bio.text = "Bio\n " + bioSummary + " ..."
        genre.text = buildGenresText(artist.tags.tag)
        // pass similar artists to Grid adapter
        gridview.adapter = SimilarArtistsAdapter(this, artist.similar.artist)
    }

    /**
     * Return artist genres as a string with delimiter.
     */
    private fun buildGenresText(tags: List<Artist.Tag>): String {

        var genres = ""
        tags.forEach {
            genres += it.name
            if (tags.indexOf(it) != tags.size - 1)
                genres += " · "
        }
        return genres

    }

    override fun showNetworkError() {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(getString(R.string.network_disabled))
        dialog.setPositiveButton(
            getString(R.string.open_network_settings)
        ) { _, _ ->
            val myIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(myIntent)
        }
        dialog.setNegativeButton(
            getString(R.string.close)
        ) { _, _ ->
        }
        dialog.show()
    }

    /**
     * Detach view from presenter.
     */
    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
