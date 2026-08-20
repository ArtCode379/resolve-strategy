package resolveclaimsni.management.resolvestrategy.di

import androidx.room.Room
import resolveclaimsni.management.resolvestrategy.data.database.RHDNCDatabase
import org.koin.dsl.module

private const val DB_NAME = "rhdnc_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = RHDNCDatabase::class.java,
        name = DB_NAME
        ).build()
    }

    single { get<RHDNCDatabase>().bookingDao()}

}