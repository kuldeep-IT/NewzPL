package com.peerbitskuldeep.newzpl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.peerbitskuldeep.newzpl.R
import com.peerbitskuldeep.newzpl.adapters.NewsAdapter
import com.peerbitskuldeep.newzpl.databinding.FragmentSavedNewsBinding
import com.peerbitskuldeep.newzpl.jsondata.Article
import com.peerbitskuldeep.newzpl.ui.NewsActivity
import com.peerbitskuldeep.newzpl.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment: Fragment() {

    lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentSavedNewsBinding
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedNewsBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = (activity as NewsActivity).vm
        }


//        viewModel = (activity as NewsActivity).vm
        setUpRV()

        newsAdapter.onClickListener = object : NewsAdapter.OnClickListener{
            override fun onClick(article: Article) {
                val bundle = Bundle().apply {
                    putSerializable("article", article)
                }
                findNavController().navigate(
                    R.id.action_savedNewsFragment_to_articleFragment,
                    bundle
                )
            }

        }
      /*  newsAdapter.onItemClickLstnr = {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }*/

//        newsAdapter.setOnItemClickLstnr {
//            val bundle = Bundle().apply {
//                putSerializable("article", it)
//            }
//            findNavController().navigate(
//                R.id.action_savedNewsFragment_to_articleFragment,
//                bundle
//            )
//        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)

                Snackbar.make(requireView(), "News Deleted!", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {

            newsAdapter.differ.submitList(it)

        })

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewModel = (activity as NewsActivity).vm
//        setUpRV()
//
//        newsAdapter.setOnItemClickLstnr {
//            val bundle = Bundle().apply {
//                putSerializable("article", it)
//            }
//            findNavController().navigate(
//                R.id.action_savedNewsFragment_to_articleFragment,
//                bundle
//            )
//        }
//
//        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ){
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                val position = viewHolder.adapterPosition
//                val article = newsAdapter.differ.currentList[position]
//                viewModel.deleteArticle(article)
//
//                Snackbar.make(view, "News Deleted!", Snackbar.LENGTH_SHORT).apply {
//                    setAction("Undo"){
//                        viewModel.saveArticle(article)
//                    }
//                    show()
//                }
//            }
//        }
//
//        ItemTouchHelper(itemTouchHelper).apply {
//            attachToRecyclerView(binding.rvSavedNews)
//        }
//
//        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
//
//            newsAdapter.differ.submitList(it)
//
//        })
//
//    }

    private fun setUpRV(){
        newsAdapter = NewsAdapter()
         binding.rvSavedNews.apply {

             layoutManager = LinearLayoutManager(activity)
             adapter = newsAdapter

         }
    }

}