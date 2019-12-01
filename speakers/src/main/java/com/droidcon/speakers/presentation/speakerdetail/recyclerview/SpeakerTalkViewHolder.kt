package com.droidcon.speakers.presentation.speakerdetail.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.speakerdetail.model.SpeakerTalk

class SpeakerTalkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(speakerTalk: SpeakerTalk) {
        itemView.findViewById<TextView>(R.id.talkTitle).text = speakerTalk.talkTitle
        itemView.findViewById<TextView>(R.id.talkSubtitle).text = speakerTalk.talkSubtitle
    }
}