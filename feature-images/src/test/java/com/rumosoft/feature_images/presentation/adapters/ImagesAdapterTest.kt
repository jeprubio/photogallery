package com.rumosoft.feature_images.presentation.adapters

import com.rumosoft.feature_images.Samples
import com.rumosoft.feature_images.data.network.mappers.toImage
import com.rumosoft.feature_images.presentation.listeners.ImageClickListener
import io.mockk.every
import io.mockk.spyk
import org.junit.Assert
import org.junit.jupiter.api.Test

internal class ImagesAdapterTest {
    private var sut: ImagesAdapter =
        spyk(
            ImagesAdapter(
                ImageClickListener { itemId ->
                    // Do nothing
                },
                ImageClickListener {
                    // Do nothing
                }
            )
        )

    init {
        every { sut.notifyDataSetChanged() } answers { nothing }
    }

    @Test
    fun `The adapter data can be updated`() {
        // Arrange
        sut.data = listOf()
        Assert.assertEquals(0, sut.data.size)

        // Act
        sut.data = listOf(
            Samples.sampleApiImage().toImage(), Samples.sampleApiImage().toImage().copy(id = 2L)
        )

        // Assert
        Assert.assertEquals(2, sut.data.size)
    }

    @Test
    fun `itemCount() returns the number of saved data`() {
        // Act
        sut.data = listOf(
            Samples.sampleApiImage().toImage(), Samples.sampleApiImage().toImage().copy(id = 2L)
        )

        // Assert
        Assert.assertEquals(2, sut.itemCount)
    }

    @Test
    fun `If no data is assigned returns zero`() {
        // Assert
        Assert.assertEquals(0, sut.itemCount)
    }
}
