package com.yandey.githubuser.data.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.yandey.githubuser.R

object AppUtils {
    fun ImageView.loadCircleImage(context: Context, imageSource: String?) {
        Glide.with(context)
            .load(imageSource)
            .into(this)
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(R.string.close) {
        }.show()
    }
}