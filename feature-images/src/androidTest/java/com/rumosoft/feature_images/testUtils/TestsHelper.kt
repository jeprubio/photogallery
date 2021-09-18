package com.rumosoft.feature_images.testUtils

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher

object TestsHelper {
    internal fun clickOnElement(recyclerView: ViewInteraction, position: Int) {
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                ViewActions.click()
            )
        )
    }

    internal fun scrollToElement(recyclerView: ViewInteraction, position: Int) {
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                ViewActions.scrollTo()
            )
        )
    }

    internal fun checkDisplayed(viewInteraction: ViewInteraction) {
        viewInteraction.check(ViewAssertions.matches(isDisplayed()))
    }

    internal fun goBack() {
        Espresso.pressBack()
    }

    class ScrollToBottomAction : ViewAction {
        override fun getDescription(): String {
            return "Scroll RecyclerView to bottom"
        }

        override fun getConstraints(): Matcher<View> {
            return allOf<View>(isAssignableFrom(RecyclerView::class.java), isDisplayed())
        }

        override fun perform(uiController: UiController?, view: View?) {
            val recyclerView = view as RecyclerView
            recyclerView.layoutManager?.smoothScrollToPosition(
                recyclerView, null, (recyclerView.adapter?.itemCount ?: 0)
            )
            Thread.sleep(500)
            uiController?.loopMainThreadUntilIdle()
        }
    }

    fun withMaxLines(maxLines: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with max lines: $maxLines")
            }

            override fun matchesSafely(textView: TextView): Boolean {
                return maxLines == textView.maxLines
            }
        }
    }

    fun recyclerViewSizeMatcher(matcherSize: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with list size: $matcherSize")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return matcherSize == recyclerView.adapter!!.itemCount
            }
        }
    }

    fun nthChildOf(parentMatcher: Matcher<View?>, childPosition: Int): Matcher<View?> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with $childPosition child view of type parentMatcher")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) {
                    return parentMatcher.matches(view.parent)
                }
                val group = view.parent as ViewGroup
                return parentMatcher.matches(view.parent) && group.getChildAt(childPosition) == view
            }
        }
    }

    fun getRecyclerElement(
        @IdRes idElement: Int,
        @IdRes idRecycler: Int,
        position: Int
    ): ViewInteraction {
        return Espresso.onView(
            allOf(
                ViewMatchers.withId(idElement),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(ViewMatchers.withId(idRecycler), position)
                )
            )
        )
    }
}
