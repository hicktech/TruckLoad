package com.github.jw3.tl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Load::class], version = 1)
abstract class LoadsDB : RoomDatabase() {
    abstract fun loadsDao(): LoadDao

    companion object {
        private var DB_INSTANCE: LoadsDB? = null

        fun getAppDbInstance(context: Context): LoadsDB {
            if(DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext, LoadsDB::class.java, "LoadsDb"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }

    }
}
