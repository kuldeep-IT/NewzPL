package com.peerbitskuldeep.newzpl.db

import android.content.Context
import androidx.room.*
import com.peerbitskuldeep.newzpl.jsondata.Article


@Database(
    entities = [Article::class],
    version = 1 //if we make changes in some points then update the version
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase(){

    abstract fun getAllArticleDao(): ArticleDao

    companion object{

        @Volatile //others thread immediately see if thread changes this instance
        private var instance: ArticleDatabase? = null

        private val LOCK = Any()

        //invoke fun -> called whenever we create an instance of database
        //can't be accessed by other thread at same time
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK)
        {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }

}