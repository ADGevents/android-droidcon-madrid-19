package com.droidcon.commons.sessionize.storage

import com.droidcon.commons.sessionize.storage.database.LinkEntity
import com.droidcon.commons.sessionize.storage.database.SpeakerDao
import com.droidcon.commons.sessionize.storage.database.SpeakerEntity
import com.droidcon.commons.sessionize.storage.database.toDO
import javax.inject.Inject

class SpeakersStorage @Inject constructor(
    private val speakerDao: SpeakerDao
) {

    suspend fun getAll(): List<SpeakerDO> {
        val speakers = speakerDao.getSpeakers()
        val linksBySpeakerId = getLinksBySpeakerId(speakers)

        return speakers.map { it.toDO(linksBySpeakerId[it.id] ?: emptyList()) }
    }

    suspend fun add(speakers: List<SpeakerDO>) {
        speakerDao.insertSpeakers(speakers.map { it.toEntity() })
        speakerDao.insertLinks(speakers.flatMap { it.toLinkEntities() })
        speakerDao.insertSpeakersForSearch(speakers.map { it.toFTSEntity() })
    }

    suspend fun get(id: String): SpeakerDO? {
        val speaker = speakerDao.getSpeaker(id)
        val links = getLinks(id)

        return speaker?.toDO(links)
    }

    suspend fun search(query: String): List<SpeakerDO> {
        val searchResults = speakerDao.searchSpeakers(query)
        return searchResults.mapNotNull { speakerId -> get(speakerId) }
    }

    private suspend fun getLinksBySpeakerId(speakers: List<SpeakerEntity>): Map<String, List<LinkEntity>> {
        val linksBySpeakerId = mutableMapOf<String, List<LinkEntity>>()

        speakers.forEach { speaker ->
            val speakerId = speaker.id
            val links = getLinks(speakerId)
            linksBySpeakerId[speakerId] = links
        }

        return linksBySpeakerId
    }

    private suspend fun getLinks(speakerId: String): List<LinkEntity> =
        speakerDao.getLinks(speakerId)
}