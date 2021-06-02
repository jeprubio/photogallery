package com.rumosoft.photogallery.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rumosoft.photogallery.MainCoroutineRule
import com.rumosoft.photogallery.Samples
import com.rumosoft.photogallery.data.network.mappers.toImage
import com.rumosoft.photogallery.domain.usecases.interfaces.repository.ImagesRepository
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
internal class UpdateImageTitleUseCaseImplTest {
    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    lateinit var repository: ImagesRepository

    init {
        MockKAnnotations.init(this)
        coEvery { repository.updateImageTitle(any()) } returns
                Resource.Success(Samples.sampleApiImage().toImage())
    }

    @Test
    fun `UpdateImageTitle usecase invocation calls getImages on repository`() =
            coroutineRule.testDispatcher.runBlockingTest {
                // Arrange
                val sut = UpdateImageTitleUseCaseImpl(repository)
                val image = Samples.sampleApiImage().toImage()

                // Act
                sut(image)

                // Assert
                coVerify { repository.updateImageTitle(image) }
            }
}