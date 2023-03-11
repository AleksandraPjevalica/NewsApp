package com.example.newsappproba

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.room.InvalidationTracker
import com.example.newsappproba.databinding.FragmentNewsArticleBinding
import com.squareup.picasso.Picasso


class NewsArticleFragment() : Fragment(){
    lateinit var newsItem: NewsArticle
    lateinit var intent: Intent
    val httpsString = "https"
    var title: String = ""
    var description: String = ""
    var imageLink: String = ""
    val viewModel: NewsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        intent = Intent(context, FavoritesFragment::class.java)
        container?.clearDisappearingChildren();


        val binding = DataBindingUtil.inflate<FragmentNewsArticleBinding>(
            inflater,
            R.layout.fragment_news_article, container, false
        )



        viewModel.newsItem.observe(viewLifecycleOwner, Observer {
            newsItem = it
            binding.articleTitle.text = newsItem.title.toString()
            title = newsItem.title.toString()
            description = newsItem.description.toString()

            binding.articleDescription.text = newsItem.description.toString()
            imageLink = newsItem.image.toString().trim()
            if (imageLink.isNotEmpty()) {
                if (imageLink.contains("http") && !imageLink.contains("https")) {
                    imageLink = imageLink.drop(4)
                    imageLink = httpsString + imageLink
                    Picasso.with(context)
                        .load(imageLink)
                        .fit()
                        .centerCrop()
                        .into(binding.articleImage)

                }
                else if(imageLink.contains("https")){
                    Picasso.with(context)
                        .load(imageLink)
                        .fit()
                        .centerCrop()
                        .into(binding.articleImage)
                }
            } else{
                binding.articleImage.setImageResource(R.drawable.start)
            }

            binding.shareIcon.setOnClickListener {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_TEXT, title + "\n\n" + description)
                shareIntent.type = "text/plain"
                startActivity(shareIntent)
            }
            binding.favoritesCard.setOnClickListener {

                val favoritesTable = FavoritesTable(title.trim(), description.trim(), imageLink.trim())
                viewModel.insertFavoriteItems(favoritesTable)

                viewModel.getAllFavoriteItems().observe(viewLifecycleOwner, object : Observer<List<FavoritesTable>>{
                    override fun onChanged(it: List<FavoritesTable>?) {
                        if(!it.isNullOrEmpty()){
                            it.forEach {
                                if (title.trim() != it.favoritesTableTitle?.trim()){
                                    viewModel.setFavoriteArticle(favoritesTable)

                                }
                            }

                        }


                    }

                })

                view?.findNavController()?.navigate(R.id.action_newsArticleFragment_to_favoritesFragment)
            }

        })

        if (description != "") {
            binding.articleDescription.text = description
        } else {
            binding.articleDescription.text = "DESCRIPTION"
        }
        if (imageLink != null && imageLink.isNotEmpty()) {
            Picasso.with(context)
                .load(imageLink)
                .fit()
                .centerCrop()
                .into(binding.articleImage)
        } else {
            binding.articleImage.setImageResource(R.drawable.start)
        }




        return binding.root

    }
}


