package com.droidcon.commons.conference.data.repository.sessionize

import com.droidcon.commons.conference.data.api.session.SessionGroupDto
import com.droidcon.commons.conference.data.api.session.SessionsApiClient
import com.droidcon.commons.conference.data.api.session.toSessionData
import com.droidcon.commons.conference.data.api.speaker.SpeakerDto
import com.droidcon.commons.conference.data.api.speaker.SpeakersApiClient
import com.droidcon.commons.conference.data.repository.speaker.toDO
import com.droidcon.commons.conference.data.storage.database.SessionizeDao
import com.droidcon.commons.conference.data.storage.database.favourites.FavouritesDao
import com.droidcon.commons.conference.data.storage.database.session.toSessionEntity
import com.droidcon.commons.conference.data.storage.database.sessionandspeaker.SessionAndSpeakerEntity
import com.droidcon.commons.conference.data.storage.toEntity
import com.droidcon.commons.conference.data.storage.toLinkEntities
import com.droidcon.commons.ioc.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionizeRepository @Inject constructor(
    private val sessionsApiClient: SessionsApiClient,
    private val speakersApiClient: SpeakersApiClient,
    private val favouritesDao: FavouritesDao,
    private val sessionizeDao: SessionizeDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun refreshConferenceData(): Boolean = withContext(ioDispatcher) {
        val sessionsGroups = sessionsApiClient.getSessionGroups()

        if (sessionsGroups.isEmpty()) return@withContext false

        return@withContext speakersApiClient.getSpeakers().fold(
            ifLeft = { false },
            ifRight = { speakers ->
                onConferenceData(sessionsGroups, speakers)
                return@fold true
            }
        )
    }

    private suspend fun onConferenceData(
        groupSessions: List<SessionGroupDto>,
        speakers: List<SpeakerDto>
    ) {
        val sessions = groupSessions.flatMap { sessionGroup ->
            sessionGroup.sessions.map { session ->
                val favourite = favouritesDao.getBySessionId(session.id)
                val isStarred = favourite != null
                session.toSessionData(isStarred)
            }
        }

        val speakerDOs = speakers.map { it.toDO() }

        val sessionsAndSpeakersEntities = sessions.flatMap { session ->
            session.speakers.map { speakerId ->
                SessionAndSpeakerEntity(
                    sessionId = session.id,
                    speakerId = speakerId
                )
            }
        }
        val sessionEntities = sessions.map { it.toSessionEntity() }
        val speakerEntities = speakerDOs.map { it.toEntity() }
        val linkEntities = speakerDOs.flatMap { it.toLinkEntities() }

        sessionizeDao.refreshData(
            sessionEntities,
            speakerEntities,
            linkEntities,
            sessionsAndSpeakersEntities
        )
    }
}