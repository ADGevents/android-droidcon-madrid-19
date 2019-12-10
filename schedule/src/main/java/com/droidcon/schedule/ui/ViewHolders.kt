package com.droidcon.schedule.ui

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.model.SessionRow

sealed class SessionRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class SessionViewHolder(itemView: View) : SessionRowViewHolder(itemView) {

    fun bind(session: SessionRow.Session) {
        itemView.findViewById<TextView>(R.id.sessionTitle).text = session.title
        itemView.findViewById<TextView>(R.id.sessionAdditionalInfo).text = session.additionalInfo
        itemView.findViewById<TextView>(R.id.sessionTime).text = session.time
        itemView.findViewById<TextView>(R.id.timePeriod).text = session.timePeriod

        val starSessionButton = itemView.findViewById<ImageButton>(R.id.starSession)

        if (session.favouritesEnabled) {
            starSessionButton.run {
                visibility = View.VISIBLE
                if (session.starred) {
                    setImageResource(R.drawable.ic_star_filled_24dp)
                    contentDescription = context.getString(R.string.unmark_as_favourite)
                } else {
                    setImageResource(R.drawable.ic_star_empty_24dp)
                    contentDescription = context.getString(R.string.mark_as_favourite)
                }
                setOnClickListener {
                    session.onStarClicked(session.id, session.starred)
                }
            }
        } else {
            starSessionButton.visibility = View.GONE
        }
    }
}

class DayDividerViewHolder(itemView: View) : SessionRowViewHolder(itemView) {

    fun bind(dayDivider: SessionRow.DayDivider) {
        itemView.findViewById<TextView>(R.id.title).text = dayDivider.title
    }
}