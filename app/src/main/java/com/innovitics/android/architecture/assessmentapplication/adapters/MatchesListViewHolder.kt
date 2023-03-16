package com.innovitics.android.architecture.assessmentapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innovitics.android.architecture.assessmentapplication.database.MatchesEntity
import com.innovitics.android.architecture.assessmentapplication.databinding.ItemAdapterBinding

class MatchesListViewHolder(
    private val binding: ItemAdapterBinding,
    private val clickListener: (MatchesEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(matches: MatchesEntity) {
        binding.matchesModel = matches
        binding.handler = this
        binding.executePendingBindings()
    }

    fun click(movie: MatchesEntity) {
        clickListener.invoke(movie)
    }

    companion object {
        fun create(
            container: ViewGroup,
            clickListener: (MatchesEntity) -> Unit
        ): MatchesListViewHolder {
            val binding = ItemAdapterBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
            return MatchesListViewHolder(binding, clickListener)
        }
    }
}
