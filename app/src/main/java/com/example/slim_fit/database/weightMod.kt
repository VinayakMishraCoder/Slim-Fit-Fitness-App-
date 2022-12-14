package com.example.slim_fit.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="weight_entries")
data class weightMod (
    @PrimaryKey(autoGenerate = true)
    val weightId: Long = 0L,

    @ColumnInfo(name="weight")
    var weight: Double,

    @ColumnInfo(name="photoUri")
    var photoUri: String,

    @ColumnInfo(name="date")
    var date: String
    )