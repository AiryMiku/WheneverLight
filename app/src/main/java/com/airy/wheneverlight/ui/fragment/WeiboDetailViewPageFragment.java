package com.airy.wheneverlight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.apdater.ViewPageDetailListViewAdapter;
import com.airy.wheneverlight.api.WeiboApi;
import com.airy.wheneverlight.api.WeiboFactory;
import com.airy.wheneverlight.bean.Comments;
import com.airy.wheneverlight.bean.MentionComment;
import com.airy.wheneverlight.contract.BaseFragmentContract;
import com.airy.wheneverlight.util.Oauth2Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Airy on 2017/7/14.
 */

public class WeiboDetailViewPageFragment extends Fragment implements BaseFragmentContract{

    private static final String PAGE_TAG = "PAGE_TYPE";
    private static final String STATUS_ID_TAG = "STATUS_ID_TAG";
    public static final int REPOST_PAGE = 1;
    public static final int COMMENT_PAGE = 2;
    private RecyclerView viewPageList;
    private ViewPageDetailListViewAdapter adapter;
    private String weiboId;
    private int currentType;
    private List<Comments> list = new ArrayList<>();

    public static WeiboDetailViewPageFragment newInstance(int type,String id){
        Bundle args = new Bundle();
        args.putInt(PAGE_TAG,type);
        args.putString(STATUS_ID_TAG,id);
        WeiboDetailViewPageFragment weiboDetailViewPageFragment = new WeiboDetailViewPageFragment();
        weiboDetailViewPageFragment.setArguments(args);
        return weiboDetailViewPageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weibo_detail_view_page,container,false);
        if(getArguments()!=null){
            currentType = getArguments().getInt(PAGE_TAG);
            weiboId = getArguments().getString(STATUS_ID_TAG);
        }
        return initView(view);
    }

    @Override
    public View initView(View view) {
        viewPageList = (RecyclerView) view.findViewById(R.id.view_page_list);
        viewPageList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ViewPageDetailListViewAdapter(getActivity(),list);
        viewPageList.setAdapter(adapter);
        getCommentOrRepost(1);
        return view;
    }

    private Map<String,Object> getRequestMap(String token, String count,String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("count",count);
        map.put("id",id);
        return map;
    }

    public void getCommentOrRepost(int type){
        //新浪不开放转发接口
        String tkstr = Oauth2Util.readToken(getContext()).getToken();
        WeiboApi weiboApi = WeiboFactory.getWeiBoApiSingleton();
        weiboApi.getMessageCommnetById(getRequestMap(tkstr,"50",weiboId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayViewPage,this::onError);

    }

    public void onError(Throwable throwable){
        throwable.printStackTrace();
        Toast.makeText(getActivity(),R.string.load_error,Toast.LENGTH_SHORT).show();
    }

    public void displayViewPage(MentionComment mentionComment){
        list.clear();
        list.addAll(mentionComment.getComments());
        adapter.notifyDataSetChanged();
    }
}
