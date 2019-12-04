package com.droidcon.commons.recyclerview

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    val drawable = context.getDrawable(drawableRes)
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}