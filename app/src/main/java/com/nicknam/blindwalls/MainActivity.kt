package com.nicknam.blindwalls

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvWalls = findViewById<ListView>(R.id.activity_main_lv_walls)
        val walls = Wall.extractFromJSONArray(DataLoader.retrieveWalls(this))
        walls.sortBy { it -> it.nrOnMap }
        val wallAdapter = WallAdapter(this, layoutInflater, walls)
        lvWalls.adapter = wallAdapter
        lvWalls.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            val intent = Intent(applicationContext, DetailsActivity::class.java)
            intent.putExtra("wall", wallAdapter.getItem(position))
            startActivity(intent)
        }
    }
}
