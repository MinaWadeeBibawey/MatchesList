package com.innovitics.android.architecture.assessmentapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.innovitics.android.architecture.assessmentapplication.database.MatchesEntity
import com.innovitics.android.architecture.assessmentapplication.databinding.ItemAdapterBinding

class MatchesOfTheDayAdapter :
    ListAdapter<MatchesEntity, MatchesOfTheDayAdapter.MatchesListViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchesListViewHolder {
        return MatchesListViewHolder(ItemAdapterBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MatchesListViewHolder, position: Int) {
        val matchProperty = getItem(position)
        holder.bind(matchProperty)
    }

    class MatchesListViewHolder(private var binding: ItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(matchesEntity : MatchesEntity) {
            binding.matchesModel = matchesEntity
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MatchesEntity>() {
        override fun areItemsTheSame(oldItem: MatchesEntity, newItem: MatchesEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MatchesEntity, newItem: MatchesEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }
}