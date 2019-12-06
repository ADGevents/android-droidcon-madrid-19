package com.droidcon.speakers.presentation.speakerdetail.recyclerview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.droidcon.speakers.presentation.speakerdetail.model.SpeakerDetailRow

object SpeakerDetailDiffCallback : DiffUtil.ItemCallback<SpeakerDetailRow>() {

    override fun areItemsTheSame(oldItem: SpeakerDetailRow, newItem: SpeakerDetailRow): Boolean = when {
        oldItem === SpeakerDetailRow.SessionsHeader && newItem === SpeakerDetailRow.SessionsHeader -> true
        oldItem is SpeakerDetailRow.Description && newItem is SpeakerDetailRow.Description -> true
        oldItem is SpeakerDetailRow.Session && newItem is SpeakerDetailRow.Session -> oldItem.id == newItem.id
        else -> false
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SpeakerDetailRow, newItem: SpeakerDetailRow): Boolean = when {
        oldItem is SpeakerDetailRow.Session && newItem is SpeakerDetailRow.Session -> oldItem == newItem
        else -> true
    }
}