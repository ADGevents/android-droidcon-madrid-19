package com.droidcon.schedule.ui.sessiondetail.model

import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.domain.SessionSpeaker
import com.droidcon.schedule.ui.schedulelist.model.getTimePeriod
import com.droidcon.schedule.ui.schedulelist.model.toFormattedTime
import java.util.concurrent.TimeUnit


fun Session.toSessionDetail(
    sessionSpeakers: List<SessionSpeaker>,
    onSpeakerSelected: (String) -> Unit = {},
    onStarClicked: (String, Boolean) -> Unit
): SessionDetail =
    SessionDetail(
        id = id,
        title = title,
        description = description,
        time = sessionStartTimeStamp.toFormattedTime(),
        timePeriod = sessionStartTimeStamp.getTimePeriod(),
        duration = "${TimeUnit.MILLISECONDS.toMinutes(durationInMillis)} min",
        isServiceSession = isServiceSession,
        speakers = sessionSpeakers.map { it.toSessionSpeakerRow(onSpeakerSelected) },
        roomName = roomName,
        starred = starred,
        onStarClicked = onStarClicked
    )

fun SessionSpeaker.toSessionSpeakerRow(onSpeakerSelected: (String) -> Unit = {}): SessionSpeakerRow =
    SessionSpeakerRow(
        id,
        fullName,
        tagLine,
        profilePicture.rawUrl,
        links,
        onSpeakerSelected = onSpeakerSelected
    )
