package com.droidcon.schedule.ui.schedulelist.recyclerview

import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.schedule.ui.schedulelist.model.SessionRow


sealed class SessionRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class SessionViewHolder(itemView: View) : SessionRowViewHolder(itemView) {

    fun bind(session: SessionRow.Session) {
        itemView.findViewById<TextView>(com.droidcon.schedule.R.id.sessionTitle).text =
            session.title
        itemView.findViewById<TextView>(com.droidcon.schedule.R.id.sessionAdditionalInfo).text =
            session.additionalInfo
        itemView.findViewById<TextView>(com.droidcon.schedule.R.id.sessionTime).text = session.time
        itemView.findViewById<TextView>(com.droidcon.schedule.R.id.timePeriod).text =
            session.timePeriod

        if (session.hasSessionDetail) {
            setClickableItem(session)
        } else {
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    com.droidcon.schedule.R.color.service_session_row_background
                )
            )
            itemView.setOnClickListener(null)
        }

        val starSessionButton =
            itemView.findViewById<ImageButton>(com.droidcon.schedule.R.id.starSession)

        if (session.favouritesEnabled) {
            starSessionButton.run {
                visibility = View.VISIBLE
                contentDescription = if (session.starred) {
                    setImageResource(com.droidcon.schedule.R.drawable.ic_star_filled_24dp)
                    context.getString(com.droidcon.schedule.R.string.unmark_as_favourite)
                } else {
                    setImageResource(com.droidcon.schedule.R.drawable.ic_star_empty_24dp)
                    context.getString(com.droidcon.schedule.R.string.mark_as_favourite)
                }
                setOnClickListener {
                    session.onStarClicked(session, session.starred)
                }
            }
        } else {
            starSessionButton.visibility = View.GONE
        }
    }

    private fun setClickableItem(session: SessionRow.Session) {
        val outValue = TypedValue()
        itemView.context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            outValue,
            true
        )
        itemView.setBackgroundResource(outValue.resourceId)
        itemView.setOnClickListener {
            session.onSessionClicked(session)
        }
    }
}

class DayDividerViewHolder(itemView: View) : SessionRowViewHolder(itemView) {

    fun bind(dayDivider: SessionRow.DayDivider) {
        itemView.findViewById<TextView>(com.droidcon.schedule.R.id.title).text = dayDivider.title
    }
}