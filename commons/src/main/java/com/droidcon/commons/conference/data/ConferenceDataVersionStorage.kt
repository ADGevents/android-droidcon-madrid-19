package com.droidcon.commons.conference.data

import android.content.SharedPreferences
import javax.inject.Inject

class ConferenceDataVersionStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getVersion(): Long =
        sharedPreferences.getLong(CONFERENCE_DATA_VERSION_KEY, 0)

    fun putVersion(version: Long) {
        sharedPreferences.edit().putLong(CONFERENCE_DATA_VERSION_KEY, version).apply()
    }

    private companion object {
        const val CONFERENCE_DATA_VERSION_KEY = "conference_data_version_key"
    }
}