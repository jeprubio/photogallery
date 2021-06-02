package com.rumosoft.feature_images.domain.usecases

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rumosoft.feature_images.MainCoroutineRule
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.BitmapRepository
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
internal class StoreImageFromContentUseCaseImplTest {
    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    lateinit var repository: BitmapRepository

    @MockK
    lateinit var uri: Uri

    init {
        MockKAnnotations.init(this)
        coEvery { repository.storeImageFromContentUri(any(), any(), any()) } returns
                "image path"
    }

    @Test
    fun `GetImages usecase invocation calls getImages on repository`() =
            coroutineRule.testDispatcher.runBlockingTest {
                // Arrange
                val sut = StoreImageFromContentUseCaseImpl(repository)
                val width = 12
                val height = 34

                // Act
                sut(uri, width, height)

                // Assert
                coVerify { repository.storeImageFromContentUri(uri, width, height) }
            }
}