package com.rumosoft.feature_images.presentation.gallery

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.SmallTest
import com.rumosoft.feature_images.R
import com.rumosoft.feature_images.testUtils.TestsHelper.checkDisplayed
import com.rumosoft.feature_images.testUtils.TestsHelper.clickOnElement
import com.rumosoft.feature_images.testUtils.TestsHelper.goBack
import com.rumosoft.feature_images.testUtils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class GalleryFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testLaunchFragment() {
        launchFragmentInHiltContainer<GalleryFragment> {
        }
    }

    private fun clickRecyclerElement(recyclerView: ViewInteraction, position: Int) {
        // Recycler is shown
        checkDisplayed(recyclerView)

        // Click on first element
        clickOnElement(recyclerView, position)
    }

    private fun checkDetailsAndGoBack() {
        checkDetails()

        // We have moved, otherwise we could not press back
        goBack()
    }

    private fun checkDetails() {
        checkDisplayed(onView(withId(R.id.details_wrapper)))
    }
}
