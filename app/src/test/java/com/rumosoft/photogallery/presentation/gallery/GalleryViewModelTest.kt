package com.rumosoft.photogallery.presentation.gallery

import android.net.Uri
import com.rumosoft.photogallery.InstantExecutorExtension
import com.rumosoft.photogallery.MainCoroutineRule
import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.photogallery.domain.usecases.interfaces.StoreImageFromContentUseCase
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
    lateinit var uri: Uri

    init {
        MockKAnnotations.init(this)

        coEvery {
            getImagesUseCase()
        } returns
                mockResult
        coEvery {
            storeImagesUseCase(any(), any(), any())
        } returns
                "image url"
    }

    @Test
    fun `getImages() calls GetImagesUseCase`() =
            coroutineRule.testDispatcher.runBlockingTest {
                // Arrange
                val sut = GalleryViewModel(getImagesUseCase, storeImagesUseCase)

                // Act
                sut.getImages()

                // Assert
                coVerify { getImagesUseCase() }
            }

    @Test
    fun `onImagePicked() calls GetImagesUseCase`() =
            coroutineRule.testDispatcher.runBlockingTest {
                // Arrange
                val sut = GalleryViewModel(getImagesUseCase, storeImagesUseCase)

                // Act
                sut.onImagePicked(uri)

                // Assert
                coVerify { storeImagesUseCase(uri, null, null) }
            }
}