package com.github.bubinimara.movies.app.debug;

/**
 * Created by davide.
 */
public class ThreadUtils {
    public static void Sleep(long mill){
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
