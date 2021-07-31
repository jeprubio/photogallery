package com.rumosoft.feature_images.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rumosoft.feature_images.MainCoroutineRule
import com.rumosoft.feature_images.Samples
import com.rumosoft.feature_images.data.network.mappers.toImage
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.feature_images.infrastructure.Resource
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
internal class RemoveImageUseCaseImplTest {
    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    lateinit var repository: ImagesRepository

    init {
        MockKAnnotations.init(this)
        coEvery { repository.removeImage(any()) } returns
            Resource.Success(Unit)
    }

    @Test
    fun `RemoveImage usecase invocation calls removeImage on repository`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = RemoveImageUseCaseImpl(repository)
            val image = Samples.sampleApiImage().toImage()

            // Act
            sut(image)

            // Assert
            coVerify { repository.removeImage(image) }
        }
}
