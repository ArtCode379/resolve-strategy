package resolveclaimsni.management.resolvestrategy.di

import resolveclaimsni.management.resolvestrategy.data.repository.BookingRepository
import resolveclaimsni.management.resolvestrategy.data.repository.RHDNCOnboardingRepo
import resolveclaimsni.management.resolvestrategy.data.repository.ServiceRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        RHDNCOnboardingRepo(
            rhdncOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ServiceRepository() }

    single{
        BookingRepository(
            bookingDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}