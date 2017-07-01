package com.brunovieira.morpheus.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MorpheusTest {

    @Rule
    public final ActivityTestRule<HomeActivity> rule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void canUseBuilderWithAllOptions() {
        onView(withId(R.id.home_fab)).perform(click());

        onView(withId(R.id.dialog_title))
                .check(matches(
                        allOf(
                                withText("Lorem ipsum dolor sit amet."),
                                isDisplayed()
                        )
                ));

        onView(withId(R.id.dialog_subtitle))
                .check(matches(
                        allOf(
                                withText("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..."),
                                isDisplayed()
                        )
                ));
    }
}
