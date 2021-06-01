package com.rumosoft.photogallery.data.network

import com.rumosoft.photogallery.data.network.apimodels.ApiImage
import com.rumosoft.photogallery.infrastructure.Resource
import com.rumosoft.photogallery.sampleImage
import com.rumosoft.photogallery.MainCoroutineRule
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
internal class ImagesNetworkImplTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @Test
    fun `When perform Search is successful returns Success`() =
            coroutineRule.testDispatcher.runBlockingTest {
                // Arrange
                val sut = ImagesNetworkImpl(successService())

                // Act
                val response = sut.getImages()

                // Assert
                assertNotNull(response)
                assertTrue(response is Resource.Success)
                val data = when (response) {
                    is Resource.Success -> response.data
                    else -> null
                }
                Assertions.assertEquals(1, data?.size)
            }

    @Test
    fun `When perform Search fails returns Error`() =
            coroutineRule.testDispatcher.runBlockingTest {
                // Arrange
                val sut = ImagesNetworkImpl(errorService())

                // Act
                val response = sut.getImages()

                // Assert
                assertNotNull(response)
                assertTrue(response is Resource.Error)
            }

    private fun successService(): ImagesService {
        return MockServiceSuccess()
    }

    private fun errorService(): ImagesService {
        return MockServiceError()
    }

    class MockServiceSuccess : ImagesService {
        override suspend fun loadImages(): List<ApiImage> = listOf(sampleImage())
    }

    class MockServiceError : ImagesService {
        override suspend fun loadImages(): List<ApiImage> = throw Exception("Images Exception")
    }
}