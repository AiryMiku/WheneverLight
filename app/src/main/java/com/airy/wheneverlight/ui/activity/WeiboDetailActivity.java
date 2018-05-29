package com.airy.wheneverlight.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.apdater.WeiboListViewAdapter;
import com.airy.wheneverlight.apdater.WeiboViewPageAdapter;
import com.airy.wheneverlight.bean.Status;
import com.airy.wheneverlight.contract.BaseActivityContract;
import com.airy.wheneverlight.util.StringUtil;
import com.airy.wheneverlight.view.NineGridLayout;
import com.airy.wheneverlight.view.SingleImage;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class WeiboDetailActivity extends AppCompatActivity implements BaseActivityContract{

    final static String TAG = "Airy";
    private CardView cardView;
    private CircleImageView titleImage;
    private TextView titleTx;
    private TextView titleTime;
    private TextView titleVia;
    private TextView contentTx;
    private View weiboItemView;
    private TabLayout weiboTabLayout;
    private Status mStatus;
    private WeiboViewPageAdapter adapter;
    private ViewPager mViewPager;
    private SingleImage oneImage;
    private NineGridLayout nineImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);

        initView();
        displayStatus();
    }

    @Override
    public void initView() {

        mStatus = (Status) getIntent().getSerializableExtra(WeiboListViewAdapter.TAG);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setTitle("");
        }
        titleImage = (CircleImageView) findViewById(R.id.wb_title_image);
        titleTx = (TextView) findViewById(R.id.wb_item_title);
        titleTime = (TextView) findViewById(R.id.wb_title_time);
        titleVia = (TextView) findViewById(R.id.wb_title_via);
        contentTx = (TextView) findViewById(R.id.wb_item_content);

        mViewPager = (ViewPager) findViewById(R.id.weibo_detail_viewpage);

        weiboTabLayout = (TabLayout) findViewById(R.id.weibo_detail_tablayout);
        weiboTabLayout.addTab(weiboTabLayout.newTab().setText("转发"));
        weiboTabLayout.addTab(weiboTabLayout.newTab().setText("评论"));
        weiboTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //weiboTabLayout.addOnTabSelectedListener();
        System.out.println(mStatus);
        adapter = new WeiboViewPageAdapter(getSupportFragmentManager(),mStatus.getIdstr());
        mViewPager.setAdapter(adapter);
        weiboTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.repost:
                //
                break;
            case R.id.comment:
                Intent intent = new Intent(this,RepostAndCommentActivity.class);
                intent.putExtra(TAG,mStatus.getIdstr());
                startActivity(intent);
                break;

        }
        return true;
    }

    public void displayStatus(){
        titleTx.setText(mStatus.getUser().getName());
        contentTx.setText(mStatus.getText());
        titleTime.setText(mStatus.getCreated_at().substring(0,19));
        titleVia.setText(StringUtil.getTail(mStatus.getSource()));
        Glide.with(this).load(mStatus.getUser().getAvatar_large()).into(titleImage);
    }

}
