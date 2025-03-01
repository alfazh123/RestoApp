package com.example.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.domain.model.Restaurant
import com.example.core.databinding.RestaurantItemBinding
import com.example.core.ui.RestaurantsAdapter.RestaurantViewHolder

class RestaurantsAdapter : ListAdapter<Restaurant, RestaurantViewHolder>(DIFF_CALLBACK) {
    private lateinit var onEventClickCallBack: OnItemClickBack

    interface OnItemClickBack {
        fun onItemClicked(data: Restaurant)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickBack) {
        this.onEventClickCallBack = onItemClickCallback
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Restaurant>() {
            override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }
        }
    }

    class RestaurantViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = RestaurantItemBinding.bind(item)

        fun bind(restaurant: Restaurant) {
            with(binding) {
                restaurantName.text = restaurant.name
                restaurantAddress.text = restaurant.city
                restaurantRating.text = "${restaurant.rating} ⭐"
                restaurantDescription.text = restaurant.description
                Glide.with(itemView.context)
                    .load("https://restaurant-api.dicoding.dev/images/medium/${restaurant.pictureId}")
                    .into(restaurantImage)

                // check if is favorite it will show favorite icon
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item, parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            onEventClickCallBack.onItemClicked(getItem(position))
        }
    }
}