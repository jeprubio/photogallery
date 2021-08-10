package com.rumosoft.feature_images.presentation.gallery

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rumosoft.feature_images.InstantExecutorExtension
import com.rumosoft.feature_images.MainCoroutineRule
import com.rumosoft.feature_images.Samples
import com.rumosoft.feature_images.data.network.mappers.toImage
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.RemoveImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.StoreImageFromContentUseCase
import com.rumosoft.feature_images.infrastructure.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
internal class GalleryViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    lateinit var mockResult: Resource<List<Image>>

    @MockK
    lateinit var getImagesUseCase: GetImagesUseCase

    @MockK
    lateinit var storeImagesUseCase: StoreImageFromContentUseCase

    @MockK
    lateinit var removeImageUseCase: RemoveImageUseCase

    private val sut: GalleryViewModel

    @MockK
    lateinit var uri: Uri

    init {
        MockKAnnotations.init(this)
        stubGetImagesUseCase()
        stubStoreImagesUseCase()
        stubRemoveImageUseCase()
        sut = GalleryViewModel(getImagesUseCase, storeImagesUseCase, removeImageUseCase)
    }

    @Test
    fun `getImages() calls GetImagesUseCase`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange

            // Act
            sut.getImages()

            // Assert
            coVerify { getImagesUseCase() }
        }

    @Test
    fun `onImagePicked() calls GetImagesUseCase`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange

            // Act
            sut.onImagePicked(uri)

            // Assert
            coVerify { storeImagesUseCase(uri, null, null) }
        }

    @Test
    fun `removeImage() calls RemoveImageUseCase`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val image = Samples.sampleApiImage().toImage()

            // Act
            sut.removeImage(image)

            // Assert
            coVerify { removeImageUseCase(image) }
        }

    @Test
    fun `restoreImagePicked() cleans the livedata value`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = GalleryViewModel(getImagesUseCase, storeImagesUseCase, removeImageUseCase)
            sut.onImagePicked(uri)
            assertNotNull(sut.imagePicked.value)

            // Act
            sut.restoreImagePicked()

            // Assert
            assertNull(sut.imagePicked.value)
        }

    @Test
    fun `restoreImageRemoved() cleans the livedata value`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = GalleryViewModel(getImagesUseCase, storeImagesUseCase, removeImageUseCase)
            val image = Samples.sampleApiImage().toImage()
            sut.removeImage(image)
            assertNotNull(sut.imageRemoveResult.value)

            // Act
            sut.restoreImageRemoved()

            // Assert
            assertNull(sut.imageRemoveResult.value)
        }

    private fun stubGetImagesUseCase() {
        coEvery {
            getImagesUseCase()
        } returns
            mockResult
    }

    private fun stubStoreImagesUseCase() {
        coEvery {
            storeImagesUseCase(any(), any(), any())
        } returns
            "image url"
    }

    private fun stubRemoveImageUseCase() {
        coEvery {
            removeImageUseCase(any())
        } returns Resource.Success(Unit)
    }
}
