package com.droidcon.schedule.ui.logic

import com.droidcon.commons.date.GetDateOfToday
import com.droidcon.schedule.ui.model.InitialScheduleTab
import javax.inject.Inject

class GetInitialScheduleTab @Inject constructor(
    private val getScheduleTabs: GetScheduleTabs,
    private val getDateOfToday: GetDateOfToday
) {

    operator fun invoke(): InitialScheduleTab {
        val dateOfToday = getDateOfToday()
        val scheduleTabs = getScheduleTabs()

        val scheduleTabOfToday = scheduleTabs.firstOrNull { it.conferenceDayDate.isEqual(dateOfToday) }

        return if (scheduleTabOfToday == null) {
            InitialScheduleTab.None
        } else {
            InitialScheduleTab.Some(scheduleTabOfToday)
        }
    }
}
