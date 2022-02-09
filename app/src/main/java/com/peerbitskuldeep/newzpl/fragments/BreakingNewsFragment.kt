package com.peerbitskuldeep.newzpl.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.peerbitskuldeep.newzpl.R
import com.peerbitskuldeep.newzpl.adapters.NewsAdapter
import com.peerbitskuldeep.newzpl.ui.NewsActivity
import com.peerbitskuldeep.newzpl.ui.Resource
import com.peerbitskuldeep.newzpl.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         viewModel = (activity as NewsActivity).vm

        setUpRecyclerView()

        newsAdapter.setOnItemClickLstnr {

            var bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )

        }


        //Get a LifecycleOwner that represents the Fragment's View lifecycle
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response ->

            when(response){

                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->

                        newsAdapter.differ.submitList(newsResponse.articles)

                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let{message ->

                        Log.e("TAG", "BreakingNewsFragment: $message", )

                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

            }

        })

    }

    private fun setUpRecyclerView(){

        newsAdapter = NewsAdapter()

        rvBreakingNews.apply {

            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }

    }

    private fun hideProgressBar()
    {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar()
    {
        paginationProgressBar.visibility = View.VISIBLE
    }

}