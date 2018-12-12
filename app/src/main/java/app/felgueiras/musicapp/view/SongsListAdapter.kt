package app.felgueiras.musicapp.view

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Track


/**
 * Created by felguiras on 15/09/2017.
 */
internal class SongsListAdapter(private val tracks: MutableList<Track>, private val context: Context) :
    RecyclerView.Adapter<SongsListAdapter.BookInListHolder>() {

    var songs: MutableList<Track>? = tracks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookInListHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.track_in_list, parent, false)
        return BookInListHolder(inflatedView, context)
    }

    override fun onBindViewHolder(holder: BookInListHolder, position: Int) {
        // get current track
        val track = tracks[position]
        holder.bindTrack(track)
    }

    override fun getItemCount(): Int {
        return songs!!.size
    }

    class BookInListHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

        lateinit var track: Track
        var title: TextView
        var artist: TextView

        init {

            title = view.findViewById(R.id.trackName)
            artist = view.findViewById(R.id.artistName)

            view.setOnClickListener {
                // TODO - navigate to other activity
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("TRACK", track)
                context.startActivity(intent)
//                Log.d(Constants.TAG, "clicked")
//                val f = (view.context as DrawerActivity).fragmentManager.findFragmentById(R.id.current_fragment)
//                val newFragment:Fragment = BookDetailView.Companion.newInstance(1, track)
//                (view.context as DrawerActivity).fragmentManager.beginTransaction()
////                        .hide(f)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .add(R.id.current_fragment,newFragment )
//                        .addToBackStack(null)
//                        .commit()
            }
        }


        fun bindTrack(track: Track) {
            this.track = track
            title.text = track.name
            artist.text = track.artist.name
            // load image (Glide)
//            Glide.with(context).load(this.track.image_url).into(bookCover);
        }
    }
}
