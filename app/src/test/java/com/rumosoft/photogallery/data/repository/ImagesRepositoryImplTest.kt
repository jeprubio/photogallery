package com.rumosoft.photogallery.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rumosoft.photogallery.MainCoroutineRule
import com.rumosoft.photogallery.Samples
import com.rumosoft.photogallery.data.network.ImagesNetwork
import com.rumosoft.photogallery.data.network.mappers.toImage
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.infrastructure.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Test
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
    lateinit var image: Image

    init {
        MockKAnnotations.init(this)
        stubbGetImages()
        stubbAddImage()
        stubbEditImage()
        stubbUpdateImageTitle()
        stubbRemoveImage()
    }

    @Test
    fun `getImages is called when calling getImages in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesRepositoryImpl(imagesNetwork)

            // Act
            sut.getImages()

            // Assert
            coVerify { imagesNetwork.getImages() }
        }
    }

    @Test
    fun `addImage is called when calling addImage in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesRepositoryImpl(imagesNetwork)
            val image = Samples.sampleApiImage().toImage()

            // Act
            sut.addImage(image)

            // Assert
            coVerify { imagesNetwork.addImage(image) }
        }
    }

    @Test
    fun `editImage is called when calling editImage in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesRepositoryImpl(imagesNetwork)
            val image = Samples.sampleApiImage().toImage()

            // Act
            sut.editImage(image)

            // Assert
            coVerify { imagesNetwork.editImage(image) }
        }
    }

    @Test
    fun `updateTitleImage is called when calling updateTitleImage in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesRepositoryImpl(imagesNetwork)
            val image = Samples.sampleApiImage().toImage()

            // Act
            sut.updateImageTitle(image)

            // Assert
            coVerify { imagesNetwork.updateImageTitle(image) }
        }
    }

    @Test
    fun `removeImage is called when calling removeImage in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesRepositoryImpl(imagesNetwork)
            val image = Samples.sampleApiImage().toImage()

            // Act
            sut.removeImage(image)

            // Assert
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
}
