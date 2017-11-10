package com.nicknam.blindwalls

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_details.*
import java.util.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val wall = intent.getSerializableExtra("wall") as Wall
        val l = if (resources.configuration.locale.language == Locale("nl").language) "nl" else "en"

        activity_details_tv_nr.text = wall.nrOnMap.toString()
        activity_details_tv_year.text = wall.year.toString()
        activity_details_tv_title.text = wall.title.getOrElse(resources.configuration.locale.language) { wall.title["en"] }?.trim()
        activity_details_tv_address.text = wall.address
        activity_details_rb_rating.rating = wall.rating ?: 0f
        activity_details_tv_material.text = getString(R.string.Material, wall.material.getOrElse(resources.configuration.locale.language) { wall.material["en"] }?.trim())
        activity_details_tv_categorie.text = getString(R.string.Category, wall.category.getOrElse(resources.configuration.locale.language) { wall.category["en"] }?.trim())
        activity_details_tv_description.text = wall.description.getOrElse(resources.configuration.locale.language) { wall.description["en"] }?.trim()
        Picasso.with(this).load(wall.images.firstOrNull()).placeholder(R.drawable.generic_wall).fit().centerCrop().into(activity_details_iv_wall)
        Picasso.with(this).load(wall.images.firstOrNull()).placeholder(R.drawable.generic_wall).fit().transform(BlurTransformation(this)).into(activity_details_iv_wall_blurred)
    }
}
