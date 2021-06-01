package com.rumosoft.photogallery.presentation.gallery

import com.rumosoft.photogallery.domain.model.Image
import com.rumosoft.photogallery.domain.usecases.interfaces.GetImagesUseCase
import com.rumosoft.photogallery.infrastructure.Resource
import es.eurohelp.toshare.community.InstantExecutorExtension
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

    init {
        MockKAnnotations.init(this)

        coEvery {
            getImagesUseCase()
        } returns
                mockResult
    }

    @Test
    fun `getImages() calls GetImagesUseCase`() =
            coroutineRule.testDispatcher.runBlockingTest {
                // Arrange
                val sut = GalleryViewModel(getImagesUseCase)

                // Act
                sut.getImages()

                // Assert
                coVerify { getImagesUseCase() }
            }
}