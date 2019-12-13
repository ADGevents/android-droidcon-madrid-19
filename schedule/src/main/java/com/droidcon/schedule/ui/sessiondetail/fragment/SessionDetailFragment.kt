package com.droidcon.schedule.ui.sessiondetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.droidcon.schedule.R
import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.schedulelist.fragment.ScheduleDayFragment
import com.droidcon.schedule.ui.schedulelist.model.ScheduleTab
import dagger.android.support.DaggerFragment

class SessionDetailFragment : DaggerFragment() {

    private lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val session = arguments?.getSerializable(ARG_SESSION_DETAILS) as? Session
//            ?: error("Missing session data details!!")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_session_detail, container, false)
    }
}
