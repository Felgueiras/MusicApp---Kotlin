package app.felgueiras.musicapp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Track
import app.felgueiras.musicapp.contracts.SongsListContract
import app.felgueiras.musicapp.presenter.SongsListPresenter
import kotlinx.android.synthetic.main.activity_songs_list.*

class SongsListActivity : AppCompatActivity(), SongsListContract.View {

    private lateinit var presenter: SongsListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_list)

        // get country from bundle
        val country = intent.getSerializableExtra(Constants.COUNTRY) as String

        // set title
        title = "Top $country"

        // hide progress bar
        progressBar.visibility = View.VISIBLE

        // build songsList RecyclerView
        songsList.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(
            songsList.context,
            (songsList.layoutManager as LinearLayoutManager).getOrientation()
        )
        songsList.addItemDecoration(dividerItemDecoration)

        // initialize presenter and fetch data
        presenter = SongsListPresenter(this)
        presenter.getSongsList(country)

    }

    override fun displaySongs(tracks: MutableList<Track>) {

        // sort tracks by name
        val sortedList = tracks.sortedWith(compareBy({ it.name }))

        val adapter = SongsListAdapter(sortedList, baseContext)
        songsList.adapter = adapter
        adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }

}
