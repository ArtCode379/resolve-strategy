package resolveclaimsni.management.resolvestrategy.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val RHDNC_PREFS_NAME = "rhdnc_prefs"

val Context.rhdncOnboardingStore by preferencesDataStore(name = RHDNC_PREFS_NAME)

class RHDNCOnboardingPrefs(
    private val context: Context
) {
    val onboardedStateFlow: Flow<Boolean?> = context.rhdncOnboardingStore.data.map { prefs ->
        prefs[ONBOARDED_STATE_KEY]
    }

    suspend fun setOnboardedState(state: Boolean) {
        context.rhdncOnboardingStore.edit { prefs ->
            prefs[ONBOARDED_STATE_KEY] = state
        }
    }

    companion object {
        private val ONBOARDED_STATE_KEY = booleanPreferencesKey("onboardedState")
    }
}