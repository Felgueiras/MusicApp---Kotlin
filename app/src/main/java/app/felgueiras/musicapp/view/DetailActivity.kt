package app.felgueiras.musicapp.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Artist
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.SongsDetailContract
import app.felgueiras.musicapp.presenter.SongDetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*
import android.os.Build
import android.view.View
import com.bumptech.glide.Glide


class DetailActivity : AppCompatActivity(), SongsDetailContract.View {

    lateinit var artist: Artist
    lateinit var track: Track
    private var presenter: SongDetailPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get the Intent that started this activity and extract the string
        track = intent.getSerializableExtra("TRACK") as Track
        title = track.name

        presenter = SongDetailPresenter(this)

        // TODO - loading indicator

        // call API
        presenter!!.getArtistDetail(track.artist.mbid)

        // animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            artistPhoto.setTransitionName("artist")
        }

        Glide.with(this).load(track.image[2].text).into(artistPhoto);
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate menu resource file.
        menuInflater.inflate(R.menu.share_menu, menu)

        // Return true to display menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.share -> {
                // TODO - set text to share
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, artist.toString() + track.toString())
                    type = "text/plain"
                }
                startActivity(sendIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun displayArtistInfo(artist: Artist) {

        this.artist = artist

        // TODO - pass all info to View
        Log.d(Constants.TAG, "" + artist.bio.content)
        artistName.text = artist.name
        song.text = track.name
        bio.text = artist.bio.summary
        genre.text = buildGenresText(artist.tags.tag)
        // similar artists as grid
        gridview.adapter = ImageAdapter(this, artist.similar.artist)
        // seconds to minutes
        if (track.duration != 0) {
            duration.text = String.format("(%02d:%02d)", track.duration / 60, track.duration % 60);
        } else {
            duration.visibility = View.GONE
        }
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

}
