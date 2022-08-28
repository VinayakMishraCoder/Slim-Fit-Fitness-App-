package com.example.slim_fit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(weightMod::class), version = 1, exportSchema = false)
public abstract class appDatabase : RoomDatabase() {

    abstract fun weightDatabaseDao(): appDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: appDatabase? = null

        fun getDatabase(context: Context): appDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "weight_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
