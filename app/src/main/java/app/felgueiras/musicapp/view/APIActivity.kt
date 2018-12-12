package app.felgueiras.musicapp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.presenter.SongsListPresenter
import butterknife.BindView

class APIActivity : AppCompatActivity(), SongsListPresenter.SongsList {

    // views
//    @BindView(R.id.songsList)
    lateinit var songsList: RecyclerView

    private var presenter: SongsListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)

        songsList = findViewById(R.id.songsList)
        songsList.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(songsList.getContext(),
            (songsList.layoutManager as LinearLayoutManager).getOrientation())
        songsList.addItemDecoration(dividerItemDecoration)

        presenter = SongsListPresenter(this)

        // call API
        presenter!!.getSongsList()


    }

    override fun displaySongs(tracks: MutableList<Track>) {
        Log.d(Constants.TAG, "displaying songs: " + tracks.size)

        val adapter = SongsListAdapter(tracks, baseContext)
        songsList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}
