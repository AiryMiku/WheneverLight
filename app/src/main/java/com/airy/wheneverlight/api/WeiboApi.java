package com.airy.wheneverlight.api;

import com.airy.wheneverlight.db.FriendsTimeLine;
import com.airy.wheneverlight.db.PublicTimeLine;
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

    @GET("/statuses/public_timeline.json")
    Observable<PublicTimeLine> getPublicTimeLine(@QueryMap Map<String,String> params);

    @GET("/statuses/friends_timeline/ids.json")
    Observable<FriendsTimeLine> getFriendTimeLine(@QueryMap Map<String,String> params);

    @GET("statuses/show.json")
    Observable<Status> getStatus(@QueryMap Map<String,String> params);

}
