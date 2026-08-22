package resolve.projectdelivery.resolvestrategy.di

import androidx.room.Room
import resolve.projectdelivery.resolvestrategy.data.database.TGRGKDatabase
import org.koin.dsl.module

private const val DB_NAME = "tgrgk_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = TGRGKDatabase::class.java,
        name = DB_NAME
        ).build()
    }

    single { get<TGRGKDatabase>().bookingDao()}

}