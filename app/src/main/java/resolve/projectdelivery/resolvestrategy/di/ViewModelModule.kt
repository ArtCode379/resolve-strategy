package resolve.projectdelivery.resolvestrategy.di

import resolve.projectdelivery.resolvestrategy.ui.viewmodel.BookingViewModel
import resolve.projectdelivery.resolvestrategy.ui.viewmodel.CheckoutViewModel
import resolve.projectdelivery.resolvestrategy.ui.viewmodel.TGRGKOnboardingVM
import resolve.projectdelivery.resolvestrategy.ui.viewmodel.ServiceDetailsViewModel
import resolve.projectdelivery.resolvestrategy.ui.viewmodel.ServiceViewModel
import resolve.projectdelivery.resolvestrategy.ui.viewmodel.TGRGKSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        TGRGKSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        TGRGKOnboardingVM(
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