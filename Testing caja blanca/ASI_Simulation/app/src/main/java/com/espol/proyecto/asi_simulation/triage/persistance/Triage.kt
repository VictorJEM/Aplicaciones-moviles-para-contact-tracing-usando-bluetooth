package com.espol.proyecto.asi_simulation.triage.persistance

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
//import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "triage")
data class Triage (
        @Nullable @PrimaryKey(autoGenerate = true) val uid: Int?,
        @ColumnInfo(name = "date") val date: String?,
        @ColumnInfo(name = "s1_index") val s1_index : Int?,
        @ColumnInfo(name = "s2_index") val s2_index : Int?,
        @ColumnInfo(name = "s3_index") val s3_index : Int?,
        @ColumnInfo(name = "s4_index") val s4_index : Int?
)