package com.droidcon.commons.sessionize.api

import arrow.core.orNull
import com.droidcon.commons.testutils.leftOrNull
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SpeakersApiClientIT {

    private val mockWebServer = MockWebServer()

    private val okHttpClient = OkHttpClient.Builder().build()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var sut: SpeakersApiClient

    @Before
    fun setUp() {
        mockWebServer.start()
        val mockWebServerUrl = mockWebServer.url(EMPTY_PATH).toString()
        sut = SpeakersApiClient(
            okHttpClient = okHttpClient,
            moshi = moshi,
            coroutineDispatcher = testCoroutineDispatcher,
            apiConfig = ApiConfig(baseUrl = mockWebServerUrl)
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getSpeakers happy path`() = testCoroutineDispatcher.runBlockingTest {
        givenAValidGetSpeakersResponse()

        val actualSpeakers = sut.getSpeakers().orNull()

        val expectedSpeakers = expectedSpeakersForSuccess()
        assertNotNull(actualSpeakers)
        assertEquals(expectedSpeakers, actualSpeakers)
    }

    @Test
    fun `getSpeakers network error`() = testCoroutineDispatcher.runBlockingTest {
        givenNoResponse()

        val actualGetSpeakersError = sut.getSpeakers().leftOrNull()

        assertNotNull(actualGetSpeakersError)
        assertEquals(GetSpeakersError.Generic, actualGetSpeakersError)
    }

    @Test(expected = JsonDataException::class)
    fun `getSpeakers invalid response`() = testCoroutineDispatcher.runBlockingTest {
        givenAnInvalidGetSpeakersResponse()

        sut.getSpeakers()
    }

    private fun givenAValidGetSpeakersResponse() {
        val getSpeakersResponse = SpeakersApiClientIT::class.java
            .getResource("/GetSpeakersValidResponse.json")
            ?.readText()!!

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getSpeakersResponse)
        )
    }

    private fun givenAnInvalidGetSpeakersResponse() {
        val getSpeakersInvalidResponse = SpeakersApiClientIT::class.java
            .getResource("/GetSpeakersInvalidResponse.json")
            ?.readText()!!

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getSpeakersInvalidResponse)
        )
    }

    private fun givenNoResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(401))
    }

    private fun expectedSpeakersForSuccess(): List<SpeakerDto> = listOf(
        SpeakerDto(
            id = "00000000-0000-0000-0000-000000000004",
            firstName = "Aiden",
            lastName = "Test",
            fullName = "Aiden Test",
            bio = "Pop culture fanatic. Friend of animals everywhere. Student. Thinker. Bacon practitioner.",
            tagLine = "Professional public speaker",
            profilePicture = "https://sessionize.com/image?f=e82a4a80c16abc546f05e05a8ef591ef,400,400,True,False,test4.jpg",
            links = listOf(
                LinkDto(
                    title = "Twitter",
                    url = "https://twitter.com/sessionizecom",
                    linkType = "Twitter"
                ),
                LinkDto(
                    title = "LinkedIn",
                    url = "http://linkedin.com/in/domagojpa",
                    linkType = "LinkedIn"
                )
            )
        ),
        SpeakerDto(
            id = "00000000-0000-0000-0000-000000000008",
            firstName = "Ava",
            lastName = "Test",
            fullName = "Ava Test",
            bio = "Student. Wannabe creator. Incurable music advocate.",
            tagLine = "PR specialist",
            profilePicture = "https://sessionize.com/image?f=4e1b91f4d9523cabcbce0759bb16d61a,400,400,True,False,test8.jpg",
            links = emptyList()
        )
    )

    private companion object {
        const val EMPTY_PATH = ""
    }
}