package com.airy.wheneverlight.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.apdater.WeiboListViewAdapter;
import com.airy.wheneverlight.api.WeiboApi;
import com.airy.wheneverlight.api.WeiboFactory;
import com.airy.wheneverlight.db.FriendsTimeLine;
import com.airy.wheneverlight.db.Status;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Airy on 2017/7/6.
 */

public class HomePageFragment extends Fragment {

    private WeiboListViewAdapter adapter;
    private RecyclerView contentList;
    private SwipeRefreshLayout swipeRefresh;
    private Oauth2AccessToken token;
    private List<Status> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        contentList = (RecyclerView) view.findViewById(R.id.content_list);
        contentList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WeiboListViewAdapter(list);
        contentList.setAdapter(adapter);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.content_refresh);
        swipeRefresh.setOnRefreshListener(() -> {
            getWeiboTimeLine();
            swipeRefresh.setRefreshing(false);
        });
        //
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.send_weibo_float);
        floatingActionButton.setOnClickListener(v ->{
            Toast.makeText(getActivity(),"不能发微博",Toast.LENGTH_SHORT).show();
        });
        getWeiboTimeLine();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("home_frg","init");

    }

    private void getWeiboTimeLine(){
        Log.d("getwbl","init");
        token = readToken(getContext());
        if (token.isSessionValid()){
            Log.d("Home_frg","token true");
            String tkstr = token.getToken();
            //String count ="50";
            WeiboApi weiboApi = WeiboFactory.getWeiBoApiSingleton();
            weiboApi.getFriendTimeLine(getRequestMap(tkstr))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(friendsTimeLine -> {
                       displayWeiboList(friendsTimeLine);
                    });

        }else{
            Log.d("Home_frg","token false");
        }

    }

    private Map<String,Object> getRequestMap(String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        return map;
    }

    private Oauth2AccessToken readToken(Context context) {
        return AccessTokenKeeper.readAccessToken(context);
    }

    public void displayWeiboList(FriendsTimeLine friendsTimeLine){
        list.clear();
        list.addAll(friendsTimeLine.getStatuses());
        adapter.notifyDataSetChanged();
    }
}
