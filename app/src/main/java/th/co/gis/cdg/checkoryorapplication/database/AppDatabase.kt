package th.co.gis.cdg.checkoryorapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import th.co.gis.cdg.checkoryorapplication.model.Oryor

@Database(
    entities = [
        Oryor::class
    ], version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun uploadDao(): UploadDao
}