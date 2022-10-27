package com.example.organizd

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.organizd.db.Task
import com.example.organizd.db.TaskDao
import com.example.organizd.db.TestDatabase
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

import org.junit.runner.RunWith

import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


}

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class AddTaskTest{

    private lateinit var taskDao: TaskDao
    private lateinit var db: TestDatabase



    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TestDatabase::class.java).build()
        taskDao = db.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }



    @Test
    @Throws(Exception::class)
    suspend fun writeUserAndReadInList() {
        val task = Task(1,"today","Study","today",false)


        taskDao.addTask(task)
        val bydate = taskDao.readSpecificTask(1)

        assert(bydate == task)

        taskDao.deleteTask(task)

}

}