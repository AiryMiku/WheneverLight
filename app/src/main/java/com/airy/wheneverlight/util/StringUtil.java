package com.airy.wheneverlight.util;

/**
 * 处理字符串
 *
 * Created by Airy on 2017/7/9.
 */

public class StringUtil {

    /*
    * 截取来源
    *
    * */
    public static String getTail(String source){
        int start = source.indexOf(">");
        if (start == -1) return null;
        int end = source.indexOf("</a>",start);
        if (start == -1) return null;
        return source.substring(start+1,end);
    }


}
