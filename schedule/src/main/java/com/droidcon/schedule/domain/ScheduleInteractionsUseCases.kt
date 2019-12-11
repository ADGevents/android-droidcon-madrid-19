package com.droidcon.schedule.domain

import com.droidcon.schedule.data.ScheduleInteractionsRepository
import org.threeten.bp.LocalDate
import javax.inject.Inject

class ShouldTrySwitchingToInitialScheduleTab @Inject constructor(
    private val scheduleInteractionsRepository: ScheduleInteractionsRepository
) {

    operator fun invoke(): Boolean = !scheduleInteractionsRepository.hasTriedToShowInitialScheduleTab
}

class RegisterShowInitialScheduleTabTry @Inject constructor(
    private val scheduleInteractionsRepository: ScheduleInteractionsRepository
) {

    operator fun invoke() {
        scheduleInteractionsRepository.hasTriedToShowInitialScheduleTab = true
    }
}

class ShouldTryScrollingToInProgressSession @Inject constructor(
    private val scheduleInteractionsRepository: ScheduleInteractionsRepository
) {

    operator fun invoke(scheduleDate: LocalDate): Boolean =
        !scheduleInteractionsRepository.hasTriedToScrollToInProgressSession(scheduleDate)
}

class RegisterScrollToInProgressSessionTry @Inject constructor(
    private val scheduleInteractionsRepository: ScheduleInteractionsRepository
) {

    operator fun invoke(scheduleDate: LocalDate) {
        scheduleInteractionsRepository.registerScrollToInProgressSessionTry(scheduleDate)
    }
}