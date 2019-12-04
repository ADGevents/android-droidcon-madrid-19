package com.droidcon.speakers.presentation.speakerlist.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.droidcon.speakers.presentation.speakerlist.model.SpeakerState
import javax.inject.Inject

class SpeakerRowDiffCallback @Inject constructor() : DiffUtil.ItemCallback<SpeakerState>() {

    override fun areItemsTheSame(oldItem: SpeakerState, newItem: SpeakerState): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: SpeakerState, newItem: SpeakerState): Boolean =
        oldItem == newItem
}