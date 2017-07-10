package com.airy.wheneverlight.bean;

import java.util.List;

/**
 * Created by Airy on 2017/7/6.
 */

public class HomeTimeLine {


    private long id;
    private String since_id;
    private String max_id;
    private List<Status> statuses;

    public List<Status> getStatuses() {
        return statuses;
    }

    public String getSince_id() {
        return since_id;
    }

    public String getMax_id() {
        return max_id;
    }

    @Override
    public String toString() {
        return "FriendsTimeLine{" +
                "since_id='" + since_id + '\'' +
                ", max_id='" + max_id + '\'' +
                ", statuses=" + statuses +
                '}';
    }



    /*
    * **
     * statuses : ["3382905382185354","3382905252160340","3382905235630562"]
     * total_number : 16
     *

    private int total_number;
    private List<String> statuses;

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
    */


}
