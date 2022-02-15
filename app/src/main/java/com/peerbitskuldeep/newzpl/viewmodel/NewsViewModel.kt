package com.peerbitskuldeep.newzpl.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peerbitskuldeep.newzpl.jsondata.Article
import com.peerbitskuldeep.newzpl.jsondata.NewsResponse
import com.peerbitskuldeep.newzpl.repository.NewsRepository
import com.peerbitskuldeep.newzpl.ui.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
): ViewModel() {

    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {

        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {

        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))

    }

    //decide success or error state
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>
    {
        if (response.isSuccessful)
        {
            response.body()?.let { resultResponse ->

                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>
    {
        if (response.isSuccessful)
        {
            response.body()?.let { resultResponse ->

                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.delete(article)
    }

}