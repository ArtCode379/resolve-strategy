package resolve.projectdelivery.resolvestrategy.di

import resolve.projectdelivery.resolvestrategy.data.repository.BookingRepository
import resolve.projectdelivery.resolvestrategy.data.repository.TGRGKOnboardingRepo
import resolve.projectdelivery.resolvestrategy.data.repository.ServiceRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        TGRGKOnboardingRepo(
            tgrgkOnboardingStoreManager = get(),
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