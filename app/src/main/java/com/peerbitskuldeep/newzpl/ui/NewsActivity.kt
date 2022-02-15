package com.peerbitskuldeep.newzpl.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.peerbitskuldeep.newzpl.R
import com.peerbitskuldeep.newzpl.databinding.ActivityNewsBinding
import com.peerbitskuldeep.newzpl.db.ArticleDatabase
import com.peerbitskuldeep.newzpl.repository.NewsRepository
import com.peerbitskuldeep.newzpl.viewmodel.NewsViewModel
import com.peerbitskuldeep.newzpl.viewmodel.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

   lateinit var vm: NewsViewModel
   lateinit var binding : ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)

        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(repository)
        vm = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)

        binding.bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.binding_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.bindingDetails -> {
                startActivity(Intent(this@NewsActivity, PageActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

}