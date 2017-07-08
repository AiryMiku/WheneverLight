package com.airy.wheneverlight.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 该类定义了微博user的集合
 *
 *
 * Created by Airy on 2017/7/4.
 */

public class User extends DataSupport implements Serializable{


    private String id;
    private String idstr;
    private String screen_name;
    private String name;
    private String location;
    private String description;
    private String profile_image_url;
    private String profile_url;
    private String gender;
    private int followers_count;
    private int friends_count;
    private int statuses_count;
    private int favourites_count;
    private boolean verified;
    private String avatar_large;
    private String avatar_hd;
    private String cover_image_phone;

    public String getCover_image_phone() {
        return cover_image_phone;
    }

    public String getId() {
        return id;
    }

    public String getIdstr() {
        return idstr;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public String getGender() {
        return gender;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public int getStatuses_count() {
        return statuses_count;
    }

    public int getFavourites_count() {
        return favourites_count;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public String getVerified_reason() {
        return verified_reason;
    }

    private String verified_reason;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", idstr='" + idstr + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", profile_image_url='" + profile_image_url + '\'' +
                ", profile_url='" + profile_url + '\'' +
                ", gender='" + gender + '\'' +
                ", followers_count=" + followers_count +
                ", friends_count=" + friends_count +
                ", statuses_count=" + statuses_count +
                ", favourites_count=" + favourites_count +
                ", verified=" + verified +
                ", avatar_large='" + avatar_large + '\'' +
                ", avatar_hd='" + avatar_hd + '\'' +
                ", verified_reason='" + verified_reason + '\'' +
                '}';
    }

}
