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
import com.droidcon.schedule.ui.model.SessionModel

class SessionsAdapter : ListAdapter<SessionModel, SessionViewHolder>(SessionDiffCallback) {

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

    fun bind(session: SessionModel) {
        itemView.findViewById<TextView>(R.id.sessionTitle).text = session.title
        itemView.findViewById<TextView>(R.id.sessionAdditionalInfo).text = session.additionalInfo
        itemView.findViewById<TextView>(R.id.sessionTime).text = session.time
        itemView.findViewById<ImageButton>(R.id.starSession).run {
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
    }
}

object SessionDiffCallback : DiffUtil.ItemCallback<SessionModel>() {
    override fun areItemsTheSame(oldItem: SessionModel, newItem: SessionModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SessionModel, newItem: SessionModel): Boolean =
        oldItem == newItem

}


