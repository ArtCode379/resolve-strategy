package resolveclaimsni.management.resolvestrategy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import resolveclaimsni.management.resolvestrategy.data.repository.RHDNCOnboardingRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RHDNCSplashVM(
    private val onboardingRepository: RHDNCOnboardingRepo,
) : ViewModel() {
    val onboardedState: StateFlow<Boolean> =
        onboardingRepository.observeOnboardingState()
            .map { it == true }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = false
            )

}