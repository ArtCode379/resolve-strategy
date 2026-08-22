package resolve.projectdelivery.resolvestrategy.data.repository

import resolve.projectdelivery.resolvestrategy.data.datastore.TGRGKOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TGRGKOnboardingRepo(
    private val tgrgkOnboardingStoreManager: TGRGKOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return tgrgkOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            tgrgkOnboardingStoreManager.setOnboardedState(state)
        }
    }
}