package com.nicknam.blindwalls

import android.content.Context
import org.json.JSONArray
import java.io.InputStreamReader

/**
 * Created by snick on 9-11-2017.
 */
class DataLoader {
    companion object {
        const val BASE_API_URL = "https://api.blindwalls.gallery/apiv2/murals"
        const val BASE_IMAGE_URL = "https://api.blindwalls.gallery/"

        fun retrieveWalls(context: Context): JSONArray = JSONArray(InputStreamReader(context.resources.openRawResource(R.raw.walls)).buffered().use { it.readText() })
    }
}