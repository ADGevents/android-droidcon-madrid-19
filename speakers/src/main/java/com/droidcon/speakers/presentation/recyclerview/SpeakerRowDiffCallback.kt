package com.droidcon.speakers.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.droidcon.speakers.presentation.SpeakerRowModel
import javax.inject.Inject

class SpeakerRowDiffCallback @Inject constructor() : DiffUtil.ItemCallback<SpeakerRowModel>() {

    override fun areItemsTheSame(oldItem: SpeakerRowModel, newItem: SpeakerRowModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: SpeakerRowModel, newItem: SpeakerRowModel): Boolean =
        oldItem == newItem
}