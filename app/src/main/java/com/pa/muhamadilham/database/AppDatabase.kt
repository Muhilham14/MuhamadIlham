package com.pa.muhamadilham.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhamadilham.model.ModelDatabase
import com.pa.muhamadilham.database.dao.DatabaseDao

@Database(entities = [ModelDatabase::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao?
}