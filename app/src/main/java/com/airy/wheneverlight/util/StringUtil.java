package com.airy.wheneverlight.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
        if (start == -1) {
            return null;
        }
        int end = source.indexOf("</a>",start);
        return source.substring(start+1,end);
    }

    /*
    * 格式化时间
    *
    * */

    public static String FormatDate(String rawDate){
        SimpleDateFormat format1 = new SimpleDateFormat(
                "EEE MMM d HH:mm:ss Z yyyy", Locale.CHINA);

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy MM dd hh:mm:ss");
        try {
            return formatDate.format(format1.parse(rawDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.getTail("<a href=\"http://app.weibo.com/t/feed/6odKO1\" rel=\"nofollow\">iPhone X</a>"));
    }

}
