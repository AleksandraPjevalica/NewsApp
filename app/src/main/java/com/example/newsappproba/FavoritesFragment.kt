package com.example.newsappproba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappproba.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment(), FavoritesAdapter.Callbacks {
    val viewModel: NewsViewModel by activityViewModels()
    var favoriteTable = mutableListOf<FavoritesTable>()
    lateinit var favoritesAdapter : FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (container != null) {
            container.removeAllViews()
        }

        favoritesAdapter = FavoritesAdapter(
            requireContext(), this
        )

        val binding = DataBindingUtil.inflate<FragmentFavoritesBinding>(
            inflater,
            R.layout.fragment_favorites, container, false
        )
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavorites.adapter = favoritesAdapter



        viewModel.favoriteItem.observeOnce(viewLifecycleOwner, Observer {
            val favoritesTable = it
            viewModel.insertFavoriteItems(favoritesTable)

        })
        viewModel.getAllFavoriteItems().observe(viewLifecycleOwner, Observer{
            favoritesAdapter.setArticles(it)
            it.forEach {

            }

        })
        favoriteTable.forEach {

        }

        val imageLink = ""

        favoriteTable.forEach {
        }
        return binding.root
    }

    override fun handleUserData(data: FavoritesTable) {
        var newsArticle = NewsArticle()
        newsArticle.title = data.favoritesTableTitle
        newsArticle.description = data.favoritesTableDescription
        newsArticle.image = data.favoritesTableImageUrl
        viewModel.setArticle(newsArticle)
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }


}