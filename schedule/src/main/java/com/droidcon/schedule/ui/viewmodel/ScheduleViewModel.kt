package com.droidcon.schedule.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.schedule.ui.logic.GetInitialScheduleTab
import com.droidcon.schedule.ui.model.InitialScheduleTab
import com.droidcon.schedule.ui.model.ScheduleEffect
import com.droidcon.schedule.ui.model.ScheduleTab

class ScheduleViewModel(
    private val getInitialScheduleTab: GetInitialScheduleTab
) : ViewModel() {

    private val mutableScheduleEffects = SingleLiveEvent<ScheduleEffect>()
    val scheduleEffects: LiveData<ScheduleEffect> = mutableScheduleEffects

    fun onScheduleVisible() {
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