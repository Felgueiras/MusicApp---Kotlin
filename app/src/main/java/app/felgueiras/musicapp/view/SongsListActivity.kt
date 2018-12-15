package app.felgueiras.musicapp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Track
import kotlinx.android.synthetic.main.activity_songs_list.*

class SongsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_list)

        scrollViewSongsList.smoothScrollTo(0,0)

        val tracks = intent.getSerializableExtra(Constants.TRACKS) as ArrayList<Track>
        val country = intent.getSerializableExtra(Constants.COUNTRY) as String

        // set title
        info.text = "Top Songs $country"

        // build songsList RecyclerView
        songsList.layoutManager = LinearLayoutManager(this)
        val adapter = SongsListAdapter(ArrayList(tracks), this)
        songsList.adapter = adapter
        adapter.notifyDataSetChanged()
    }


}
