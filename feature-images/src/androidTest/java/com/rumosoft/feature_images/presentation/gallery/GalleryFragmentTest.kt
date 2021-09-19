package com.rumosoft.feature_images.presentation.gallery

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
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
import org.hamcrest.Matcher
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
        val recyclerView = onView(
            withId(R.id.recycler)
        )

        recyclerView
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(4, scrollTo()))
            .check(matches(hasDescendant(withId(R.id.itemEditIcon))))
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

    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController?, view: View) {
                val v: View = view.findViewById(id)
                v.performClick()
            }
        }
    }
}
