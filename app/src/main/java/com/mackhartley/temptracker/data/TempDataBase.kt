package com.mackhartley.temptracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        TempLog::class,
        TempCollection::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class TempDataBase : RoomDatabase() {

    abstract fun getDao(): TempDao

    companion object {

        @Volatile
        var INSTANCE: TempDataBase? = null

        fun getDatabase(context: Context): TempDataBase {
            val temporaryInstance = INSTANCE
            if (temporaryInstance != null) {
                return temporaryInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, TempDataBase::class.java, "temp_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }
}