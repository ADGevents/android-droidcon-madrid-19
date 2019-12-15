package com.droidcon.info.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.droidcon.commons.BuildConfig
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.info.R
import com.droidcon.info.presentation.InfoEffect
import com.droidcon.info.presentation.InfoOption
import com.droidcon.info.presentation.InfoState

class InfoFragmentViewModel : ViewModel() {

    private object InfoOptionId {
        const val CODE_OF_CONDUCT = 1L
        const val CONTACTS = 2L
        const val WEBSITE = 3L
    }

    private val options by lazy {
        listOf(
            InfoOption.Map(
                "https://maps.googleapis.com/maps/api/staticmap?center=Nuevos+Ministerios+Madrid&zoom=13&scale=1&size=600x300&maptype=roadmap&key=${BuildConfig.STATIC_MAPS_API_KEY}&format=png&visual_refresh=true&markers=size:mid%7Ccolor:0xff0000%7Clabel:%7CUCM+Facultad+de+Inform%C3%A1tica",
                ::onMapTapped
            ),
            InfoOption.Row(
                InfoOptionId.CODE_OF_CONDUCT,
                R.string.code_of_conduct,
                R.drawable.ic_code_of_conduct) { onInfoOptionTapped(InfoOptionId.CODE_OF_CONDUCT) },
            InfoOption.Row(
                InfoOptionId.CONTACTS,
                R.string.contacts,
                R.drawable.ic_contacts
            ) { onInfoOptionTapped(InfoOptionId.CONTACTS) },
            InfoOption.Row(
                InfoOptionId.WEBSITE,
                R.string.website,
                R.drawable.ic_web
            ) { onInfoOptionTapped(InfoOptionId.WEBSITE) }
        )
    }

    private val mutableInfoState = MutableLiveData<InfoState>()
    val infoState: LiveData<InfoState> = mutableInfoState

    private val mutableInfoEffects = SingleLiveEvent<InfoEffect>()
    val infoEffects: LiveData<InfoEffect> = mutableInfoEffects

    fun onInfoVisible() {
        mutableInfoState.value = InfoState(options)
    }

    private fun onInfoOptionTapped(id: Long) {
        when (id) {
            InfoOptionId.CODE_OF_CONDUCT -> emitNavigateToCodeOfConductEffect()
            InfoOptionId.CONTACTS -> emitNavigateToContactsOfInterestEffect()
            InfoOptionId.WEBSITE -> emitNavigateToWebsiteEffect()
            else -> Log.w("INFO_OPTION", "Invalid option: $id")
        }
    }

    private fun onMapTapped() {
        emitNavigateToMapEffect()
    }

    private fun emitNavigateToMapEffect() {
        val effect = InfoEffect.NavigateToMaps(Uri.parse("geo:0,0?q=Calle+del+Prof.+José+García+Santesmases,+9,+28040+Madrid"))
        mutableInfoEffects.setValue(effect)
    }

    private fun emitNavigateToCodeOfConductEffect() {
        val effect = InfoEffect.NavigateToUri(Uri.parse("https://www.madrid.droidcon.com/coc"))
        mutableInfoEffects.setValue(effect)
    }

    private fun emitNavigateToContactsOfInterestEffect() {
        mutableInfoEffects.setValue(InfoEffect.NavigateToContactsOfInterest)
    }

    private fun emitNavigateToWebsiteEffect() {
        val effect = InfoEffect.NavigateToUri(Uri.parse("https://www.madrid.droidcon.com"))
        mutableInfoEffects.setValue(effect)
    }
}