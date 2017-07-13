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

public class CommentFragment extends Fragment implements BaseFragmentContract {

    final static String COMMENT_TAG = "comment";
    final static int COMMENT_TIME_LINE = 1;
    final static int COMMENT_TO_ME = 2;
    final static int COMMENT_MENTION = 3;
    final static int COMMENT_BY_ME = 4;

    private int currentCommentType;
    private CommentListViewAdapter adapter;
    private RecyclerView contentList;
    private SwipeRefreshLayout swipeRefresh;
    private Oauth2AccessToken token;
    private List<Comments> list = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //反射构造一个commentFragment实例
    public static CommentFragment newInstance(int commentType){
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COMMENT_TAG,commentType);
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    private void getComment(int type){
        swipeRefresh.setRefreshing(true);
        token = Oauth2Util.readToken(getContext());
        if(token.isSessionValid()){
            Log.d("CommentTimeLine","token true");
            String tkstr = token.getToken();
            WeiboApi weiboapi = WeiboFactory.getWeiBoApiSingleton();
            switch (type){
                case COMMENT_TIME_LINE :
                    weiboapi.getMessageCommentTimeLine(getRequestMap(tkstr))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::displayCommentList,this::onError);
                    break;
                case COMMENT_TO_ME :
                    weiboapi.getMessageGetComment(getRequestMap(tkstr))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::displayCommentList,this::onError);
                    break;
                case COMMENT_BY_ME :
                    weiboapi.getMessageSendComment(getRequestMap(tkstr))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::displayCommentList,this::onError);
                    break;
                case COMMENT_MENTION :
                    weiboapi.getMessageAtComment(getRequestMap(tkstr))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::displayCommentList,this::onError);
                    break;
            }
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
        //处理commentType
        if(getArguments()!=null){
            currentCommentType = getArguments().getInt(COMMENT_TAG);
        }
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
            getComment(currentCommentType);
            swipeRefresh.setRefreshing(false);
        });
        getComment(currentCommentType);
        return view;
    }
}
