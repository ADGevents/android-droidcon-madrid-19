package com.droidcon.schedule.ui.schedulelist.logic

import com.droidcon.commons.date.GetNowDate
import com.droidcon.schedule.ui.schedulelist.model.InitialScheduleTab
import javax.inject.Inject

class GetInitialScheduleTab @Inject constructor(
    private val getScheduleTabs: GetScheduleTabs,
    private val getNowDate: GetNowDate
) {

    operator fun invoke(): InitialScheduleTab {
        val dateOfToday = getNowDate()
        val scheduleTabs = getScheduleTabs()

        val scheduleTabOfToday = scheduleTabs.firstOrNull { it.conferenceDayDate.isEqual(dateOfToday) }

        return if (scheduleTabOfToday == null) {
            InitialScheduleTab.None
        } else {
            InitialScheduleTab.Some(scheduleTabOfToday)
        }
    }
}
