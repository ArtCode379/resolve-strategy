package resolve.projectdelivery.resolvestrategy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import resolve.projectdelivery.resolvestrategy.data.dao.BookingDao
import resolve.projectdelivery.resolvestrategy.data.database.converter.Converters
import resolve.projectdelivery.resolvestrategy.data.entity.BookingEntity

@Database(
    entities = [BookingEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TGRGKDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
}

