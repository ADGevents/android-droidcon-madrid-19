package com.droidcon.schedule.ui

import androidx.recyclerview.widget.DiffUtil
import com.droidcon.schedule.domain.Session

class SessionDiffCallback: DiffUtil.ItemCallback<Session>() {
    override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean =
        oldItem == newItem
}