package com.peerbitskuldeep.newzpl.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.peerbitskuldeep.newzpl.jsondata.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //if inserted data is already exist in database then it will be replaced
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>> //it won't be suspend function because its return live data object

    //Live data -> unable us to subscribe changes in live data and whenever its changes livedata will notify all of its observers

    @Delete
    suspend fun deleteArticle(article: Article)
}