package com.peerbitskuldeep.newzpl.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.peerbitskuldeep.newzpl.R
import com.peerbitskuldeep.newzpl.adapters.NewsAdapter
import com.peerbitskuldeep.newzpl.constants.Constants.Companion.API_DELAY
import com.peerbitskuldeep.newzpl.ui.NewsActivity
import com.peerbitskuldeep.newzpl.ui.Resource
import com.peerbitskuldeep.newzpl.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.android.synthetic.main.fragment_search_news.paginationProgressBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment: Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).vm
        setUpRV()

        newsAdapter.setOnItemClickLstnr {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        var job: Job? = null //for delay
        etSearch.addTextChangedListener { editable ->

            job?.cancel()
            job = MainScope().launch {

                delay(API_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }

            }

        }


        viewModel.searchNews.observe(viewLifecycleOwner, Observer {response ->

            when(response){
                is Resource.Success -> {

                    hideProgressBar()
                    response.data?.let { newsResponse ->

                        newsAdapter.differ.submitList(newsResponse.articles)

                    }
                }

                is Resource.Error -> {

                    hideProgressBar()
                    response.data?.let {
                        Toast.makeText(activity,"Error! $it", Toast.LENGTH_SHORT).show()
                    }

                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })



    }

    private fun hideProgressBar()
    {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar()
    {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRV(){
        newsAdapter = NewsAdapter()

        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

}