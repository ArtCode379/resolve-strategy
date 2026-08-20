package resolveclaimsni.management.resolvestrategy.di

import resolveclaimsni.management.resolvestrategy.ui.viewmodel.BookingViewModel
import resolveclaimsni.management.resolvestrategy.ui.viewmodel.CheckoutViewModel
import resolveclaimsni.management.resolvestrategy.ui.viewmodel.RHDNCOnboardingVM
import resolveclaimsni.management.resolvestrategy.ui.viewmodel.ServiceDetailsViewModel
import resolveclaimsni.management.resolvestrategy.ui.viewmodel.ServiceViewModel
import resolveclaimsni.management.resolvestrategy.ui.viewmodel.RHDNCSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        RHDNCSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        RHDNCOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ServiceViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        ServiceDetailsViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        BookingViewModel(
            bookingRepository = get(),
            serviceRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            bookingRepository = get(),
        )
    }
}