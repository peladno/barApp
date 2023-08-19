package com.example.barapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TableData::class, DetailsData::class], version = 1, exportSchema = false )
abstract class TableDatabase : RoomDatabase() {
    abstract fun tableDataDao(): TableDao

    companion object{
        @Volatile
        private var INSTANCE: TableDatabase? = null

        fun getDatabase(context: Context): TableDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TableDatabase::class.java,
                    "TableDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}