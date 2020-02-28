package com.example.marsplay.activities;


import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.example.marsplay.ChildViewAction;
import com.example.marsplay.CustomMatchersList;
import com.example.marsplay.R;
import com.example.marsplay.RecyclerViewMatcher;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    /**
     * Recyclerview comes into view
     */
    @Test
    public void test_isRecyclerViewVisibleAtStart() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    /**
     * Select list item, check if description expands
     */
    @Test
    public void test_checkIfDescExpands() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withRecyclerView(R.id.recyclerView)
                .atPositionOnView(6, R.id.small_description))
                .check(matches(CustomMatchersList.isTextInLines(6)));
    }

    /**
     * Click on Read More, check if DetailActivity opens with same title
     */
    @Test
    public void test_checkIfDetailActivityCalledProperly() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,
                ChildViewAction.clickChildViewWithId(R.id.read_more_button)));
        onView(withId(R.id.article_heading)).check(matches(isDisplayed()));
        onView(withId(R.id.article_heading)).check(matches(withText("Stability of mRNA/DNA and DNA/DNA Duplexes Affects mRNA Transcription")));
        pressBack();
    }

    /**
     * nav to DetailActivity, press back
     */
    @Test
    public void test_checkBackNavToMain() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1,
                ChildViewAction.clickChildViewWithId(R.id.read_more_button)));
        pressBack();
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
