package com.example.organizd

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.organizd.db.Task
import com.example.organizd.db.TaskDao
import com.example.organizd.db.TestDatabase
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.organizd", appContext.packageName)
    }

}

@Suppress("DEPRECATION")
class addTaskTest {

    private lateinit var taskDao: TaskDao
    private lateinit var db: TestDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TestDatabase::class.java
        ).build()
        taskDao = db.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    suspend fun writeUserAndReadInList() : Unit{
        val task = Task(1, "today", "Study", "today", false)


        taskDao.addTask(task)
        val bydate = taskDao.readSpecificTask(1)
        taskDao.deleteTask(task)
        return assert(bydate.name == task.name)



        val bydate2 = taskDao.readSpecificTask(1)
        assert(bydate2.name.isNullOrEmpty())
    }
}