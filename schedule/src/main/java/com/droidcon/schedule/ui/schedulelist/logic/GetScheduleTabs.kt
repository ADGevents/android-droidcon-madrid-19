package com.droidcon.schedule.ui.schedulelist.logic

import com.droidcon.commons.BuildConfig
import com.droidcon.schedule.ui.schedulelist.model.ScheduleTab
import org.threeten.bp.LocalDate

object GetScheduleTabs {

    operator fun invoke(): List<ScheduleTab> {
        val rawConferenceDaysDate = BuildConfig.CONFERENCE_DAYS_DATE
        return rawConferenceDaysDate.map { ScheduleTab(LocalDate.parse(it)) }
    }
}