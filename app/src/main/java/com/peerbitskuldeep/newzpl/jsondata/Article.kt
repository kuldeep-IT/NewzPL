package com.peerbitskuldeep.newzpl.jsondata

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "articles")
data class Article(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source, //use TypeConverter because Datatype is Source not a primitive
    val title: String,
    val url: String,
    val urlToImage: String
) : Serializable