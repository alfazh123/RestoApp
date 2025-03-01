package com.example.restoapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.domain.model.Restaurant
import com.example.core.ui.RestaurantsAdapter
import com.example.restoapp.databinding.FragmentListRestoBinding
import com.example.restoapp.presentation.detail.DetailRestaurantActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentListRestoBinding

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListRestoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()


        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
//                    lifecycleScope.launch {
                        searchRestaurants(newText)
//                    }
                }
                return true
            }

        })

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)
    }

    private fun observeData() {
        lifecycleScope.launch {
            homeViewModel.restaurants.collect { restaurants ->
                Log.d("HomeFragment1", "Data: ${restaurants.data}")
//                if (restaurants.data.isNullOrEmpty()) {
                    when (restaurants) {
                        is Resource.Loading -> showLoading(true)
                        is Resource.Success -> {
                            showLoading(false)
                            setList(restaurants.data!!)
                            Log.d("HomeFragment2", "Data: ${restaurants.data}")
                        }
                        is Resource.Error -> {
                            showLoading(false)
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            Log.e("HomeFragment", "Error: ${restaurants.message}")
                        }
                    }
//                }
            }
        }
    }

    private fun searchRestaurants(query: String) {
        homeViewModel.searchRestaurants(query).asLiveData().observe(viewLifecycleOwner) { restaurants ->
            when (restaurants) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    setList(restaurants.data!!)
                    Log.d("HomeFragment", "Search Data: ${restaurants.data}")
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    Log.e("HomeFragment", "Error: ${restaurants.message}")
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setList(restaurants: List<Restaurant>) {

        val listRestoAdapter = RestaurantsAdapter()
        listRestoAdapter.submitList(restaurants)
        binding.recyclerView.adapter = listRestoAdapter

        listRestoAdapter.setOnItemClickCallback(object : RestaurantsAdapter.OnItemClickBack {
            override fun onItemClicked(data: Restaurant) {
                val intent = Intent(context, DetailRestaurantActivity::class.java)
                intent.putExtra(DetailRestaurantActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }
        })
    }
}