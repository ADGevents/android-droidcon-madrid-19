package com.droidcon.info.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.droidcon.info.R

sealed class InfoOptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class InfoOptionRowViewHolder(itemView: View) : InfoOptionViewHolder(itemView) {

    fun bind(infoOptionRow: InfoOption.Row) {
        itemView.findViewById<TextView>(R.id.title).run {
            text = context.getString(infoOptionRow.title)
            setCompoundDrawablesRelativeWithIntrinsicBounds(infoOptionRow.icon, 0, R.drawable.ic_arrow, 0)
        }

        itemView.setOnClickListener { infoOptionRow.action() }
    }
}

class InfoOptionMapViewHolder(itemView: View) : InfoOptionViewHolder(itemView) {

    fun bind(infoOptionMap: InfoOption.Map) {
        itemView.findViewById<ImageView>(R.id.venueMap).run {
            Glide.with(itemView)
                .load(infoOptionMap.image)
                .transform(FitCenter())
                .placeholder(R.drawable.staticmap_bg)
                .into(this)
        }
        itemView.findViewById<View>(R.id.dummyViewForRipple).setOnClickListener {
            infoOptionMap.action()
        }
    }
}