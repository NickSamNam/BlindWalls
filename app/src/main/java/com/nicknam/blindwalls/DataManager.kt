package com.nicknam.blindwalls

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.io.InputStreamReader

/**
 * Created by snick on 9-11-2017.
 */
class DataManager {
    companion object {
        const val BASE_API_URL = "https://api.blindwalls.gallery/apiv2/"
        const val BASE_IMAGE_URL = "https://api.blindwalls.gallery/"
        const val MURALS_API_SUFFIX = "murals"
        private val requestQueueMap: MutableMap<Context, RequestQueue> = HashMap()

        fun retrieveWallsInternal(context: Context): JSONArray = JSONArray(InputStreamReader(context.resources.openRawResource(R.raw.walls)).buffered().use { it.readText() })

        fun retreiveWallsFromAPI (context: Context, responseListener: Response.Listener<JSONArray>, errorListener: Response.ErrorListener) {
            val request = JsonArrayRequest(Request.Method.GET, BASE_API_URL + MURALS_API_SUFFIX, null, responseListener, errorListener)
            getRequestQueue(context).add(request)
        }

        private fun getRequestQueue(context: Context) : RequestQueue {
            if (requestQueueMap[context] == null)
                requestQueueMap.put(context, Volley.newRequestQueue(context))
            return requestQueueMap[context]!!
        }
    }
}