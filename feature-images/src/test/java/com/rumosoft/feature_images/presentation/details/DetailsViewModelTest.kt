package com.rumosoft.feature_images.presentation.details

import android.text.Editable
import androidx.lifecycle.SavedStateHandle
import com.google.gson.Gson
import com.rumosoft.feature_images.InstantExecutorExtension
import com.rumosoft.feature_images.MainCoroutineRule
import com.rumosoft.feature_images.Samples
import com.rumosoft.feature_images.data.network.mappers.toImage
import com.rumosoft.feature_images.domain.usecases.interfaces.AddImageUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.UpdateImageTitleUseCase
import com.rumosoft.feature_images.infrastructure.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
internal class DetailsViewModelTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    lateinit var addImageUseCase: AddImageUseCase

    @MockK
    lateinit var updateImageTitleUseCase: UpdateImageTitleUseCase

    private var sampleImage = Samples.sampleApiImage().toImage()

    init {
        MockKAnnotations.init(this)
        coEvery {
            addImageUseCase(any())
        } returns Resource.Success(1L)
        coEvery {
            updateImageTitleUseCase(any())
        } returns Resource.Success(Samples.sampleApiImage().toImage())
    }

    @Test
    fun `saveImage() calls UpdateImageTitle use case if it has id greater than 0`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val savedStateHandle = SavedStateHandle().apply {
                set("item", Gson().toJson(sampleImage))
            }
            val sut = DetailsViewModel(addImageUseCase, updateImageTitleUseCase, savedStateHandle)

            // Act
            sut.saveImage()

            // Assert
            coVerify { updateImageTitleUseCase(any()) }
        }

    @Test
    fun `saveImage() calls addImageUseCase use case if it has id lower or equal to 0`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val savedStateHandle = SavedStateHandle().apply {
                set("item", Gson().toJson(sampleImage.copy(id = -1L)))
            }
            val sut = DetailsViewModel(addImageUseCase, updateImageTitleUseCase, savedStateHandle)

            // Act
            sut.saveImage()

            // Assert
            coVerify { addImageUseCase(any()) }
        }

    @Test
    fun `afterTitleChanged() updates the image LiveData value`() =
        coroutineRule.testDispatcher.runBlockingTest {
            // Arrange
            val savedStateHandle = SavedStateHandle().apply {
                set("item", Gson().toJson(sampleImage))
            }
            val sut = DetailsViewModel(addImageUseCase, updateImageTitleUseCase, savedStateHandle)
            val editable = mockk<Editable>()
            val editableString = "PhotoGallery Rules!"
            coEvery { editable.toString() } returns editableString
            assertNotEquals(editableString, sut.image.value!!.title)

            // Act
            sut.afterTitleChanged(editable)

            // Assert
            assertEquals(editableString, sut.image.value!!.title)
        }
}
