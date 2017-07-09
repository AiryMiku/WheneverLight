package com.airy.wheneverlight.fragment;

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
import com.airy.wheneverlight.contract.BaseFragmentContract;
import com.airy.wheneverlight.db.HomeTimeLine;
import com.airy.wheneverlight.db.Status;
import com.airy.wheneverlight.util.Oauth2Util;
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

public class HomePageFragment extends Fragment implements BaseFragmentContract{

    private FloatingActionButton floatingActionButton;
    private WeiboListViewAdapter adapter;
    private RecyclerView contentList;
    private SwipeRefreshLayout swipeRefresh;
    private Oauth2AccessToken token;
    private List<Status> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("home_frg","onCreate");
    }

    private void getWeiboTimeLine(){
        Log.d("getwbl","init");
        swipeRefresh.setRefreshing(true);
        token = Oauth2Util.readToken(getContext());
        if (token.isSessionValid()){
            Log.d("Home_frg","token true");
            String tkstr = token.getToken();
            String count ="50";
            WeiboApi weiboApi = WeiboFactory.getWeiBoApiSingleton();
            weiboApi.getHomeTimeLine(getRequestMap(tkstr,count))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::displayWeiboList,this::onError);

        }else{
            Log.d("Home_frg","token false");
        }

    }

    private Map<String,Object> getRequestMap(String token,String count) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("count",count);
        return map;
    }

    public void displayWeiboList(HomeTimeLine HomeTimeLine){
        list.clear();
        list.addAll(HomeTimeLine.getStatuses());
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    public void onError(Throwable throwable){
        throwable.printStackTrace();
        Toast.makeText(getActivity(),"网络开小差了？",Toast.LENGTH_SHORT).show();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public View initView(View view) {
        contentList = (RecyclerView) view.findViewById(R.id.home_page_content_list);
        contentList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WeiboListViewAdapter(list);
        contentList.setAdapter(adapter);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.home_page_content_refresh);
        swipeRefresh.setOnRefreshListener(() -> {
            getWeiboTimeLine();
            swipeRefresh.setRefreshing(false);
        });
        //
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.send_weibo_float);
        floatingActionButton.setOnClickListener(v ->{
            Toast.makeText(getActivity(),"不能发微博",Toast.LENGTH_SHORT).show();
        });
        getWeiboTimeLine();
        return view;
    }
}
