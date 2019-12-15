package com.droidcon.info.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.droidcon.info.R
import javax.inject.Inject

class InfoOptionsAdapter @Inject constructor() : ListAdapter<InfoOption, InfoOptionViewHolder>(InfoOptionsItemCallback) {

    private object ViewType {
        const val Row = 0
        const val Map = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoOptionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ViewType.Row -> InfoOptionRowViewHolder(
                layoutInflater.inflate(R.layout.info_option_row, parent, false)
            )
            ViewType.Map -> InfoOptionMapViewHolder(
                layoutInflater.inflate(R.layout.venue_map_row, parent, false)
            )
            else -> throw IllegalArgumentException("Incorrect view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: InfoOptionViewHolder, position: Int) {
        when (holder) {
            is InfoOptionRowViewHolder -> holder.bind(getItem(position) as InfoOption.Row)
            is InfoOptionMapViewHolder -> holder.bind(getItem(position) as InfoOption.Map)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is InfoOption.Row -> ViewType.Row
            is InfoOption.Map -> ViewType.Map
        }
}