package com.nicknam.blindwalls

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.VolleyError
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity(), Response.Listener<JSONArray>, Response.ErrorListener {
    private lateinit var wallAdapter: WallAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wallAdapter = WallAdapter(this, layoutInflater, emptyList())

        val walls = savedInstanceState?.getParcelableArrayList<Wall>("walls")
        if (walls != null) refreshWallAdapter(walls) else {
            activity_main_swipeContainer.isRefreshing = true
            DataManager.retrieveWallsFromAPI(this, this, this)}

        activity_main_swipeContainer.setOnRefreshListener( {DataManager.retrieveWallsFromAPI(this, this, this)} )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("walls", ArrayList(wallAdapter.walls))
    }

    private fun refreshWallAdapter(walls: List<Wall>) {
        val lvWalls = findViewById<ListView>(R.id.activity_main_lv_walls)
        wallAdapter = WallAdapter(this, layoutInflater, walls)
        lvWalls.adapter = wallAdapter
        lvWalls.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            val intent = Intent(applicationContext, DetailsActivity::class.java)
            intent.putExtra("wall", wallAdapter.getItem(position))
            startActivity(intent)
        }
    }

    override fun onResponse(response: JSONArray) {
        refreshWallAdapter(Wall.extractFromJSONArray(response).sortedBy { it -> it.nrOnMap })
        activity_main_swipeContainer.isRefreshing = false
    }

    override fun onErrorResponse(error: VolleyError) {
        refreshWallAdapter(Wall.extractFromJSONArray(DataManager.retrieveWallsInternal(this)).sortedBy { it -> it.nrOnMap })
        activity_main_swipeContainer.isRefreshing = false
        Toast.makeText(this, R.string.api_error, Toast.LENGTH_LONG).show()
    }
}
