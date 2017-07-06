package com.airy.wheneverlight.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 该类定义了微博内容的集合
 *
 * Created by Airy on 2017/7/4.
 */

public class Status extends DataSupport implements Serializable{

    private String created_at;
    private int id;
    private String text;
    private String source;
    private boolean favorited;
    private boolean truncated;
    private String geo;
    private String mid;
    private int reposts_count;
    private int comments_count;
    private List<String> annotations;
    private User user;
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getCreated_at() {
        return created_at;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public String getSource() {
        return source;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
    public boolean getFavorited() {
        return favorited;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }
    public boolean getTruncated() {
        return truncated;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }
    public String getGeo() {
        return geo;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
    public String getMid() {
        return mid;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }
    public int getReposts_count() {
        return reposts_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }
    public int getComments_count() {
        return comments_count;
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }
    public List<String> getAnnotations() {
        return annotations;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "user "+user.getName()+"\n"
                +"text "+text;
    }

}
