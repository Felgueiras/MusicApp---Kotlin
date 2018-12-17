package app.felgueiras.musicapp.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.SongDetailContract
import app.felgueiras.musicapp.presenter.SongDetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*
import android.os.Build
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.view.View
import app.felgueiras.musicapp.model.Model
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.artist_detail.*
import kotlinx.android.synthetic.main.view_loading.*


class DetailActivity : AppCompatActivity(), SongDetailContract.View {


    lateinit var artist: Artist
    lateinit var track: Track
    private var presenter = SongDetailPresenter(Model.getModel())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get the Intent that started this activity and extract the string
        track = intent.getSerializableExtra("TRACK") as Track
        title = track.name

        presenter.attachView(this)

        artistName.text = track.artist.name
        song.text = track.name
        // seconds to minutes
        if (track.duration != 0) {
            duration.text = String.format("(%02d:%02d)", track.duration / 60, track.duration % 60);
        } else {
            duration.visibility = View.GONE
        }
        // millions / thousands
        val listenersCount = track.listeners
        if (listenersCount / 1000000 > 1) {
            listeners.text = String.format("Listeners: %d M", track.listeners / 1000000);
        } else {
            listeners.text = String.format("Listeners: %d K", track.listeners / 1000);
        }

        // call API
        presenter!!.getArtistDetail(track.artist.mbid)

        // animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            artistPhoto.setTransitionName("artist")
        }

        share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, artist.toString() + track.toString())
                type = "url/plain"
            }
            startActivity(sendIntent)
        }

        Glide.with(this).load(track.images[Constants.IMAGE_QUALITY].url).into(artistPhoto);

        // hide fields
        bio.visibility = View.INVISIBLE
        genre.visibility = View.INVISIBLE
        similar.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }


    override fun displayArtistInfo(artist: Artist) {

        this.artist = artist

        // show fields
        bio.visibility = View.VISIBLE
        genre.visibility = View.VISIBLE
        similar.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        artist_detail.visibility = View.VISIBLE
        val bioSummary = artist.bio.summary.split("<a ")[0]
        bio.text = "Bio\n " + bioSummary + " ..."
        genre.text = buildGenresText(artist.tags.tag)
        // similar artists as grid
        gridview.adapter = ImageAdapter(this, artist.similar.artist)
    }

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
