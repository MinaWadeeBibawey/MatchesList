package com.innovitics.android.architecture.assessmentapplication.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("teamsName")
fun bindTextViewToDisplayTeamsName(textView: TextView, teamName: String) {
    textView.text = teamName
}

@BindingAdapter("matchStatus")
fun bindTextViewToDisplayMatchStatus(textView: TextView, matchStatus: String) {
    textView.text = matchStatus
}


@BindingAdapter("app:score")
fun bindTextViewToDisplayScore(textView: TextView, score: String) {
    textView.text = score
}

@BindingAdapter("app:wishlist")
fun bindTextViewToDisplayWishListStatus(textView: TextView, wishlistStatus: Boolean) {
    if (wishlistStatus) {
        textView.text = "added To wish list"
    } else {
        textView.text = "add To wish list"
    }
}



