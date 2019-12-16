package dev.androidcookers.droidcon

import android.content.Context
import android.content.Intent
import com.droidcon.commons.presentation.Navigator
import com.droidcon.schedule.ui.sessiondetail.SessionDetailActivity
import com.droidcon.speakers.presentation.speakerdetail.SpeakerDetailActivity
import javax.inject.Inject

class AppNavigator @Inject constructor() : Navigator {

    override fun toSpeakerDetail(context: Context, speakerId: String) {
        Intent(context, SpeakerDetailActivity::class.java).apply {
            putExtra("speakerId", speakerId)
            context.startActivity(this)
        }
    }

    override fun toSessionDetail(context: Context, sessionId: String) {
        Intent(context, SessionDetailActivity::class.java).apply {
            putExtra("sessionId", sessionId)
            context.startActivity(this)
        }
    }
}