package app.felgueiras.musicapp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Track
import kotlinx.android.synthetic.main.activity_songs_list.*

class SongsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_list)

        val tracks = intent.getSerializableExtra(Constants.TRACKS) as ArrayList<Track>
        val country = intent.getSerializableExtra(Constants.COUNTRY) as String

        // set title
        title = "Top $country"

        // build songsList RecyclerView
        songsList.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(
            songsList.context,
            (songsList.layoutManager as LinearLayoutManager).getOrientation()
        )
        songsList.addItemDecoration(dividerItemDecoration)

        // sort tracks by name
        val sortedList = tracks.sortedWith(compareBy({ it.name }))

        val adapter = SongsListAdapter(sortedList, this)
        songsList.adapter = adapter
        adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE
    }



}
