package com.androiddevs.mvvmnewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.models.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.articleId == newItem.articleId
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        article.apply {
            holder.itemView.apply {
                Glide.with(this).load(urlToImage).into(ivArticleImage)
                tvSource.text = source?.name
                tvTitle.text = title
                tvDescription.text = description
                tvPublishedAt.text = publishedAt
                setOnClickListener {
                    onItemClickListener?.let {
                        it(article)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article)->Unit)? = null

    fun setOnItemClickListener(listener: (Article)-> Unit) {
        onItemClickListener = listener
    }

}