package com.peerbitskuldeep.newzpl.repository

import com.peerbitskuldeep.newzpl.api.RetrofitInstance
import com.peerbitskuldeep.newzpl.db.ArticleDatabase
import com.peerbitskuldeep.newzpl.jsondata.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    //make network call request
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)


    suspend fun upsert(article: Article) = db.getAllArticleDao().upsert(article)

    fun getSavedNews() = db.getAllArticleDao().getAllArticles()

    suspend fun delete(article: Article) = db.getAllArticleDao().deleteArticle(article)

}