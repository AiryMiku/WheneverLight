package com.airy.wheneverlight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.airy.wheneverlight.apdater.CommentListViewAdapter;
import com.airy.wheneverlight.api.WeiboApi;
import com.airy.wheneverlight.api.WeiboFactory;
import com.airy.wheneverlight.bean.Comments;
import com.airy.wheneverlight.bean.MentionComment;
import com.airy.wheneverlight.contract.BaseFragmentContract;
import com.airy.wheneverlight.util.Oauth2Util;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Airy on 2017/7/10.
 */

public class CommentTimeLineFragment extends Fragment implements BaseFragmentContract {

    private CommentListViewAdapter adapter;
    private RecyclerView contentList;
    private SwipeRefreshLayout swipeRefresh;
    private Oauth2AccessToken token;
    private List<Comments> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getCommentTimeLine(){
        swipeRefresh.setRefreshing(true);
        token = Oauth2Util.readToken(getContext());
        if(token.isSessionValid()){
            Log.d("CommentTimeLine","token true");
            String tkstr = token.getToken();
            WeiboApi weiboapi = WeiboFactory.getWeiBoApiSingleton();
            weiboapi.getMessageCommentTimeLine(getRequestMap(tkstr))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::displayCommentList,this::onError);
        }
    }

    private Map<String,Object> getRequestMap(String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("count","50");
        return map;
    }

    public void displayCommentList(MentionComment mentionComment){
        list.clear();
        list.addAll(mentionComment.getComments());
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    public void onError(Throwable throwable){
        throwable.printStackTrace();
        Toast.makeText(getActivity(),R.string.load_error,Toast.LENGTH_SHORT).show();
        swipeRefresh.setRefreshing(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment,container,false);
        return initView(view);
    }

    @Override
    public View initView(View view) {
        contentList = (RecyclerView) view.findViewById(R.id.comment_content_list);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.comment_content_refresh);
        contentList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommentListViewAdapter(getActivity(),list);
        contentList.setAdapter(adapter);
        swipeRefresh.setOnRefreshListener(()->{
            getCommentTimeLine();
            swipeRefresh.setRefreshing(false);
        });
        getCommentTimeLine();
        return view;
    }
}
