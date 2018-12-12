package app.felgueiras.musicapp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.presenter.SongsListPresenter

class SongsListActivity : AppCompatActivity(), SongsListPresenter.SongsList {

    // views
    lateinit var songsList: RecyclerView
    lateinit var progress: ProgressBar

    private var presenter: SongsListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)

        // get country from bundle
        var country = intent.getSerializableExtra(Constants.COUNTRY) as String

        progress = findViewById(R.id.progressBar)
        progress.visibility = View.VISIBLE

        songsList = findViewById(R.id.songsList)
        songsList.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(
            songsList.getContext(),
            (songsList.layoutManager as LinearLayoutManager).getOrientation()
        )
        songsList.addItemDecoration(dividerItemDecoration)

        presenter = SongsListPresenter(this)

        // call API
        presenter!!.getSongsList(country)


    }

    override fun displaySongs(tracks: MutableList<Track>) {
        Log.d(Constants.TAG, "displaying songs: " + tracks[0].image[0].text)

        // sort tracks by name
        var sortedList = tracks.sortedWith(compareBy({ it.name }))

        val adapter = SongsListAdapter(sortedList, baseContext)
        songsList.adapter = adapter
        adapter.notifyDataSetChanged()
        progress.visibility = View.GONE
    }

}
