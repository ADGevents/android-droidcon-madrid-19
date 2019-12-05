package com.droidcon.speakers.presentation.speakerdetail.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.droidcon.speakers.presentation.speakerdetail.model.SpeakerSessionState
import javax.inject.Inject

class SpeakerTalkDiffCallback @Inject constructor() : DiffUtil.ItemCallback<SpeakerSessionState>() {

    override fun areItemsTheSame(oldItem: SpeakerSessionState, newItem: SpeakerSessionState): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SpeakerSessionState, newItem: SpeakerSessionState): Boolean =
        oldItem == newItem
}