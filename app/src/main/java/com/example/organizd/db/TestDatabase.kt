package com.example.organizd.db

import android.content.Context
import androidx.room.Room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TestDatabase : RoomDatabase(){


    abstract fun  taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TestDatabase? = null

        //controlliamo che ci sia solo una instance del nostro db.
        //se c'è usiamo quella esistente, sennò la costruiamo.

        fun  getDatabase(context: Context): TestDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestDatabase::class.java,
                    "test_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}