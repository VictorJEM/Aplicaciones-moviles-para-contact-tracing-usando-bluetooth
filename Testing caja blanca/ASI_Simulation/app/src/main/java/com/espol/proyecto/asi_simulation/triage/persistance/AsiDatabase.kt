package com.espol.proyecto.asi_simulation.triage.persistance


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Triage::class], version = 5)
abstract class AsiDatabase : RoomDatabase() {

    abstract fun triageDAO() : TriageDAO


    companion object{
        @Volatile
        private var INSTANCE: AsiDatabase? = null

        fun getInstance(context: Context): AsiDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context,
                            AsiDatabase::class.java, "asi-db"
                    ).fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}