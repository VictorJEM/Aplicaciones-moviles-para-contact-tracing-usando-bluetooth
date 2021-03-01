package com.espol.proyecto.asi_simulation.triage.persistance

import androidx.room.*

@Dao
interface TriageDAO {

    @Query("SELECT * FROM triage")
    fun getAll(): List<Triage>

    @Query("SELECT * FROM triage WHERE uid IN (:triageIds)")
    fun getAllByIds(triageIds: IntArray): List<Triage>

    @Query("SELECT * FROM triage WHERE date == (:date)")
    fun getAllByDate(date: String): List<Triage>

    @Query("SELECT DISTINCT date FROM triage ORDER BY date DESC")
    fun getUniqueDates(): List<String>

//    @Query("SELECT count(DISTINCT s_id) FROM triage WHERE date = (:date) ORDER BY s_id ASC")
//    fun loadCountDistinctSymptomsByDate(date: String): Int
//
//    @Query("SELECT s.description FROM triage t JOIN symptom s ON t.s_id = s.uid WHERE t.date = (:date) GROUP BY s.description ORDER BY t.s_id ASC")
//    fun loadDistinctSymptomsByDate(date: String): List<String>

    @Insert
    fun insertAll(vararg triages: Triage)

    @Delete
    fun delete(triage: Triage)

    @Query("DELETE FROM triage WHERE date == (:date)")
    fun deleteByDate(date : String)

    @Query("DELETE FROM triage WHERE 1 == 1")
    fun deleteAll()

    @Update
    fun update(triage: Triage)

}