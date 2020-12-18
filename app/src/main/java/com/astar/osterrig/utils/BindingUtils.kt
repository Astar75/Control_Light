package com.astar.osterrig.utils

import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.recyclerview.widget.RecyclerView


/* fragment color */
@BindingAdapter("color_drawable")
fun View.setColor(color: Int) {
    Log.e("Binding", "setColor: $color")
    val background = this.background
    background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    this.setBackgroundDrawable(background)
}

@BindingAdapter("alpha_drawable")
fun View.setAlphaChannel(@IntRange(from = 0, to = 255) alpha: Int) {
    this.alpha = (((alpha.toFloat())/(255.0)*(1.0-0.15)+0.15).toFloat())
}

/* fragment_cct */
@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}

@BindingAdapter("color_background_drawable")
fun FrameLayout.setColorBackground(color: Int) {
    val background = this.background
    background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    this.setBackgroundDrawable(background)
}