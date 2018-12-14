package app.felgueiras.musicapp.view

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


class ImageAdapter(
    private val mContext: Context,
    private val similar: List<Artist>
) : BaseAdapter() {

    override fun getCount(): Int = similar.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0L

    // create a new ImageView for each item referenced by the Adapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView

        var holder = RecordHolder()


        if (convertView == null) {
            val inflater = (mContext as Activity).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_item, parent, false);

            holder.txtTitle = row.findViewById(R.id.artist_name);
            holder.imageItem = row.findViewById(R.id.artist_image);
            row.setTag(holder);
        } else {
            holder = row!!.getTag() as RecordHolder
        }

        val artist = similar[position]
        holder.txtTitle.text = artist.name
        Glide.with(mContext).load(artist.image[Constants.IMAGE_QUALITY].url).into(holder.imageItem);

        return row!!
    }

    internal class RecordHolder {
        lateinit var txtTitle: TextView
        lateinit var imageItem: ImageView

    }

}
