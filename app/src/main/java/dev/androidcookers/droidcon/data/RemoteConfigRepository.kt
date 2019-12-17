package dev.androidcookers.droidcon.data

import android.util.Log
import com.droidcon.commons.ioc.IoDispatcher
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteConfigRepository @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    private val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    suspend fun init() {
        withContext(ioDispatcher) {
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10)
                .build()
            firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        }
    }

    suspend fun refresh(onFetchedAndActivated: () -> Unit) {
        withContext(ioDispatcher) {
            firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { fetchAndActivate ->
                if (fetchAndActivate.isSuccessful) {
                    val versionCode = firebaseRemoteConfig.all["version"]?.asLong()
                    Log.d("RemoteConfig", "remote config refreshed $versionCode")
                    onFetchedAndActivated()
                } else {
                    Log.d("RemoteConfig", "activate unsuccessful")
                }

            }
        }
    }

    suspend fun getConferenceDataVersion(): Long? = withContext(ioDispatcher) {
        firebaseRemoteConfig.all["version"]?.asLong()
    }
}