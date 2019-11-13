package com.droidcon.speakers.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.SpeakerRowModel
import javax.inject.Inject

class SpeakersAdapter @Inject constructor(
    speakerRowDiffCallback: SpeakerRowDiffCallback
) : ListAdapter<SpeakerRowModel, SpeakerViewHolder>(speakerRowDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.speaker_row,
            parent,
            false
        )
        return SpeakerViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: SpeakerViewHolder, position: Int) {
        val speaker = getItem(position)
        holder.bind(speaker)
    }
}