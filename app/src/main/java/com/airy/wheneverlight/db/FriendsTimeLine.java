package com.airy.wheneverlight.db;

import java.util.List;

/**
 * Created by Airy on 2017/7/6.
 */

public class FriendsTimeLine {


    /**
     * statuses : ["3382905382185354","3382905252160340","3382905235630562"]
     * total_number : 16
     */

    private int total_number;
    private List<String> statuses;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }
}
