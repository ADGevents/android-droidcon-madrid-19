package com.droidcon.commons.presentation

import android.content.Context

interface Navigator {
    fun toSpeakerDetail(context: Context, speakerId: String)
    fun toSessionDetail(context: Context, sessionId: String)
}