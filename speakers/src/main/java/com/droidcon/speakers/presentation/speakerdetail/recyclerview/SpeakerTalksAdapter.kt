package com.droidcon.speakers.presentation.speakerdetail.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.speakerdetail.model.SpeakerDetailRow
import javax.inject.Inject

class SpeakerTalksAdapter @Inject constructor() : ListAdapter<SpeakerDetailRow, SpeakerDetailViewHolder>(SpeakerDetailDiffCallback) {

    private object ViewType {
        const val DESCRIPTION = 0
        const val SESSIONS_HEADER = 1
        const val SESSION = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.DESCRIPTION -> SpeakerDetailViewHolder.Description(
                layoutInflater.inflate(R.layout.speaker_description_row, parent, false)
            )
            ViewType.SESSIONS_HEADER -> SpeakerDetailViewHolder.SessionsHeader(
                layoutInflater.inflate(R.layout.speaker_sessions_header, parent, false)
            )
            ViewType.SESSION -> SpeakerDetailViewHolder.Session(
                layoutInflater.inflate(R.layout.speaker_talk_row, parent, false)
            )
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: SpeakerDetailViewHolder, position: Int) {
        when (holder) {
            is SpeakerDetailViewHolder.Description -> {
                holder.bind(getItem(position) as SpeakerDetailRow.Description)
            }
            is SpeakerDetailViewHolder.SessionsHeader -> Unit
            is SpeakerDetailViewHolder.Session -> {
                holder.bind(getItem(position) as SpeakerDetailRow.Session)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is SpeakerDetailRow.Description -> ViewType.DESCRIPTION
        SpeakerDetailRow.SessionsHeader -> ViewType.SESSIONS_HEADER
        is SpeakerDetailRow.Session -> ViewType.SESSION
    }
}