package app.felgueiras.musicapp.view.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import app.felgueiras.musicapp.Constants
import app.felgueiras.musicapp.R
import app.felgueiras.musicapp.api.Artist
import com.bumptech.glide.Glide

/**
 * Present similar Artists info.
 */
class SimilarArtistsAdapter(
    private val mContext: Context,
    private val similar: List<Artist>
) : BaseAdapter() {

    override fun getCount(): Int = similar.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0L

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var row = convertView

        var holder = ArtistHolder()
        if (convertView == null) {
            val inflater = (mContext as Activity).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_item, parent, false);

            holder.txtTitle = row.findViewById(R.id.artist_name);
            holder.imageItem = row.findViewById(R.id.artist_image);
            row.tag = holder;
        } else {
            holder = row!!.getTag() as ArtistHolder
        }

        val artist = similar[position]
        holder.txtTitle.text = artist.name
        Glide.with(mContext).load(artist.images[Constants.IMAGE_QUALITY].url).into(holder.imageItem);

        return row!!
    }

    internal class ArtistHolder {
        lateinit var txtTitle: TextView
        lateinit var imageItem: ImageView
    }

}
