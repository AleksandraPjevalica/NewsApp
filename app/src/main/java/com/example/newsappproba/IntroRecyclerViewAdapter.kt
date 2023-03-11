package com.example.newsappproba

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappproba.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso

class IntroRecyclerViewAdapter(private var context: Context, val handler: Callbacks
) :
    RecyclerView.Adapter<IntroRecyclerViewAdapter.NewsViewHolder>() {
    private var newsList = mutableListOf<NewsArticle>()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(binding: NewsViewHolder, position: Int) {
        val titlesList = mutableListOf<String>()
        val descriptionList = mutableListOf<String>()
        val thumbList = mutableListOf<String>()
        val imageList = mutableListOf<String>()
        var thumbLink = ""
        var imageLink = ""
        val httpsString = "https"
        if (newsList.isNotEmpty()) {
            newsList.forEach { article ->
                titlesList.add(article.title!!.trim())
                descriptionList.add(article.description!!.trim())

                if (article.thumb!!.contains("http") && !article.thumb!!.contains(
                        "https"
                    )
                ) {
                    thumbLink = article.thumb!!.drop(4)
                    thumbLink = httpsString + thumbLink
                    thumbList.add(thumbLink)

                }
                if (article.image?.contains("http") == true && !article.image?.contains(
                        "https"
                    )!!
                ) {
                    imageLink = article.image!!.drop(4)
                    imageLink = httpsString + imageLink
                    imageList.add(imageLink)

                }


        }

        binding.title.text = titlesList[position]
        binding.description.text = descriptionList[position]
        if (thumbList[position] != null && !thumbList[position].isEmpty()) {
            Picasso.with(context)
                .load(thumbList[position])
                .fit()
                .centerCrop()
                .into(binding.thumb)
        } else {
            binding.thumb.setImageResource(R.drawable.start)
        }

            binding.itemView.setOnClickListener(View.OnClickListener {
                val newsTable = newsList[position]
                if (newsTable != null) {
                    handler.handleUserData(newsTable)
                }
            })

    }

}

override fun getItemCount(): Int {
    return newsList.size
}

class NewsViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    val title: TextView = binding.title
    val description: TextView = binding.description
    val thumb: ImageView = binding.thumb
}

    interface Callbacks {
        fun handleUserData(data: NewsArticle)
    }

    fun setArticles(list: List<NewsArticle>) {
    newsList.clear()
    newsList.addAll(list)
    notifyDataSetChanged()
}
}
