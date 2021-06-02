package com.rumosoft.feature_images.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.rumosoft.feature_images.CoroutineTestRule
import com.rumosoft.feature_images.data.database.dbmodels.ImageEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4ClassRunner::class)
class RoomEntityReadWriteTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var imageDao: ImageDao
    private lateinit var db: ImagesDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
                context, ImagesDatabase::class.java
        ).build()
        imageDao = db.imageDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeImageEntityAndCheckItCanBeRetrieved() = runBlocking {
        // Arrange
        val imageEntity: ImageEntity = sampleImageEntity()

        // Act
        imageDao.insert(imageEntity)

        // Assert
        assertEquals(imageEntity, imageDao.getImage(imageEntity.id))
    }

    private fun sampleImageEntity(updated: Date = Date()): ImageEntity {
        return ImageEntity(
                id = 1L,
                title = "title",
                image = "image",
                thumbnail = "thumbnail",
        )
    }
}

