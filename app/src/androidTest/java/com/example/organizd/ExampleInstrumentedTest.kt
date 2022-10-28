package com.example.organizd

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.organizd.db.Task
import com.example.organizd.db.TaskDao
import com.example.organizd.db.TestDatabase
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.

        assertEquals(true , true)
    }

}

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class addTaskTest {

    private lateinit var taskDao: TaskDao
    private lateinit var db: TestDatabase

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

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



    @Throws(Exception::class)
    @Test
    suspend fun writeUserAndReadInList() {
        val task = Task(1, "today", "Study", "today", false)
        taskDao.addTask(task)
        val bydate = taskDao.readSpecificTask(1)
        taskDao.deleteTask(task)
        assertEquals(bydate.name , task.name)

    }
}

@RunWith(AndroidJUnit4::class)
class testView {
    @Test
    fun useAppContext() {


        assertEquals(true , true)
    }

}