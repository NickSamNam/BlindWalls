package com.nicknam.blindwalls

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            supportActionBar?.hide()
        }
        else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        val wall = intent.getParcelableExtra<Wall>("wall")

        activity_details_tv_nr.text = wall.nrOnMap.toString()
        activity_details_tv_year.text = wall.year.toString()
        activity_details_tv_title.text = wall.title.getOrElse(resources.configuration.locale.language) { wall.title["en"] }?.trim()
        activity_details_tv_address.text = wall.address
        activity_details_rb_rating.rating = wall.rating
        activity_details_tv_material.text = getString(R.string.Material, wall.material.getOrElse(resources.configuration.locale.language) { wall.material["en"] }?.trim())
        activity_details_tv_categorie.text = getString(R.string.Category, wall.category.getOrElse(resources.configuration.locale.language) { wall.category["en"] }?.trim())
        activity_details_tv_description.text = wall.description.getOrElse(resources.configuration.locale.language) { wall.description["en"] }?.trim()
        activity_details_vp_images.adapter = ImageSwipeAdapter(this, layoutInflater, wall.images)
    }
}
