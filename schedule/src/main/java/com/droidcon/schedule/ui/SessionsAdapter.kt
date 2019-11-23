package com.droidcon.schedule.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.schedule.R
import com.droidcon.schedule.domain.Session

class SessionsAdapter: ListAdapter<Session, SessionViewHolder>(SessionDiffCallback) {

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

    fun bind(session: Session) {
        itemView.findViewById<TextView>(R.id.sessionTitle).text = session.title
        itemView.findViewById<TextView>(R.id.sessionAdditionalInfo).text = "Main stage / 30min"
        itemView.findViewById<TextView>(R.id.sessionTime).text = "12:05"
        itemView.findViewById<TextView>(R.id.timePeriod).text = "PM"
        itemView.findViewById<TextView>(R.id.sessionCategory).text = "droidcon"
    }
}

object SessionDiffCallback: DiffUtil.ItemCallback<Session>() {
    override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean =
        oldItem == newItem
}

