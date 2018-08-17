package com.airy.wheneverlight.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.apdater.WeiboListViewAdapter;
import com.airy.wheneverlight.api.WeiboApi;
import com.airy.wheneverlight.api.WeiboFactory;
import com.airy.wheneverlight.bean.HomeTimeLine;
import com.airy.wheneverlight.bean.Status;
import com.airy.wheneverlight.contract.BaseFragmentContract;
import com.airy.wheneverlight.ui.activity.SendWeiboActivity;
import com.airy.wheneverlight.util.Oauth2Util;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Airy on 2017/7/6.
 */

public class HomePageFragment extends Fragment implements BaseFragmentContract{

    final static String COMMENT_TAG = "comment";
    final static int COMMENT_TIME_LINE_TAG = 1;
    final static int COMMENT_TO_ME_TAG = 2;
    final static int COMMENT_MENTION_TAG = 3;
    final static int COMMENT_BY_ME = 4;

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
        return initView(view);
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
        contentList.getAdapter().notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    public void onError(Throwable throwable){
        throwable.printStackTrace();
        Snackbar.make(floatingActionButton,R.string.load_error,Snackbar.LENGTH_SHORT).show();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public View initView(View view) {
        contentList = (RecyclerView) view.findViewById(R.id.home_page_content_list);
        contentList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WeiboListViewAdapter(getContext(),list);
        ScaleInAnimationAdapter scale = new ScaleInAnimationAdapter(adapter);
        scale.setDuration(500);
        scale.setFirstOnly(false);
        AlphaInAnimationAdapter alpha = new AlphaInAnimationAdapter(scale);
        alpha.setDuration(600);
        alpha.setFirstOnly(false);
//        alpha.setInterpolator(new OvershootInterpolator());
        contentList.setAdapter(alpha);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.home_page_content_refresh);
        //            swipeRefresh.setRefreshing(false);
        swipeRefresh.setOnRefreshListener(this::getWeiboTimeLine);
        //
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.send_weibo_float);
        floatingActionButton.setOnClickListener(v ->{
            startActivity(new Intent(getActivity(), SendWeiboActivity.class));
            //Toast.makeText(getActivity(),"发微博啦",Toast.LENGTH_SHORT).show();
        });
        getWeiboTimeLine();
        return view;
    }
}
