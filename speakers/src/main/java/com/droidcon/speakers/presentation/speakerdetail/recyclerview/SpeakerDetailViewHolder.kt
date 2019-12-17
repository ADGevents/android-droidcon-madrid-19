package com.droidcon.speakers.presentation.speakerdetail.recyclerview

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.speakerdetail.model.SpeakerDetailRow

sealed class SpeakerDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class Description(itemView: View) : SpeakerDetailViewHolder(itemView) {

        fun bind(row: SpeakerDetailRow.Description) {
            itemView.findViewById<TextView>(R.id.speakerDescription).text = row.description
        }
    }

    class SessionsHeader(itemView: View) : SpeakerDetailViewHolder(itemView)

    class Session(itemView: View) : SpeakerDetailViewHolder(itemView) {

        fun bind(session: SpeakerDetailRow.Session) {
            itemView.findViewById<TextView>(R.id.talkTitle).text = session.talkTitle
            itemView.findViewById<TextView>(R.id.talkSubtitle).text = session.talkSubtitle
            itemView.findViewById<ImageButton>(R.id.starSession).run {
                visibility = View.VISIBLE
                if (session.isStarred) {
                    setImageResource(R.drawable.ic_star_filled_24dp)
                    contentDescription = context.getString(R.string.unmark_as_favourite)
                } else {
                    setImageResource(R.drawable.ic_star_empty_24dp)
                    contentDescription = context.getString(R.string.mark_as_favourite)
                }
                setOnClickListener {
                    session.onStarClicked(
                        session,
                        session.isStarred
                    )
                }
            }
            itemView.setOnClickListener { session.onSessionClicked(session) }
        }
    }
}