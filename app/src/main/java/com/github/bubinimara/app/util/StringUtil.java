package com.github.bubinimara.app.util;

/**
 * Created by davide.
 */
public class StringUtil {

    public static boolean areNotEquals(String str1,String str2){
        return !areEquals(str1,str2);
    }
    public static boolean areEquals(String str1,String str2){
        if(str1 == null){
            return str2==null;
        }else if (str2==null){
            return false;
        }

        return str1.trim().equalsIgnoreCase(str2.trim());
    }
}
