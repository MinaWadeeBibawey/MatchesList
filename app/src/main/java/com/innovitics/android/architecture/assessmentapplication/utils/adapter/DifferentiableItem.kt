package com.innovitics.android.architecture.assessmentapplication.utils.adapter

interface DifferentiableItem {

    fun getUniqueIdentifier(): Any

    fun getContent(): String
}
