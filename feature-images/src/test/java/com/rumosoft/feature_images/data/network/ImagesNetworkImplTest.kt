package com.rumosoft.feature_images.data.network

import com.rumosoft.feature_images.MainCoroutineRule
import com.rumosoft.feature_images.Samples.sampleApiImage
import com.rumosoft.feature_images.data.network.mappers.toImage
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.infrastructure.Resource
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue

@ExperimentalCoroutinesApi
internal class ImagesNetworkImplTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @Test
    fun `When getImages is successful returns Success`() =
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
    fun `When getImages fails returns Error`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesNetworkImpl(errorService())

            // Act
            val response = sut.getImages()

            // Assert
            assertNotNull(response)
            assertTrue(response is Resource.Error)
        }

    @Test
    fun `When addImage is successful returns Success`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesNetworkImpl(successService())
            val image = sampleApiImage().toImage()

            // Act
            val response = sut.addImage(image)

            // Assert
            assertNotNull(response)
            assertTrue(response is Resource.Success)
            val data = when (response) {
                is Resource.Success -> response.data
                else -> null
            }
            Assertions.assertEquals(image.id, data)
        }

    @Test
    fun `When addImage fails returns Error`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesNetworkImpl(errorService())
            val image = mockk<Image>()

            // Act
            val response = sut.addImage(image)

            // Assert
            assertNotNull(response)
            assertTrue(response is Resource.Error)
        }

    @Test
    fun `When editImage is successful returns Success`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesNetworkImpl(successService())
            val image = sampleApiImage().toImage()

            // Act
            val response = sut.editImage(image)

            // Assert
            assertNotNull(response)
            assertTrue(response is Resource.Success)
            val data = when (response) {
                is Resource.Success -> response.data
                else -> null
            }
            Assertions.assertEquals(image.id, data)
        }

    @Test
    fun `When editImage fails returns Error`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesNetworkImpl(errorService())
            val image = mockk<Image>()

            // Act
            val response = sut.addImage(image)

            // Assert
            assertNotNull(response)
            assertTrue(response is Resource.Error)
        }

    @Test
    fun `When updateTitleImage is successful returns Success`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesNetworkImpl(successService())
            val image = sampleApiImage().toImage()

            // Act
            val response = sut.updateImageTitle(image)

            // Assert
            assertNotNull(response)
            assertTrue(response is Resource.Success)
            val data = when (response) {
                is Resource.Success -> response.data
                else -> null
            }
            Assertions.assertEquals(image, data)
        }

    @Test
    fun `When updateTitleImage fails returns Error`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesNetworkImpl(errorService())
            val image = mockk<Image>()

            // Act
            val response = sut.updateImageTitle(image)

            // Assert
            assertNotNull(response)
            assertTrue(response is Resource.Error)
        }

    @Test
    fun `When removeImage is successful returns Success`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesNetworkImpl(successService())
            val image = sampleApiImage().toImage()

            // Act
            val response = sut.removeImage(image)

            // Assert
            assertNotNull(response)
            assertTrue(response is Resource.Success)
        }

    @Test
    fun `When removeImage fails returns Error`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = ImagesNetworkImpl(errorService())
            val image = mockk<Image>()

            // Act
            val response = sut.removeImage(image)

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
}
