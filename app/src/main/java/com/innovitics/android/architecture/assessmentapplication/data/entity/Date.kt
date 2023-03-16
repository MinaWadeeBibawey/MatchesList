package com.innovitics.android.architecture.assessmentapplication.data.entity

import com.innovitics.android.architecture.assessmentapplication.utils.adapter.DifferentiableItem

class Date(
    val value: String
) : DifferentiableItem {

    override fun getUniqueIdentifier(): Any = value

    override fun getContent(): String = toString()
}
