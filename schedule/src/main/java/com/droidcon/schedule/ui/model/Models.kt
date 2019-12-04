package com.droidcon.schedule.ui.model

data class SessionModel(
    val id: String,
    val title: String,
    val additionalInfo: String,
    val time: String,
    val starred: Boolean,
    val onStarClicked: (String, Boolean) -> Unit,
    val onSessionClicked: (String) -> Unit
)

sealed class ScheduleEffect {
    object ShowUpdateStarredStateError : ScheduleEffect()
}