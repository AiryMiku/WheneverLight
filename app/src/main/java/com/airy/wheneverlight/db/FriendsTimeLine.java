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
}
