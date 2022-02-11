package com.peerbitskuldeep.newzpl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.peerbitskuldeep.newzpl.R
import com.peerbitskuldeep.newzpl.databinding.FragmentArticleBinding
import com.peerbitskuldeep.newzpl.ui.NewsActivity
import com.peerbitskuldeep.newzpl.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment: Fragment() {

    lateinit var binding: FragmentArticleBinding
    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentArticleBinding.inflate(
            inflater,
            container,
            false
        )
        viewModel = (activity as NewsActivity).vm
        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(requireView(),"News saved!", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewModel = (activity as NewsActivity).vm
//
//        val article = args.article
//
//        webView.apply {
//            webViewClient = WebViewClient()
//            loadUrl(article.url)
//        }
//
//        fab.setOnClickListener {
//            viewModel.saveArticle(article)
//            Snackbar.make(view,"News saved!", Snackbar.LENGTH_SHORT).show()
//
//        }
//
//    }

}