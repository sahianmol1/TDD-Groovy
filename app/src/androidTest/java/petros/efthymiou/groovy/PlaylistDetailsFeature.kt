package petros.efthymiou.groovy

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaylistDetailsFeature {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun displaysPlayListNameAndDetails() {
        Thread.sleep(4000)
        onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.img_playlist),
                ViewMatchers.isDescendantOfA(nthChildOf(ViewMatchers.withId(R.id.rv_playList), 0))
            )
        ).perform(ViewActions.click())

        onView(withText("Hard Rock Cafe"))

        onView(withText("Rock your senses with this timeless signature vibe list. \\n\\n • Poison \\n • You shook me all night \\n • Zombie \\n • Rock'n Me \\n • Thunderstruck \\n • I Hate Myself for Loving you \\n • Crazy \\n • Knockin' on Heavens Door"))
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }

}