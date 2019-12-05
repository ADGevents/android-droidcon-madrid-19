package com.droidcon.speakers.presentation.speakerdetail.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.speakerdetail.model.SpeakerSessionState
import javax.inject.Inject

class SpeakerTalksAdapter @Inject constructor(
    speakerTalkDiffCallback: SpeakerTalkDiffCallback
) : ListAdapter<SpeakerSessionState, SpeakerTalkViewHolder>(speakerTalkDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerTalkViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.speaker_talk_row,
            parent,
            false
        )
        return SpeakerTalkViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SpeakerTalkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}