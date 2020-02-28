package com.example.marsplay;

import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class CustomMatchersList {
    public static TypeSafeMatcher<View> isTextInLines(final int lines) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return ((TextView) item).getMaxLines() == lines;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("isTextInLines");
            }
        };
    }
}
