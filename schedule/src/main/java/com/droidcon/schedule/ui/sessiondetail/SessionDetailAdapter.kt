package com.droidcon.schedule.ui.sessiondetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.droidcon.schedule.R
import javax.inject.Inject

class SessionDetailAdapter @Inject constructor()
    : ListAdapter<SessionDetailRow, SessionDetailViewHolder>(SessionDetailRowDiffItemCallback) {

    private object ViewType {
        const val SUBTITLE = 0
        const val DESCRIPTION = 1
        const val SPEAKERS_LABEL = 2
        const val SPEAKER = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.SUBTITLE -> SubtitleViewHolder(
                layoutInflater.inflate(R.layout.session_detail_subtitle_row, parent, false)
            )
            ViewType.DESCRIPTION -> DescriptionViewHolder(
                layoutInflater.inflate(R.layout.session_detail_description_row, parent, false)
            )
            ViewType.SPEAKERS_LABEL -> SpeakersLabelViewHolder(
                layoutInflater.inflate(R.layout.session_detail_speakers_label_row, parent, false)
            )
            ViewType.SPEAKER -> SpeakerViewHolder(
                layoutInflater.inflate(R.layout.session_speaker_row, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: SessionDetailViewHolder, position: Int) {
        when (holder) {
            is SubtitleViewHolder -> holder.bind(getItem(position) as SessionDetailRow.Subtitle)
            is DescriptionViewHolder -> holder.bind(getItem(position) as SessionDetailRow.Description)
            is SpeakersLabelViewHolder -> Unit
            is SpeakerViewHolder -> holder.bind(getItem(position) as SessionDetailRow.Speaker)
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is SessionDetailRow.Subtitle -> ViewType.SUBTITLE
        is SessionDetailRow.Description -> ViewType.DESCRIPTION
        SessionDetailRow.SpeakersLabel -> ViewType.SPEAKERS_LABEL
        is SessionDetailRow.Speaker -> ViewType.SPEAKER
    }
}