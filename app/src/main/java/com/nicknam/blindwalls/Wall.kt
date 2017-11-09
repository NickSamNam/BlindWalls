package com.nicknam.blindwalls

import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by snick on 9-11-2017.
 */
data class Wall(val id: Int,
                val published: Int,
                val date: Date,
                val dateMod: Date,
                val authorID: Int,
                val latitude: Float,
                val longitude: Float,
                val address: String,
                val nrOnMap: Int,
                val videoURL: String,
                val year: Int,
                val photographer: String,
                val videoAuthor: String,
                val author: String,
                val rating: Float,
                val title: Map<String, String>,
                val url: Map<String, String>,
                val description: Map<String, String>,
                val material: Map<String, String>,
                val category: Map<String, String>,
                val images: List<String>
) {
    companion object {
        fun extractFromJSONArray(jsonArray: JSONArray): List<Wall> {
            val walls: MutableList<Wall> = ArrayList(jsonArray.length())
            for (i in 0..jsonArray.length()) {
                val o = jsonArray[i] as JSONObject

                val title: MutableMap<String, String> = HashMap(2)
                title.put("en", o.getJSONObject("title")["en"] as String)
                title.put("nl", o.getJSONObject("title")["nl"] as String)
                val url: MutableMap<String, String> = HashMap(2)
                url.put("en", o.getJSONObject("url")["en"] as String)
                url.put("nl", o.getJSONObject("url")["nl"] as String)
                val description: MutableMap<String, String> = HashMap(2)
                description.put("en", o.getJSONObject("description")["en"] as String)
                description.put("nl", o.getJSONObject("description")["nl"] as String)
                val material: MutableMap<String, String> = HashMap(2)
                material.put("en", o.getJSONObject("material")["en"] as String)
                material.put("nl", o.getJSONObject("material")["nl"] as String)
                val category: MutableMap<String, String> = HashMap(2)
                category.put("en", o.getJSONObject("category")["en"] as String)
                category.put("nl", o.getJSONObject("category")["nl"] as String)
                val images: MutableList<String> = ArrayList((o.getJSONArray("images")).length())

                walls.add(Wall(
                        o["id"] as Int,
                        o["published"] as Int,
                        Date(o["date"] as Long),
                        Date(o["dateModified"] as Long),
                        o["authorID"] as Int,
                        o["latitude"] as Float,
                        o["longitude"] as Float,
                        o["address"] as String,
                        o["numberOnMap"] as Int,
                        o["videoUrl"] as String,
                        o["year"] as Int,
                        o["photographer"] as String,
                        o["videoAuthor"] as String,
                        o["author"] as String,
                        o["rating"] as Float,
                        title,
                        url,
                        description,
                        material,
                        category,
                        images
                ))
            }
            return walls
        }
    }
}