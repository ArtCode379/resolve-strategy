package resolveclaimsni.management.resolvestrategy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import resolveclaimsni.management.resolvestrategy.data.dao.BookingDao
import resolveclaimsni.management.resolvestrategy.data.database.converter.Converters
import resolveclaimsni.management.resolvestrategy.data.entity.BookingEntity

@Database(
    entities = [BookingEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RHDNCDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
}

