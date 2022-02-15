package com.peerbitskuldeep.newzpl.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.peerbitskuldeep.newzpl.R
import com.peerbitskuldeep.newzpl.databinding.ActivityPageBinding
import com.peerbitskuldeep.newzpl.viewmodel.PageViewModel

class PageActivity : AppCompatActivity() {

    lateinit var binding: ActivityPageBinding
    lateinit var pageViewModel: PageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_page)

        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)

        binding.viewmodel = pageViewModel
        binding.lifecycleOwner = this
    }
}