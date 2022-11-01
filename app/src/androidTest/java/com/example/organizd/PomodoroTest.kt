package com.example.organizd


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PomodoroTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun pOmodoroTest() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.myTimer), withContentDescription("Timer"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val materialButton = onView(
            allOf(
                withId(R.id.btnContdownStart), withText("Start"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.flFragment),
                        0
                    ),
                    13
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
        Thread.sleep(1505_000)
        val materialButton2 = onView(
            allOf(
                withId(R.id.btnContdownStart), withText("Start"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.flFragment),
                        0
                    ),
                    13
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        Thread.sleep(310_000)
        val textView = onView(
            allOf(
                withId(R.id.pomoDayContText), withText("Oggi hai completato: 1 \uD83C\uDF45"),
                withParent(withParent(withId(R.id.flFragment))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Oggi hai completato: 1 \uD83C\uDF45")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
