package resolveclaimsni.management.resolvestrategy.di

import resolveclaimsni.management.resolvestrategy.data.datastore.RHDNCOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { RHDNCOnboardingPrefs(androidContext()) }
}