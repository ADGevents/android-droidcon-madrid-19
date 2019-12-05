package com.droidcon.schedule.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.model.SessionState
import javax.inject.Inject

class SessionsAdapter @Inject constructor() :
    ListAdapter<SessionState, SessionViewHolder>(SessionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder =
        SessionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.session_row,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = getItem(position)
        holder.bind(session)
    }
}

class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(session: SessionState) {
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

object SessionDiffCallback : DiffUtil.ItemCallback<SessionState>() {
    override fun areItemsTheSame(oldItem: SessionState, newItem: SessionState): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SessionState, newItem: SessionState): Boolean =
        oldItem == newItem

}


