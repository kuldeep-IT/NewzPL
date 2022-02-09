package com.peerbitskuldeep.newzpl.jsondata

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)