package com.github.bubinimara.movies.app.util;

import butterknife.Unbinder;

/**
 * Created by davide.
 */
public class ButterKnifeUtil {

    public static void safeUnbind(Unbinder unbinder) {
        try {
            if(unbinder!=null){
                unbinder.unbind();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
