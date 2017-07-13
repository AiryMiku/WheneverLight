package com.airy.wheneverlight.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.contract.BaseActivityContract;

import de.hdodenhof.circleimageview.CircleImageView;

public class WeiboDetailActivity extends AppCompatActivity implements BaseActivityContract{

    private CardView cardView;
    private CircleImageView titleImage;
    private TextView titleTx;
    private TextView titleTime;
    private TextView titleVia;
    private TextView contentTx;
    private TextView repostTx;
    private TextView commentTx;
    private View weiboItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);
        initView();
    }

    @Override
    public void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setTitle("");
        }



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

                break;
            case R.id.comment:

                break;

        }
        return true;
    }

}
