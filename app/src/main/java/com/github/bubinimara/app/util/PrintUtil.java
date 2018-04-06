package com.github.bubinimara.app.util;

import android.util.Log;

import com.github.bubinimara.app.domain.Movie;
import com.github.bubinimara.app.domain.PageMovie;

/**
 * Created by davide.
 */

public class PrintUtil {
    public static void printToLog(String tag, PageMovie r, int position) {
        Log.d(tag, "printToLog: **** "+r.getMovies().size());
        for (int i=0;i<r.getMovies().size();i++
             ) {
            Movie m = r.getMovies().get(i);
            String match = "";
            if(i==position){
                match = " <- THIS IS";
            }
            Log.d(tag, "printToLog ["+i+"] : "+m.getTitle()+match);
        }
    }
}
