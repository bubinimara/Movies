package com.github.bubinimara.movies.app.robo;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by davide.
 */
public class ActivityAssertions {
    public static void assertActivityStarted(Fragment fragment, Class<? extends Activity> clazz) {
        ShadowActivity shadowActivity = shadowOf(fragment.getActivity());
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertThat(shadowIntent.getIntentClass().getName(),
                equalTo(clazz.getName()));
    }
}
