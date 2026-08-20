package resolveclaimsni.management.resolvestrategy.data.repository

import resolveclaimsni.management.resolvestrategy.data.datastore.RHDNCOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RHDNCOnboardingRepo(
    private val rhdncOnboardingStoreManager: RHDNCOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return rhdncOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            rhdncOnboardingStoreManager.setOnboardedState(state)
        }
    }
}