package com.example.slim_fit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface appDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weight: weightMod) : Long

    @Query("SELECT * FROM weight_entries ORDER BY date DESC")
    suspend fun getAllEntries(): List<weightMod>

    @Query("SELECT * FROM weight_entries ORDER BY weightId ASC")
    suspend fun getEntriesForGraph(): List<weightMod>

    @Query("DELETE FROM weight_entries")
    suspend fun clearAllEntries()
}