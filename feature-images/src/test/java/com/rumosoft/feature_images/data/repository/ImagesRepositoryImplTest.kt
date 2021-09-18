package com.rumosoft.feature_images.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rumosoft.feature_images.MainCoroutineRule
import com.rumosoft.feature_images.Samples
import com.rumosoft.feature_images.data.database.ImageDao
import com.rumosoft.feature_images.data.network.ImagesNetwork
import com.rumosoft.feature_images.data.network.mappers.toImage
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.infrastructure.Resource
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
internal class ImagesRepositoryImplTest {
    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    lateinit var imagesNetwork: ImagesNetwork

    @MockK
    lateinit var imageDao: ImageDao

    @MockK(relaxed = true)
    lateinit var image: Image

    init {
        MockKAnnotations.init(this)
        stubbGetImages()
        stubbAddImage()
        stubbEditImage()
        stubbUpdateImageTitle()
        stubbRemoveImage()
        stubbImageDao()
    }

    @Test
    fun `getImages is called when calling getImages in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            val sut = ImagesRepositoryImpl(imagesNetwork, imageDao)

            sut.getImages()

            coVerify { imagesNetwork.getImages() }
            coVerify(exactly = 0) { imageDao.getImages() }
        }
    }

    @Test
    fun `if getImages fails then the database gets queried`() {
        coroutineRule.testDispatcher.runBlockingTest {
            val sut = ImagesRepositoryImpl(imagesNetwork, imageDao)
            coEvery { imagesNetwork.getImages() } returns
                Resource.Error(Throwable("Could not get images"))

            sut.getImages()

            coVerify { imageDao.getImages() }
        }
    }

    @Test
    fun `addImage is called when calling addImage in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            val sut = ImagesRepositoryImpl(imagesNetwork, imageDao)
            val image = Samples.sampleApiImage().toImage()

            sut.addImage(image)

            coVerify { imagesNetwork.addImage(image) }
        }
    }

    @Test
    fun `editImage is called when calling editImage in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            val sut = ImagesRepositoryImpl(imagesNetwork, imageDao)
            val image = Samples.sampleApiImage().toImage()

            sut.editImage(image)

            coVerify { imagesNetwork.editImage(image) }
        }
    }

    @Test
    fun `updateTitleImage is called when calling updateTitleImage in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            val sut = ImagesRepositoryImpl(imagesNetwork, imageDao)
            val image = Samples.sampleApiImage().toImage()

            sut.updateImageTitle(image)

            coVerify { imagesNetwork.updateImageTitle(image) }
        }
    }

    @Test
    fun `removeImage is called when calling removeImage in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            val sut = ImagesRepositoryImpl(imagesNetwork, imageDao)
            val image = Samples.sampleApiImage().toImage()

            sut.removeImage(image)

            coVerify { imagesNetwork.removeImage(image) }
        }
    }

    private fun stubbGetImages() {
        coEvery { imagesNetwork.getImages() } returns
            Resource.Success(
                listOf(
                    image
                )
            )
    }

    private fun stubbAddImage() {
        coEvery { imagesNetwork.addImage(any()) } returns
            Resource.Success(
                Samples.sampleApiImage().id
            )
    }

    private fun stubbEditImage() {
        coEvery { imagesNetwork.editImage(any()) } returns
            Resource.Success(
                Samples.sampleApiImage().id
            )
    }

    private fun stubbUpdateImageTitle() {
        coEvery { imagesNetwork.updateImageTitle(any()) } returns
            Resource.Success(
                Samples.sampleApiImage().toImage()
            )
    }

    private fun stubbRemoveImage() {
        coEvery { imagesNetwork.removeImage(any()) } returns
            Resource.Success(Unit)
    }

    private fun stubbImageDao() {
        coEvery { imageDao.insertAll(any()) } just Runs
    }
}
