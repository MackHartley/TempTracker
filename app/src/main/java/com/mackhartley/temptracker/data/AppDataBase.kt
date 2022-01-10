package com.mackhartley.temptracker.data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mackhartley.temptracker.data.models.Fever
import com.mackhartley.temptracker.data.models.TempLog

@Database(
    entities = [
        TempLog::class,
        Fever::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getDao(): AppDao

    companion object {

        @Volatile
        var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            val temporaryInstance = INSTANCE
            if (temporaryInstance != null) {
                return temporaryInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }
}