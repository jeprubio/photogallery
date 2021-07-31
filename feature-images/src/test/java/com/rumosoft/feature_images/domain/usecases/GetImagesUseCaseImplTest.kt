package com.rumosoft.feature_images.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rumosoft.feature_images.MainCoroutineRule
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
internal class GetImagesUseCaseImplTest {
    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    lateinit var repository: ImagesRepository

    init {
        MockKAnnotations.init(this)
        coEvery { repository.getImages() } returns
            Resource.Success(emptyList())
    }

    @Test
    fun `GetImages usecase invocation calls getImages on repository`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val sut = GetImagesUseCaseImpl(repository)

            // Act
            sut()

            // Assert
            coVerify { repository.getImages() }
        }
}
