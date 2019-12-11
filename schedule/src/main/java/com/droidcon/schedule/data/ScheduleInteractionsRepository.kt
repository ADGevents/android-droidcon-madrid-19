package com.droidcon.schedule.data

import org.threeten.bp.LocalDate
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleInteractionsRepository @Inject constructor() {

    private val triedToScrollToInProgressSessionByDate = ConcurrentHashMap<LocalDate, Boolean>()

    var hasTriedToShowInitialScheduleTab: Boolean = false

    fun registerScrollToInProgressSessionTry(scheduleDayDate: LocalDate) {
        triedToScrollToInProgressSessionByDate[scheduleDayDate] = true
    }

    fun hasTriedToScrollToInProgressSession(scheduleDayDate: LocalDate): Boolean =
        triedToScrollToInProgressSessionByDate[scheduleDayDate] ?: false
}