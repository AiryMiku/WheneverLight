package com.airy.wheneverlight.api;

import com.airy.wheneverlight.bean.Comments;
import com.airy.wheneverlight.bean.HomeTimeLine;
import com.airy.wheneverlight.bean.MentionComment;
import com.airy.wheneverlight.bean.Status;
import com.airy.wheneverlight.bean.User;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * retrofit2.0 sinaweibo api
 *
 *
 * Created by Airy on 2017/7/5.
 */

public interface WeiboApi {

    @GET("users/show.json")
    Observable<User> getUser(@QueryMap Map<String,Object> params);

    @GET("statuses/show.json")
    Observable<Status> getStatus(@QueryMap Map<String,Object> params);

    @GET("statuses/home_timeline.json")
    Observable<HomeTimeLine> getHomeTimeLine(@QueryMap Map<String,Object> params);

    @GET("statuses/mentions.json")
    Observable<HomeTimeLine> getMessageAtWeiBo(@QueryMap Map<String,Object> params);

    @GET("comments/timeline.json")
    Observable<MentionComment> getMessageCommentTimeLine(@QueryMap Map<String,Object> params);

    @GET("comments/mentions.json")
    Observable<MentionComment> getMessageAtComment(@QueryMap Map<String,Object> params);

    @GET("comments/to_me.json")
    Observable<MentionComment> getMessageGetComment(@QueryMap Map<String,Object> params);

    @GET("comments/by_me.json")
    Observable<MentionComment> getMessageSendComment(@QueryMap Map<String,Object> params);

    @GET("comments/show.json")
    Observable<MentionComment> getMessageCommnetById(@QueryMap Map<String,Object> params);

    @FormUrlEncoded
    @POST("statuses/share.json")
    Observable<Status> sendWeiboWithText(@FieldMap Map<String,Object> params);

    @FormUrlEncoded
    @POST("comments/create.json")
    Observable<Comments> sendCommentToStatus(@FieldMap Map<String,Object> params);

}
