package com.applek.happy.utils;

/**
 * Created by wang_gp on 2016/12/28.
 */

public class CommonUtil {
    public static String getString(String content){
        return content.replaceAll("&nbsp;","  ");
    }
}
