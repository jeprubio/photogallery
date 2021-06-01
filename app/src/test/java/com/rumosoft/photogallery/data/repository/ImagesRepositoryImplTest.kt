package com.rumosoft.photogallery.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rumosoft.photogallery.data.network.ImagesNetwork
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.infrastructure.Resource
import es.eurohelp.toshare.community.MainCoroutineRule
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
        coEvery { imagesNetwork.getImages() } returns
                Resource.Success(
                        listOf(
                                image
                        )
                )
    }

    @Test
    fun `searchNetwork is called when calling getImages in the repository`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesRepositoryImpl(imagesNetwork)

            // Act
            sut.getImages()

            // Assert
            coVerify { imagesNetwork.getImages() }
        }
    }
}