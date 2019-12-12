package com.droidcon.schedule.ui.schedulelist.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.schedulelist.model.SessionRow
import javax.inject.Inject

class SessionsAdapter @Inject constructor(
    sessionRowDiffItemCallback: SessionRowDiffItemCallback
) : ListAdapter<SessionRow, SessionRowViewHolder>(
    sessionRowDiffItemCallback
) {

    private object ViewType {
        const val DAY_DIVIDER = 0
        const val SESSION = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionRowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.DAY_DIVIDER -> DayDividerViewHolder(
                layoutInflater.inflate(R.layout.day_divider_row, parent, false)
            )
            ViewType.SESSION -> SessionViewHolder(
                layoutInflater.inflate(R.layout.session_row, parent, false)
            )
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: SessionRowViewHolder, position: Int) {
        when (holder) {
            is SessionViewHolder -> holder.bind(getItem(position) as SessionRow.Session)
            is DayDividerViewHolder -> holder.bind(getItem(position) as SessionRow.DayDivider)
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is SessionRow.DayDivider -> ViewType.DAY_DIVIDER
        is SessionRow.Session -> ViewType.SESSION
    }
}


