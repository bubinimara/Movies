package com.github.bubinimara.movies.app.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

/**
 * Created by davide.
 */
public class ActivityTestUtil {
    ActivityTestUtil() {

    }

    /**
     * Change screen orientation
     */
    public static void rotateScreen(@NonNull Activity activity) {
        int orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }
        activity.setRequestedOrientation(orientation);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }
}
