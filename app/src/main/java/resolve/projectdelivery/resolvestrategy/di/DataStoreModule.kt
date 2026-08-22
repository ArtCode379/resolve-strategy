package resolve.projectdelivery.resolvestrategy.di

import resolve.projectdelivery.resolvestrategy.data.datastore.TGRGKOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { TGRGKOnboardingPrefs(androidContext()) }
}