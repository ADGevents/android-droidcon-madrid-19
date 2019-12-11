package com.droidcon.schedule.ui

import androidx.recyclerview.widget.DiffUtil
import com.droidcon.schedule.ui.model.SessionRow

class SessionRowDiffItemCallback(
    private val sessionDiffItemCallback: SessionItemDiffCallback,
    private val dayDividerItemDiffCallback: DayDividerItemDiffCallback
) : DiffUtil.ItemCallback<SessionRow>() {

    override fun areItemsTheSame(oldItem: SessionRow, newItem: SessionRow): Boolean =
        when {
            oldItem is SessionRow.Session && newItem is SessionRow.Session ->
                sessionDiffItemCallback.areItemsTheSame(oldItem, newItem)
            oldItem is SessionRow.DayDivider && newItem is SessionRow.DayDivider ->
                dayDividerItemDiffCallback.areItemsTheSame(oldItem, newItem)
            else -> false
        }

    override fun areContentsTheSame(oldItem: SessionRow, newItem: SessionRow): Boolean =
        when {
            oldItem is SessionRow.Session && newItem is SessionRow.Session ->
                sessionDiffItemCallback.areContentsTheSame(oldItem, newItem)
            oldItem is SessionRow.DayDivider && newItem is SessionRow.DayDivider ->
                dayDividerItemDiffCallback.areContentsTheSame(oldItem, newItem)
            else -> false
        }
}

object SessionItemDiffCallback : DiffUtil.ItemCallback<SessionRow.Session>() {
    override fun areItemsTheSame(oldItem: SessionRow.Session, newItem: SessionRow.Session): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SessionRow.Session, newItem: SessionRow.Session): Boolean =
        oldItem == newItem
}

object DayDividerItemDiffCallback : DiffUtil.ItemCallback<SessionRow.DayDivider>() {
    /* As we can only have a day divider per unique day, if two instances are equal
    * structurally, we can assume they are the same item.*/
    override fun areItemsTheSame(oldItem: SessionRow.DayDivider, newItem: SessionRow.DayDivider): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: SessionRow.DayDivider, newItem: SessionRow.DayDivider): Boolean =
        oldItem == newItem

}