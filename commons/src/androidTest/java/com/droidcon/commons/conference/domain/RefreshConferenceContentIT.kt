package com.droidcon.commons.conference.domain

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.droidcon.commons.conference.data.api.session.SessionDto
import com.droidcon.commons.conference.data.api.session.SessionGroupDto
import com.droidcon.commons.conference.data.api.session.SessionsApiClient
import com.droidcon.commons.conference.data.api.speaker.SpeakersApiClient
import com.droidcon.commons.conference.data.repository.session.SessionData
import com.droidcon.commons.conference.data.repository.session.SessionsRepository
import com.droidcon.commons.conference.data.repository.speaker.SpeakersRepository
import com.droidcon.commons.conference.data.storage.SessionsStorage
import com.droidcon.commons.conference.data.storage.SpeakersStorage
import com.droidcon.commons.conference.data.storage.database.SessionizeDatabase
import com.droidcon.commons.conference.data.storage.database.favourites.FavouritesEntity
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RefreshConferenceContentIT {

    private val sessionsApiClientMock = mockk<SessionsApiClient>()
    private val speakersApiClientMock = mockk<SpeakersApiClient>(relaxed = true)
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val sessionizeDatabase = Room.inMemoryDatabaseBuilder(context, SessionizeDatabase::class.java).build()
    private val sessionsStorage = SessionsStorage(
        sessionDao = sessionizeDatabase.sessionDao(),
        favouritesDao = sessionizeDatabase.favouritesDao()
    )
    private val speakersStorage = SpeakersStorage(
        speakerDao = sessionizeDatabase.speakerDao()
    )
    private val sessionsRepository = SessionsRepository(
        sessionsApiClient = sessionsApiClientMock,
        sessionsStorage = sessionsStorage,
        favouritesDao = sessionizeDatabase.favouritesDao()
    )
    private val speakersRepository = SpeakersRepository(
        speakersApiClient = speakersApiClientMock,
        speakersStorage = speakersStorage
    )

    private val sut = dev.androidcookers.droidcon.RefreshConferenceContent(
        sessionsRepository = sessionsRepository,
        speakersRepository = speakersRepository,
        ioDispatcher = TestCoroutineDispatcher()
    )

    @Test
    fun sessionsAreRefreshed() = runBlocking {
        sessionsStorage.storeSessions(anySessions())
        sessionizeDatabase.favouritesDao().insert(FavouritesEntity("id"))
        every { sessionsApiClientMock.getSessionGroups() } returns anySessionGroups()

        sut.invoke()

        val refreshedSessions = sessionsStorage.getAllSessionsData()
        val expectedSessions = listOf(
            SessionData("id", "updatedTitle", "updatedDescription", "startsAt", "endsAt", true, false, emptyList(), 222, "roomName", true),
            SessionData("idB", "anyTitle", "updatedDescription", "startsAt", "endsAt", true, false, emptyList(), 222, "roomName", false)
        )
        assertEquals(expectedSessions, refreshedSessions)
    }

    private fun anySessions(): List<SessionData> = listOf(
        SessionData("id", "title", "description", "startsAt", "endsAt", true, false, emptyList(), 222, "roomName", true)
    )

    private fun anySessionGroups(): List<SessionGroupDto> = listOf(
        SessionGroupDto(
            "groupId",
            "groupName",
            listOf(
                SessionDto("id", "updatedTitle", "updatedDescription", "startsAt", "endsAt", true, false, emptyList(), 222, "roomName"),
                SessionDto("idB", "anyTitle", "updatedDescription", "startsAt", "endsAt", true, false, emptyList(), 222, "roomName")
            )
        )
    )
}