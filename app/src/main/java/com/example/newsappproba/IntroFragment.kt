package com.example.newsappproba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappproba.databinding.FragmentIntroBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class IntroFragment() : Fragment() , IntroRecyclerViewAdapter.Callbacks {

    val model : NewsViewModel by activityViewModels()
    lateinit var introRecyclerViewAdapter : IntroRecyclerViewAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (container != null) {
            container.removeAllViews();
        }
        introRecyclerViewAdapter =IntroRecyclerViewAdapter(
            requireContext(), this)

        container?.clearDisappearingChildren();



        val binding = DataBindingUtil.inflate<FragmentIntroBinding>(
            inflater,
            R.layout.fragment_intro, container, false
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = introRecyclerViewAdapter
        refresh()

        binding.refreshIcon.setOnClickListener {
            refresh()
        }


        return binding.root
    }

    fun refresh() {
        model.getLiveData().observe(viewLifecycleOwner, object :
            Observer<List<NewsArticle>?> {
            override fun onChanged(newsList: List<NewsArticle>?) {
                introRecyclerViewAdapter.setArticles(newsList!!)

            }

        })
    }

    override fun handleUserData(data: NewsArticle) {
        model.setArticle(data)
        transition()


    }
    fun transition(){
        view?.findNavController()?.navigate(R.id.action_introFragment_to_newsArticleFragment)
    }
}