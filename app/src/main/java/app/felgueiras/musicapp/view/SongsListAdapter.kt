package app.felgueiras.musicapp.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Track
import android.support.v4.app.ActivityOptionsCompat
import android.widget.ImageView
import com.bumptech.glide.Glide


/**
 * Adapter:
 */
internal class SongsListAdapter(private val tracks: List<Track>, private val context: Context) :
    RecyclerView.Adapter<SongsListAdapter.SongInListHolder>() {

    var songs: List<Track>? = tracks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongInListHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.track_in_list, parent, false)
        return SongInListHolder(inflatedView, context)
    }

    override fun onBindViewHolder(holder: SongInListHolder, position: Int) {
        // get current track
        val track = tracks[position]
        holder.bindTrack(track)

    }

    override fun getItemCount(): Int {
        return songs!!.size
    }

    class SongInListHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

        lateinit var track: Track
        var title: TextView = view.findViewById(R.id.trackName)
        var artist: TextView = view.findViewById(R.id.artistName)
        var artistPhoto: ImageView = view.findViewById(R.id.artistPhoto)

        init {

            view.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("TRACK", track)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context as Activity,
                    artistPhoto,
                    "artist"
                )
                context.startActivity(intent, options.toBundle())
            }
        }


        fun bindTrack(track: Track) {
            this.track = track
            title.text = track.name
            artist.text = track.artist.name
            // load image (Glide)
            Glide.with(context).load(track.image[2].text).into(artistPhoto);
        }
    }
}
