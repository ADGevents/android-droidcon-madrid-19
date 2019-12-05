package com.droidcon.speakers.presentation.speakerdetail.recyclerview

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.speakerdetail.model.SpeakerSessionState

class SpeakerTalkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(speakerSessionState: SpeakerSessionState) {
        itemView.findViewById<TextView>(R.id.talkTitle).text = speakerSessionState.talkTitle
        itemView.findViewById<TextView>(R.id.talkSubtitle).text = speakerSessionState.talkSubtitle
        itemView.findViewById<ImageButton>(R.id.starSession).run {
            visibility = View.VISIBLE
            if (speakerSessionState.isStarred) {
                setImageResource(R.drawable.ic_star_filled_24dp)
                contentDescription = context.getString(R.string.unmark_as_favourite)
            } else {
                setImageResource(R.drawable.ic_star_empty_24dp)
                contentDescription = context.getString(R.string.mark_as_favourite)
            }
            setOnClickListener {
                speakerSessionState.onStarClicked(speakerSessionState.id, speakerSessionState.isStarred)
            }
        }
    }
}