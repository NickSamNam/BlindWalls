package com.nicknam.blindwalls

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Created by snick on 9-11-2017.
 */
class WallAdapter(private val context: Context, private val layoutInflater: LayoutInflater, private val walls: MutableList<Wall>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val cv: View

        if (convertView == null) {
            cv = layoutInflater.inflate(R.layout.item_wall, parent, false)
            vh = ViewHolder()
            vh.title = cv.findViewById(R.id.item_wall_tv_title)
            vh.address = cv.findViewById(R.id.item_wall_tv_address)
            vh.rating = cv.findViewById(R.id.item_wall_rb_rating)
            vh.picture = cv.findViewById(R.id.item_wall_iv_picture)
            cv.tag = vh
        } else {
            cv = convertView
            vh = convertView.tag as ViewHolder
        }
        val w = walls[position]
        vh.title.text = w.title.getOrElse(context.resources.configuration.locale.language) { w.title["en"] }?.trim()
        vh.address.text = w.address
        vh.rating.rating = w.rating ?: 0f
        Picasso.with(context).load(w.images.firstOrNull()).placeholder(R.drawable.generic_wall).fit().centerCrop().into(vh.picture)

        return cv
    }

    override fun getItem(position: Int): Wall = walls[position]

    override fun getItemId(position: Int): Long = getItem(position).hashCode().toLong()

    override fun getCount(): Int = walls.count()

    private class ViewHolder {
        lateinit var title: TextView
        lateinit var address: TextView
        lateinit var rating: RatingBar
        lateinit var picture: ImageView
    }
}