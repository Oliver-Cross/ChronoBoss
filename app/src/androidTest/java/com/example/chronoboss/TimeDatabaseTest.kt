package com.example.chronoboss

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.chronoboss.database.TimeDatabase
import com.example.chronoboss.database.TimeDatabaseDao
import com.example.chronoboss.database.TimeTracking
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.sql.Time

@RunWith(AndroidJUnit4::class)
class TimeDatabaseTest {

    private lateinit var timeDao: TimeDatabaseDao
    private lateinit var db: TimeDatabase

    @Before
    fun createDb() {
        print("Before working")
        //val context = InstrumentationRegistry.getInstrumentation().targetContext
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(context, TimeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        timeDao = db.timeDatabaseDao
    }

//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWord() = runBlocking {
        val word = TimeTracking()
        timeDao.insert(word)
        val allWords = timeDao.getAlphabetizedWords().first()
        assertEquals(allWords[0].word, word.word)
    }

    @Test
    @Throws(Exception::class)
    fun getAllWords() = runBlocking {
        val word = Word("aaa")
        wordDao.insert(word)
        val word2 = Word("bbb")
        wordDao.insert(word2)
        val allWords = wordDao.getAlphabetizedWords().first()
        assertEquals(allWords[0].word, word.word)
        assertEquals(allWords[1].word, word2.word)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val word = Word("word")
        wordDao.insert(word)
        val word2 = Word("word2")
        wordDao.insert(word2)
        wordDao.deleteAll()
        val allWords = wordDao.getAlphabetizedWords().first()
        Assert.assertTrue(allWords.isEmpty())
    }


}