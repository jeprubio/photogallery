package com.rumosoft.photogallery.presentation.gallery

import android.net.Uri
import com.rumosoft.photogallery.InstantExecutorExtension
import com.rumosoft.photogallery.MainCoroutineRule
import com.rumosoft.photogallery.Samples
import com.rumosoft.photogallery.data.network.mappers.toImage
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.photogallery.domain.usecases.interfaces.RemoveImageUseCase
import com.rumosoft.photogallery.domain.usecases.interfaces.StoreImageFromContentUseCase
import com.rumosoft.photogallery.infrastructure.Resource
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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
internal class GalleryViewModelTest {
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
        stubbGetImagesUseCase()
        stubbStoreImagesUseCase()
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

    private fun stubbGetImagesUseCase() {
        coEvery {
            getImagesUseCase()
        } returns
                mockResult
    }

    private fun stubbStoreImagesUseCase() {
        coEvery {
            storeImagesUseCase(any(), any(), any())
        } returns
                "image url"
    }
}