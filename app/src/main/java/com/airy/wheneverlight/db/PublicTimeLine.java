package com.airy.wheneverlight.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 该类定义了公共时间线的集合
 *
 * Created by Airy on 2017/7/4.
 */

public class PublicTimeLine extends DataSupport implements Serializable{

    private List<Status> statuses;
    private int previous_cursor;
    private int next_cursor;
    private int total_number;
    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
    public List<Status> getStatuses() {
        return statuses;
    }

    public void setPrevious_cursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }
    public int getPrevious_cursor() {
        return previous_cursor;
    }

    public void setNext_cursor(int next_cursor) {
        this.next_cursor = next_cursor;
    }
    public int getNext_cursor() {
        return next_cursor;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }
    public int getTotal_number() {
        return total_number;
    }

    @Override
    public String toString() {
        return "total_number "+total_number;
    }
}
