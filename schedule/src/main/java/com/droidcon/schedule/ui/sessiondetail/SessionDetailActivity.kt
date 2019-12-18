package com.droidcon.schedule.ui.sessiondetail

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.commons.presentation.Navigator
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.sessiondetail.model.SessionDetail
import com.droidcon.schedule.ui.sessiondetail.model.SessionDetailEffect
import com.droidcon.schedule.ui.sessiondetail.model.getSessionDetailRows
import com.droidcon.schedule.ui.sessiondetail.viewmodel.SessionDetailViewModel
import com.droidcon.schedule.ui.sessiondetail.viewmodel.SessionDetailViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class SessionDetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var sessionDetailViewModelFactory: SessionDetailViewModelFactory
    private lateinit var sessionDetailViewModel: SessionDetailViewModel

    @Inject
    lateinit var sessionDetailAdapter: SessionDetailAdapter

    private lateinit var sessionTitle: TextView
    private lateinit var sessionFavorite: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_detail)

        val sessionId = intent.extras?.getString("sessionId")
            ?: error("Cannot open session detail without session id")

        setUpViews()
        setUpViewModel(sessionId)
    }


    private fun setUpViews() {
        sessionTitle = findViewById(R.id.sessionTitle)
        sessionFavorite = findViewById(R.id.sessionDetailStarButton)
        findViewById<RecyclerView>(R.id.sessionDetailRows).run {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionDetailAdapter
            itemAnimator = null
        }
        findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            onBackPressed()
        }

        setCollapsingToolbarForSession()
    }

    private fun setCollapsingToolbarForSession() {
        val collapsingToolbar =
            findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbarLayout)
        val appBarLayout = findViewById<AppBarLayout>(R.id.appBarLayout)
        appBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = sessionDetailViewModel.getSessionTitle()
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = ""
                    isShow = false
                }
            }
        })
    }

    private fun setUpViewModel(sessionId: String) {
        sessionDetailViewModel = sessionDetailViewModelFactory.get(this)
        sessionDetailViewModel.sessionDetailEffects.observe(::getLifecycle, ::onSessionDetailEffect)
        sessionDetailViewModel.sessionDetailState.observe(::getLifecycle, ::onSessionDetailState)
        sessionDetailViewModel.onSessionDetailVisible(sessionId)
    }

    private fun onSessionDetailState(sessionDetail: SessionDetail) {
        sessionTitle.text = sessionDetail.title
        sessionDetailAdapter.submitList(sessionDetail.getSessionDetailRows())
        setUpSessionStarredUI(sessionDetail)
    }

    private fun setUpSessionStarredUI(sessionDetail: SessionDetail) {
        if (sessionDetail.starred) {
            sessionFavorite.setImageResource(R.drawable.ic_star_filled_24dp)
        } else {
            sessionFavorite.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        sessionFavorite.setOnClickListener {
            sessionDetail.onStarClicked(sessionDetail, sessionDetail.starred)
        }
    }

    private fun onSessionDetailEffect(sessionDetailEffect: SessionDetailEffect) {
        when (sessionDetailEffect) {
            is SessionDetailEffect.NavigateToSpeakerDetail -> navigateToSpeakerDetail(
                sessionDetailEffect.speakerId
            )
        }
    }

    private fun navigateToSpeakerDetail(speakerId: String) {
        navigator.toSpeakerDetail(this, speakerId)
    }
}