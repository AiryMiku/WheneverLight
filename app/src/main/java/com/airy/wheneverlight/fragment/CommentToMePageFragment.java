package com.airy.wheneverlight.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airy.wheneverlight.contract.BaseFragmentContract;
import com.airy.wheneverlight.db.MentionComment;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Airy on 2017/7/8.
 */

public class CommentToMePageFragment extends Fragment implements BaseFragmentContract{


    private RecyclerView contentList;
    private SwipeRefreshLayout mSwipeRefresh;
    private Oauth2AccessToken token;
    private List<MentionComment> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View initView(View view) {

        return null;
    }
}
