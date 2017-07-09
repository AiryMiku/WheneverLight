package com.airy.wheneverlight.api;

import com.airy.wheneverlight.db.HomeTimeLine;
import com.airy.wheneverlight.db.MentionComment;
import com.airy.wheneverlight.db.Status;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * retrofit2.0 sinaweibo api
 *
 *
 * Created by Airy on 2017/7/5.
 */

public interface WeiboApi {

    @GET("statuses/show.json")
    Observable<Status> getStatus(@QueryMap Map<String,Object> params);

    @GET("statuses/home_timeline.json")
    Observable<HomeTimeLine> getHomeTimeLine(@QueryMap Map<String,Object> params);

    @GET("comments/mentions.json")
    Observable<MentionComment> getMessageAtComment(@QueryMap Map<String,Object> params);

    @GET("comments/to_me.json")
    Observable<MentionComment> getMessageGetComment(@QueryMap Map<String,Object> params);

    @GET("comments/by_me.json")
    Observable<MentionComment> getMessageSendComment(@QueryMap Map<String,Object> params);


}
