package com.innovitics.android.architecture.assessmentapplication.adapters

import android.annotation.SuppressLint

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.innovitics.android.architecture.assessmentapplication.R
import com.innovitics.android.architecture.assessmentapplication.data.entity.Date
import com.innovitics.android.architecture.assessmentapplication.database.MatchesEntity

class MatchesListAdapter(
    private val onClickListener: (MatchesEntity) -> Unit,
) :
    ListAdapter<Any, RecyclerView.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_adapter -> MatchesListViewHolder.create(parent, onClickListener)
            R.layout.item_date -> DateViewHolder.create(parent)
            R.layout.today_and_tomorrow_matches_item_adapter -> MatchesOfTheDayViewHolder.create(
                parent
            )
            else -> throw IllegalArgumentException("An unknown view type was passed.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_adapter -> (holder as MatchesListViewHolder).bind(getItem(position) as MatchesEntity)
            R.layout.item_date -> (holder as DateViewHolder).bind(getItem(position) as Date)
            R.layout.today_and_tomorrow_matches_item_adapter -> (holder as MatchesOfTheDayViewHolder).bind(
                getItem(position) as ArrayList<MatchesEntity>
            )
            else -> throw IllegalArgumentException("An unknown view type was passed.")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is MatchesEntity) {
            R.layout.item_adapter
        } else if (getItem(position) is Date) {
            R.layout.item_date
        } else {
            R.layout.today_and_tomorrow_matches_item_adapter

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem == newItem
        }
    }
}