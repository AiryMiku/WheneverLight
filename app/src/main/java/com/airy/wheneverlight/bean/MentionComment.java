package com.airy.wheneverlight.bean;

import java.util.List;

/**
 * 获取到的评论
 *
 * Created by Airy on 2017/7/8.
 */

public class MentionComment {

    private List<Comments> comments;
    private String next_cursor;
    private String previous_cursor;
    private String total_number;

    public List<Comments> getComments() {
        return comments;
    }

    public String getNext_cursor() {
        return next_cursor;
    }

    public String getPrevious_cursor() {
        return previous_cursor;
    }

    public String getTotal_number() {
        return total_number;
    }

    @Override
    public String toString() {
        return "MentionComment{" +
                "comments=" + comments +
                ", next_cursor='" + next_cursor + '\'' +
                ", previous_cursor='" + previous_cursor + '\'' +
                ", total_number='" + total_number + '\'' +
                '}';
    }

}
