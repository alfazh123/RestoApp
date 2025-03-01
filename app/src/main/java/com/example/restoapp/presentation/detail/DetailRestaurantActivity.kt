package com.example.restoapp.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.restoapp.R
import com.example.core.domain.model.DetailRestaurant
import com.example.core.domain.model.Restaurant
import com.example.core.ui.ReviewsAdapter
import com.example.restoapp.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailRestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantId = intent.getStringExtra(EXTRA_ID)
        if (restaurantId != null) {
            detailViewModel.getRestaurantById(restaurantId).observe(this@DetailRestaurantActivity) { restaurant ->
                if (restaurant != null) {

                    setUi(restaurant.data!!)
                    detailViewModel.isRestaurantExist(restaurantId).observe(this@DetailRestaurantActivity) { isExist ->

                        val entity = Restaurant(
                            id = restaurant.data!!.id,
                            name = restaurant.data!!.name,
                            description = restaurant.data!!.description,
                            pictureId = restaurant.data!!.pictureId,
                            city = restaurant.data!!.city,
                            rating = restaurant.data!!.rating
                        )

                        if (isExist) {
                            binding.favButton.setImageResource(R.drawable.fav_check)
                            binding.favButton.setOnClickListener {
                                detailViewModel.deleteFavoriteRestaurant(entity)
                            }
                        } else {
                            binding.favButton.setImageResource(R.drawable.fav_uncheck)
                            binding.favButton.setOnClickListener {
                                detailViewModel.insertFavoriteRestaurant(entity)
                            }
                        }
                    }
//                    binding.favButton.setOnClickListener {
//                        detailViewModel.isRestaurantExist(restaurantId).observe(this@DetailRestaurantActivity) { isExist ->
//                            val entity = Restaurant(
//                                id = restaurant.data?.id!!,
//                                name = restaurant.data.name,
//                                description = restaurant.data.description,
//                                pictureId = restaurant.data.pictureId,
//                                city = restaurant.data.city,
//                                rating = restaurant.data.rating
//                            )
//
//                            if (isExist) {
//                                detailViewModel.deleteFavoriteRestaurant(entity)
//                                Toast.makeText(this@DetailRestaurantActivity, "Restaurant already in Favorite", Toast.LENGTH_SHORT).show()
//                            } else {
//                                detailViewModel.insertFavoriteRestaurant(entity)
//                                Toast.makeText(this@DetailRestaurantActivity, "Added to Favorite", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
                }
            }

        }
    }

    private fun setUi(restaurant: DetailRestaurant) {
        binding.progressBar.visibility = View.GONE
        val layoutManager = LinearLayoutManager(this)
        binding.rvComments.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvComments.addItemDecoration(itemDecoration)

        with(restaurant) {
            binding.restaurantName.text = name
            binding.restaurantDescription.text = description
            binding.restaurantAddress.text = address
            binding.restaurantRating.text = "$rating / 5"
            Glide.with(this@DetailRestaurantActivity)
                .load("https://restaurant-api.dicoding.dev/images/medium/$pictureId")
                .into(binding.restaurantImage)

            val commentAdapter = ReviewsAdapter()
            commentAdapter.submitList(customerReviewDomains)
            binding.rvComments.adapter = commentAdapter
        }

        binding.sendButton.setOnClickListener {
            Toast.makeText(this@DetailRestaurantActivity, "Send Comment", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}