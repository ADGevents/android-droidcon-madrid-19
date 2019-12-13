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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TalkDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TalkDetailFragment : Fragment() {

    private lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val session = arguments?.getSerializable(ARG_SESSION_DETAILS) as? Session
            ?: error("Missing session data arguments!!")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_session_detail, container, false)
    }

    companion object {
        private const val ARG_SESSION_DETAILS = "arg.SESSION_DETAILS"

        fun newInstance(scheduleTab: ScheduleTab): ScheduleDayFragment {
            val args = Bundle().apply {
                putSerializable(ARG_SESSION_DETAILS, scheduleTab)
            }
            return ScheduleDayFragment().apply { arguments = args }
        }
    }
}
