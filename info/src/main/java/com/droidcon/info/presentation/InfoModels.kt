package com.droidcon.info.presentation

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class InfoState(
    val infoOptions: List<InfoOption>
)

sealed class InfoOption {
    data class Row(
        val id: Long,
        @StringRes val title: Int,
        @DrawableRes val icon: Int,
        val action: () -> Unit
    ) : InfoOption()

    data class Map(
        val image: String,
        val action: () -> Unit
    ) : InfoOption()
}

sealed class InfoEffect {
    data class NavigateToUri(val url: Uri) : InfoEffect()
    data class NavigateToMaps(val mapsInfo: Uri) : InfoEffect()
    object NavigateToContactsOfInterest : InfoEffect()
}