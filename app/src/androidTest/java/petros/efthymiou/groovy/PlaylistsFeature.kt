package petros.efthymiou.groovy

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import petros.efthymiou.groovy.PlaylistsFeature.RecyclerViewMatchers.hasItemCount

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
        Thread.sleep(4000)

        onView(withId(R.id.rv_playList)).check(matches(hasItemCount(10)))

        onView(allOf(withId(R.id.tv_title), isDescendantOfA(nthChildOf(withId(R.id.rv_playList), 0))))
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.tv_playlist_category), isDescendantOfA(nthChildOf(withId(R.id.rv_playList), 0))))
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

//        onView(allOf(withId(R.id.img_playlist), isDescendantOfA(nthChildOf(withId(R.id.rv_playList), 0))))
//            .check(matches(withTagValue(equalTo(R.drawable.playlist))))
//            .check(matches(isDisplayed()))
    }

    @Test
    fun displayLoaderWhenTheAppIsFetchingTheData() {
        onView(withId(R.id.loader)).check(matches(isDisplayed()))
    }

    @Test
    fun hidesLoader() {
        Thread.sleep(4000)
        onView(withId(R.id.loader)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun displayRockImageForRockTypePlayLists(){

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

    object RecyclerViewMatchers {
        @JvmStatic
        fun hasItemCount(itemCount: Int): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(
                RecyclerView::class.java) {

                override fun describeTo(description: Description) {
                    description.appendText("has $itemCount items")
                }

                override fun matchesSafely(view: RecyclerView): Boolean {
                    return view.adapter!!.itemCount == itemCount
                }
            }
        }
    }

}