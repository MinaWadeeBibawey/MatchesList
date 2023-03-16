package com.innovitics.android.architecture.assessmentapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innovitics.android.architecture.assessmentapplication.data.entity.Date
import com.innovitics.android.architecture.assessmentapplication.data.entity.Match
import com.innovitics.android.architecture.assessmentapplication.databinding.ItemDateBinding

class DateViewHolder(private val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(date: Date) {
        binding.date = date.value
        binding.executePendingBindings()
    }

    companion object {
        fun create(container: ViewGroup): DateViewHolder {
            val binding = ItemDateBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
            return DateViewHolder(binding)
        }
    }
}
