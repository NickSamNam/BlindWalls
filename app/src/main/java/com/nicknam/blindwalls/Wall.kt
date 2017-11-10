package com.nicknam.blindwalls

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
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
                val date: Date?,
                val dateMod: Date?,
                val authorID: Int,
                val latitude: Float,
                val longitude: Float,
                val address: String?,
                val nrOnMap: Int,
                val videoURL: String?,
                val year: Int,
                val photographer: String?,
                val videoAuthor: String?,
                val author: String?,
                val rating: Float,
                val title: Map<String, String> = HashMap(),
                val url: Map<String, String> = HashMap(),
                val description: Map<String, String> = HashMap(),
                val material: Map<String, String> = HashMap(),
                val category: Map<String, String> = HashMap(),
                val images: List<String> = ArrayList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
            id = parcel.readInt(),
            published = parcel.readInt(),
            date = parcel.readSerializable() as Date,
            dateMod = parcel.readSerializable() as Date,
            authorID = parcel.readInt(),
            latitude = parcel.readFloat(),
            longitude = parcel.readFloat(),
            address = parcel.readString(),
            nrOnMap = parcel.readInt(),
            videoURL = parcel.readString(),
            year = parcel.readInt(),
            photographer = parcel.readString(),
            videoAuthor = parcel.readString(),
            author = parcel.readString(),
            rating = parcel.readFloat(),
            images = parcel.createStringArrayList(),
            title = parcel.readBundle().toMap(),
            url = parcel.readBundle().toMap(),
            description = parcel.readBundle().toMap(),
            material = parcel.readBundle().toMap(),
            category = parcel.readBundle().toMap())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeInt(published)
        dest.writeSerializable(date)
        dest.writeSerializable(dateMod)
        dest.writeInt(authorID)
        dest.writeFloat(latitude)
        dest.writeFloat(longitude)
        dest.writeString(address)
        dest.writeInt(nrOnMap)
        dest.writeString(videoURL)
        dest.writeInt(year)
        dest.writeString(photographer)
        dest.writeString(videoAuthor)
        dest.writeString(author)
        dest.writeFloat(rating)
        dest.writeStringList(images)
        dest.writeBundle(title.toBundle())
        dest.writeBundle(url.toBundle())
        dest.writeBundle(description.toBundle())
        dest.writeBundle(material.toBundle())
        dest.writeBundle(category.toBundle())
    }

    override fun describeContents(): Int = 0

    companion object {
        fun extractFromJSONArray(jsonArray: JSONArray): MutableList<Wall> {
            val walls: MutableList<Wall> = ArrayList(jsonArray.length())
            for (i in 0 until jsonArray.length()) {
                val jO = jsonArray[i] as? JSONObject ?: break
                val jTitle = jO["title"] as? JSONObject
                val jURL = jO["url"] as? JSONObject
                val jDescription = jO["description"] as? JSONObject
                val jMaterial = jO["material"] as? JSONObject
                val jCategory = jO["category"] as? JSONObject
                val jImages = jO["images"] as? JSONArray

                val title: MutableMap<String, String> = HashMap(2)
                val url: MutableMap<String, String> = HashMap(2)
                val description: MutableMap<String, String> = HashMap(2)
                val material: MutableMap<String, String> = HashMap(2)
                val category: MutableMap<String, String> = HashMap(2)
                val images: MutableList<String> = ArrayList((jO.getJSONArray("images")).length())

                jTitle?.keys()?.forEach { k ->
                    val v = jTitle[k] as? String
                    v?.let { title.put(k, v) }
                }
                jURL?.keys()?.forEach { k ->
                    val v = jURL[k] as? String
                    v?.let { url.put(k, v) }
                }
                jDescription?.keys()?.forEach { k ->
                    val v = jDescription[k] as? String
                    v?.let { description.put(k, v) }
                }
                jMaterial?.keys()?.forEach { k ->
                    val v = jMaterial[k] as? String
                    v?.let { material.put(k, v) }
                }
                jCategory?.keys()?.forEach { k ->
                    val v = jCategory[k] as? String
                    v?.let { category.put(k, v) }
                }
                if (jImages != null) (0 until jImages.length()).mapNotNullTo(images) { jImages.getJSONObject(it)["url"] as? String }

                walls.add(Wall(
                        id = jO["id"] as? Int ?: -1,
                        published = jO["published"] as? Int ?: -1,
                        date = if (jO["date"] as? Int != null) Date((jO["date"] as Int).toLong()) else null,
                        dateMod = if (jO["dateModified"] as? Int != null) Date((jO["dateModified"] as Int).toLong()) else null,
                        authorID = jO["authorID"] as? Int ?: -1,
                        latitude = (jO["latitude"] as? Double ?: Double.NaN).toFloat(),
                        longitude = (jO["longitude"] as? Double ?: Double.NaN).toFloat(),
                        address = jO["address"] as String?,
                        nrOnMap = jO["numberOnMap"] as Int? ?: -1,
                        videoURL = jO["videoUrl"] as? String,
                        year = (jO["year"] as String? ?: "-1").toInt(),
                        photographer = jO["photographer"] as String?,
                        videoAuthor = jO["videoAuthor"] as String?,
                        author = jO["author"] as String?,
                        rating = if (jO["rating"] is Double) (jO["rating"] as Double).toFloat() else (jO["rating"] as? Int ?: -1).toFloat(),
                        title = title,
                        url = url,
                        description = description,
                        material = material,
                        category = category,
                        images = images
                ))
            }
            return walls
        }

        @JvmField
        val CREATOR = object : Parcelable.Creator<Wall> {
            override fun createFromParcel(parcel: Parcel): Wall = Wall(parcel)

            override fun newArray(size: Int): Array<Wall?> = arrayOfNulls(size)
        }

        fun Map<String, String>.toBundle(): Bundle {
            val bundle = Bundle()
            for (entry in this.entries)
                bundle.putString(entry.key, entry.value)
            return bundle
        }

        fun Bundle.toMap(): Map<String, String> {
            val map: MutableMap<String, String> = HashMap()
            this.keySet().forEach { k -> map.put(k, this.getString(k)) }
            return map
        }
    }
}