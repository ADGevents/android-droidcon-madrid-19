package com.droidcon.schedule.ui.schedulelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.schedule.domain.RegisterShowInitialScheduleTabTry
import com.droidcon.schedule.domain.ShouldTrySwitchingToInitialScheduleTab
import com.droidcon.schedule.ui.schedulelist.logic.GetInitialScheduleTab
import com.droidcon.schedule.ui.schedulelist.model.InitialScheduleTab
import com.droidcon.schedule.ui.schedulelist.model.ScheduleEffect
import com.droidcon.schedule.ui.schedulelist.model.ScheduleTab

class ScheduleViewModel(
    private val getInitialScheduleTab: GetInitialScheduleTab,
    private val shouldTrySwitchingToInitialScheduleTab: ShouldTrySwitchingToInitialScheduleTab,
    private val registerShowInitialScheduleTabTry: RegisterShowInitialScheduleTabTry
) : ViewModel() {

    private val mutableScheduleEffects = SingleLiveEvent<ScheduleEffect>()
    val scheduleEffects: LiveData<ScheduleEffect> = mutableScheduleEffects

    fun onScheduleVisible() {
        if (!shouldTrySwitchingToInitialScheduleTab()) {
            return
        }

        registerShowInitialScheduleTabTry()

        when (val initialScheduleTab = getInitialScheduleTab()) {
            InitialScheduleTab.None -> Unit
            is InitialScheduleTab.Some -> emitSwitchToTabEffect(initialScheduleTab.scheduleTab)
        }
    }

    fun onSearchClicked() {
        mutableScheduleEffects.setValue(ScheduleEffect.NavigateToSearchSessions)
    }

    private fun emitSwitchToTabEffect(tab: ScheduleTab) {
        mutableScheduleEffects.setValue(ScheduleEffect.SwitchToTab(tab))
    }
}