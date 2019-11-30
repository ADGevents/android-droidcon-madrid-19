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
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
        itemView.findViewById<TextView>(R.id.sessionAdditionalInfo).text =
            "${TimeUnit.MILLISECONDS.toMinutes(session.durationInMillis)} min / Room 3"
        itemView.findViewById<TextView>(R.id.sessionTime).text = session.sessionStartTimeStamp.toFormattedTime()
    }
}

object SessionDiffCallback: DiffUtil.ItemCallback<Session>() {
    override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean =
        oldItem == newItem
}

fun Long.toFormattedTime(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(date)
}

