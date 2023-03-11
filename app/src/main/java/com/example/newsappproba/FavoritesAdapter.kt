package com.example.newsappproba

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappproba.databinding.FavoritesItemLayoutBinding
import com.squareup.picasso.Picasso

class FavoritesAdapter(
    private var context: Context,
    val handler: FavoritesAdapter.Callbacks
) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
    var favoritesItem = mutableListOf<FavoritesTable>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = FavoritesItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(binding: FavoritesViewHolder, position: Int) {
        val titlesList = mutableListOf<String>()
        val descriptionList = mutableListOf<String>()
        val imageList = mutableListOf<String>()
        var imageLink = ""
        val httpsString = "https"
        if (favoritesItem.isNotEmpty()) {
            favoritesItem.forEach {

                titlesList.add(it.favoritesTableTitle!!.trim())
                descriptionList.add(it.favoritesTableDescription!!.trim())


                    if (it.favoritesTableImageUrl.contains("http") && !it.favoritesTableImageUrl.contains("https")) {

                        imageLink = it.favoritesTableImageUrl.drop(4)
                    imageLink = httpsString + imageLink
                    imageList.add(imageLink)
                }


                else{
                    imageList.add(it.favoritesTableImageUrl)

                    }

            }
            binding.favoriteTitle.text = titlesList[position]
            binding.favoriteDescription.text = descriptionList[position]
            if (imageList.isNotEmpty()) {
                Picasso.with(context)
                    .load(imageList[position])
                    .fit()
                    .centerCrop()
                    .into(binding.favoriteImage)
            } else {
                binding.favoriteImage.setImageResource(R.drawable.start)
            }

            binding.itemView.setOnClickListener(View.OnClickListener {
                val newsTable = favoritesItem[position]
                if (newsTable != null) {
                    handler.handleUserData(newsTable)
                }
            })



        }

    }

    override fun getItemCount(): Int {
        return favoritesItem.size
    }

    class FavoritesViewHolder(binding: FavoritesItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val favoriteTitle: TextView = binding.favoriteTitle
        val favoriteDescription: TextView = binding.favoriteDescription
        val favoriteImage: ImageView = binding.favoriteImage
    }
    interface Callbacks {
        fun handleUserData(data: FavoritesTable)
    }

    fun setArticles( list: List<FavoritesTable>){
        favoritesItem.clear()
        favoritesItem.addAll(list)
        notifyDataSetChanged()
    }
}


