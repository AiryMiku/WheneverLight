package com.airy.wheneverlight.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.airy.wheneverlight.api.WeiboApi;
import com.airy.wheneverlight.api.WeiboFactory;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Airy on 2017/7/6.
 */

public class HomePageFragment extends Fragment {


    private RecyclerView contentList;
    private LinearLayoutManager mLayoutManager;
    private Context mContext;
    private Oauth2AccessToken token;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("home_frg","init");
        getWeiboTimeLine();
    }

    private void getWeiboTimeLine(){
        Log.d("getwbl","init");
        token = readToken(getContext());
        if (token.isSessionValid()){
            String tkstr = token.getToken();
            String count ="50";
            WeiboApi weiboApi = WeiboFactory.getWeiBoApiSingleton();
            weiboApi.getPublicTimeLine(getRequestMap(tkstr,count))
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(publicTimeLine -> {
                        publicTimeLine.getStatuses().get(0).toString();
                    });
        }

    }

    private Map<String, String> getRequestMap(String token, String count) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", token);
        map.put("count", count);
        return map;
    }

    private Oauth2AccessToken readToken(Context context) {
        return AccessTokenKeeper.readAccessToken(context);
    }
}
