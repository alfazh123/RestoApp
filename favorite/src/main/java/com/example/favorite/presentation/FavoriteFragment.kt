package com.example.favorite.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.model.Restaurant
import com.example.core.ui.RestaurantsAdapter
import com.example.favorite.databinding.FragmentListFavoriteBinding
import com.example.favorite.di.favModule
import com.example.restoapp.presentation.detail.DetailRestaurantActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentListFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favModule)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

        favoriteViewModel.favoriteRestaurants.observe(viewLifecycleOwner) { restaurants ->
            showLoading()
            setList(restaurants)
            Log.d("FavoriteFragment", "onViewCreated: $restaurants")
        }

    }

    private fun showLoading(state: Boolean = false) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setList(restaurants: List<Restaurant>) {
        val restaurantAdapter = RestaurantsAdapter()
        restaurantAdapter.submitList(restaurants)
        binding.recyclerView.adapter = restaurantAdapter

        restaurantAdapter.setOnItemClickCallback(object : RestaurantsAdapter.OnItemClickBack {
            override fun onItemClicked(data: Restaurant) {
                val intent = Intent(context, DetailRestaurantActivity::class.java)
                intent.putExtra(DetailRestaurantActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }
        })
    }
}