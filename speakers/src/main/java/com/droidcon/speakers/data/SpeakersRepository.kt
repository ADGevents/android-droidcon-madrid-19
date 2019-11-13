package com.droidcon.speakers.data

import javax.inject.Inject

class SpeakersRepository @Inject constructor() {

    fun getAll(): List<SpeakerData> = listOf(
        SpeakerData(
            id = "00000000-0000-0000-0000-000000000004",
            name = NameData(
                firstName = "Jorge",
                lastName = "Mucientes",
                fullName = "Jorge Mucientes"
            ),
            bio = "Pop culture fanatic. Friend of animals everywhere. Student. Thinker. Bacon practitioner.",
            tagLine = "Professional public speaker",
            profilePicture = "https://sessionize.com/image?f=e82a4a80c16abc546f05e05a8ef591ef,400,400,True,False,test4.jpg".asUrl(),
            links = emptyList()
        ),
        SpeakerData(
            id = "00000000-0000-0000-0000-000000000005",
            name = NameData(
                firstName = "Juan",
                lastName = "Patarino",
                fullName = "Juan Patarino"
            ),
            bio = "Student. Wannabe creator. Incurable music advocate.",
            tagLine = "PR specialist",
            profilePicture = "https://sessionize.com/image?f=4e1b91f4d9523cabcbce0759bb16d61a,400,400,True,False,test8.jpg".asUrl(),
            links = emptyList()
        )
    )
}