package com.airy.wheneverlight.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.api.WeiboApi;
import com.airy.wheneverlight.api.WeiboFactory;
import com.airy.wheneverlight.bean.User;
import com.airy.wheneverlight.contract.BaseActivityContract;
import com.airy.wheneverlight.setting.SettingActivity;
import com.airy.wheneverlight.ui.fragment.CommentFragment;
import com.airy.wheneverlight.ui.fragment.HomePageFragment;
import com.airy.wheneverlight.util.Oauth2Util;
import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePageActivity extends AppCompatActivity implements BaseActivityContract{

    final static int COMMENT_TIME_LINE = 1;
    final static int COMMENT_TO_ME = 2;
    final static int COMMENT_MENTION = 3;
    final static int COMMENT_BY_ME = 4;

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private CircleImageView userIcon;
    private TextView userName;
    private Oauth2AccessToken token;
    private ImageView userBackground;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
        getCurrentUser();
    }

    private void getCurrentUser(){
        token = Oauth2Util.readToken(this);
        if (token.isSessionValid()) {
            String tkstr = token.getToken();
            String uId = token.getUid();
            WeiboApi w = WeiboFactory.getWeiBoApiSingleton();
            w.getUser(getRequestMap(tkstr,uId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::disPlayUserInfo,this::onError);

        }
    }

    public void disPlayUserInfo(User u){
        currentUser = u;
        //toolbar.setTitle(u.getName());
        userName.setText(u.getName());
        Glide.with(HomePageActivity.this).load(u.getAvatar_large()).into(userIcon);
        Glide.with(HomePageActivity.this).load(u.getCover_image_phone()).into(userBackground);
    }

    public void onError(Throwable throwable){
        throwable.printStackTrace();
        Snackbar.make(toolbar,R.string.load_error,Snackbar.LENGTH_SHORT).show();
    }

    private Map<String,Object> getRequestMap(String token, String uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("uid",uid);
        return map;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
//            case R.id.refresh:
//                Toast.makeText(this, "刷新", Toast.LENGTH_SHORT).show();
//                break;
        }
        return true;
    }

    @Override
    public void initView(){

        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_page_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //

        mNavigationView = (NavigationView) findViewById(R.id.nav);
        View navHeader = mNavigationView.getHeaderView(0);
        userBackground = (ImageView) navHeader.findViewById(R.id.user_background);
        userIcon = (CircleImageView) navHeader.findViewById(R.id.user_icon);
        userName = (TextView) navHeader.findViewById(R.id.user_name);
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_page_drawer:
                    replaceFragment(new HomePageFragment());
                    toolbar.setTitle(currentUser.getName());
                    break;
                case R.id.to_me_comment_drawer:
                    replaceCommentFragment(COMMENT_TO_ME);
                    toolbar.setTitle("收到的评论");
                    break;
                case R.id.by_me_comment_drawer:
                    replaceCommentFragment(COMMENT_BY_ME);
                    toolbar.setTitle("我发出的评论");
                    break;
                case R.id.at_comment_drawer:
                    replaceCommentFragment(COMMENT_MENTION);
                    toolbar.setTitle("@我的评论");
                    break;
                case R.id.comment_drawer:
                    replaceCommentFragment(COMMENT_TIME_LINE);
                    toolbar.setTitle("全部评论");
                    break;
                case R.id.at_weibo_drawer:
                    //
                    toolbar.setTitle("@我的微博");
                    break;
                case R.id.setting_drawer:
                    startActivity(new Intent(HomePageActivity.this, SettingActivity.class));
                    break;
            }
            item.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
        
        replaceFragment(new HomePageFragment());
        //mNavigationView.getMenu().getItem(R.id.home_page_drawer).setChecked(true);
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_fragment,fragment)
                .commit();
    }

    //区分CommentFragment
    private void replaceCommentFragment(int params){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_fragment,CommentFragment.newInstance(params))
                .commit();
    }

}
