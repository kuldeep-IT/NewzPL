package com.peerbitskuldeep.newzpl.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peerbitskuldeep.newzpl.R
import com.peerbitskuldeep.newzpl.base.BaseViewHolder
import com.peerbitskuldeep.newzpl.databinding.ItemArticlePreviewBinding
import com.peerbitskuldeep.newzpl.jsondata.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val mBinding: ItemArticlePreviewBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            val article = differ.currentList[position]
            mBinding.articleModel = article

            itemView.setOnClickListener {
                onClickListener?.onClick(article)
//                onItemClickLstnr?.invoke(article)
            }

            mBinding.executePendingBindings()
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url //bcz url also work as id
        }

        //compares two contents
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        var mBinding = ItemArticlePreviewBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ArticleViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
//        val article = differ.currentList[position]
        holder.onBind(position)

//        holder.mBinding.tvDescription.text = article.description

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnClickListener {
        fun onClick(article: Article)
    }

    var onClickListener: OnClickListener? = null

    //onClick
    var onItemClickLstnr: ((Article) -> Unit)? = null

}