package com.innovitics.android.architecture.assessmentapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innovitics.android.architecture.assessmentapplication.R
import com.innovitics.android.architecture.assessmentapplication.database.MatchesEntity
import com.innovitics.android.architecture.assessmentapplication.databinding.ItemAdapterBinding
import com.innovitics.android.architecture.assessmentapplication.databinding.TodayAndTomorrowMatchesItemAdapterBinding

class MatchesOfTheDayViewHolder(
    private val binding: TodayAndTomorrowMatchesItemAdapterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(matches: ArrayList<MatchesEntity>) {
        binding.executePendingBindings()

        val viewModelAdapter = MatchesOfTheDayAdapter()

        binding.todayAndTomorrowMatchesRv.apply {
            adapter = viewModelAdapter
            viewModelAdapter.submitList(matches)
        }
    }


    companion object {
        fun create(
            container: ViewGroup
        ): MatchesOfTheDayViewHolder {
            val binding = TodayAndTomorrowMatchesItemAdapterBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
            return MatchesOfTheDayViewHolder(binding)
        }
    }
}
