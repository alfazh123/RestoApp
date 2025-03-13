package com.example.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.R
import com.example.core.domain.model.CustomerReviewsDomain
import com.example.core.databinding.CommentsItemBinding
import com.example.core.ui.ReviewsAdapter.ViewHolder

class ReviewsAdapter: ListAdapter<CustomerReviewsDomain, ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comments_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CommentsItemBinding.bind(itemView)
        fun bind(comment: CustomerReviewsDomain) {
            with(binding) {
                commentName.text = comment.name
                commentDate.text = comment.date
                commentContent.text = comment.review
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<CustomerReviewsDomain>() {
        override fun areItemsTheSame(oldItem: CustomerReviewsDomain, newItem: CustomerReviewsDomain): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CustomerReviewsDomain, newItem: CustomerReviewsDomain): Boolean {
            return oldItem == newItem
        }
    }
}