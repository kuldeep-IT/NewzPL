package com.peerbitskuldeep.newzpl.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.peerbitskuldeep.newzpl.R
import com.peerbitskuldeep.newzpl.adapters.NewsAdapter
import com.peerbitskuldeep.newzpl.databinding.FragmentBreakingNewsBinding
import com.peerbitskuldeep.newzpl.jsondata.Article
import com.peerbitskuldeep.newzpl.ui.NewsActivity
import com.peerbitskuldeep.newzpl.ui.Resource
import com.peerbitskuldeep.newzpl.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment: Fragment() {

    lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentBreakingNewsBinding
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBreakingNewsBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = (activity as NewsActivity).vm
        }

        setUpRecyclerView()

        newsAdapter.onClickListener = object : NewsAdapter.OnClickListener {
            override fun onClick(article: Article) {
                var bundle = Bundle().apply {
                    putSerializable("article",article)
                }
                Toast.makeText(requireContext(), article.description, Toast.LENGTH_SHORT).show()
                findNavController().navigate(
                    R.id.action_breakingNewsFragment_to_articleFragment,
                    bundle
                )
            }
        }

    /*    newsAdapter.onItemClickLstnr =  {
            var bundle = Bundle().apply {
                putSerializable("article",it)
            }
            Toast.makeText(requireContext(), it.description, Toast.LENGTH_SHORT).show()
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }*/

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

        return binding.root
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        viewModel = (activity as NewsActivity).vm
//        setUpRecyclerView()
//
//
//        newsAdapter.setOnItemClickLstnr {
//
//            var bundle = Bundle().apply {
//                putSerializable("article",it)
//            }
//            findNavController().navigate(
//                R.id.action_breakingNewsFragment_to_articleFragment,
//                bundle
//            )
//
//        }
//
//
//        //Get a LifecycleOwner that represents the Fragment's View lifecycle
//        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response ->
//
//            when(response){
//
//                is Resource.Success -> {
//                    hideProgressBar()
//                    response.data?.let { newsResponse ->
//
//                        newsAdapter.differ.submitList(newsResponse.articles)
//
//                    }
//                }
//
//                is Resource.Error -> {
//                    hideProgressBar()
//                    response.message?.let{message ->
//
//                        Log.e("TAG", "BreakingNewsFragment: $message", )
//
//                    }
//                }
//
//                is Resource.Loading -> {
//                    showProgressBar()
//                }
//
//            }
//
//        })
//
//    }

    private fun setUpRecyclerView(){

        newsAdapter = NewsAdapter()

        binding.rvBreakingNews.apply {

            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }

    }

    private fun hideProgressBar()
    {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar()
    {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

}