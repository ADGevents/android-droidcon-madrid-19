package com.droidcon.schedule.ui.sessiondetail

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

object SessionDetailRowDiffItemCallback : DiffUtil.ItemCallback<SessionDetailRow>() {

    override fun areItemsTheSame(oldItem: SessionDetailRow, newItem: SessionDetailRow): Boolean = when {
        oldItem is SessionDetailRow.Speaker && newItem is SessionDetailRow.Speaker -> oldItem.id == newItem.id
        else -> false
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SessionDetailRow, newItem: SessionDetailRow): Boolean = when {
        oldItem is SessionDetailRow.Speaker && newItem is SessionDetailRow.Speaker -> oldItem == newItem
        else -> false
    }
}