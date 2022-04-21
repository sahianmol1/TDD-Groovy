package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class PlaylistsFeature {

    @get:Rule val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun displayScreenTitle() {
        onView(withText("Playlists")).check(matches(isDisplayed()))
    }

    @Test
    fun displayListOfPlayLists() {

    }
}