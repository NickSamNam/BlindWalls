package com.nicknam.blindwalls

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

/**
 * Created by snick on 11-11-2017.
 */
class ImageSwipeAdapter(private val context: Context, private val layoutInflater: LayoutInflater, private val urls: List<String>) : PagerAdapter() {
    override fun isViewFromObject(view: View, o: Any): Boolean = view == o

    override fun getCount(): Int = urls.count()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = layoutInflater.inflate(R.layout.swipe_item_wall_image, container, false)
        val iv_wall = v.findViewById<ImageView>(R.id.swipe_item_wall_image_iv_wall)
        val iv_wall_blurred = v.findViewById<ImageView>(R.id.swipe_item_wall_image_iv_wall_blurred)

        Picasso.with(context).load(urls[position]).placeholder(R.drawable.generic_wall).fit().centerCrop().into(iv_wall)
        Picasso.with(context).load(urls[position]).placeholder(R.drawable.generic_wall).fit().transform(BlurTransformation(context)).into(iv_wall_blurred)

        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        container.removeView(o as View)
    }
}