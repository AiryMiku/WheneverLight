package com.airy.wheneverlight.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.airy.wheneverlight.R;
import com.airy.wheneverlight.api.WeiboApi;
import com.airy.wheneverlight.api.WeiboFactory;
import com.airy.wheneverlight.contract.BaseActivityContract;
import com.airy.wheneverlight.util.Oauth2Util;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SendWeiboActivity extends AppCompatActivity implements BaseActivityContract{

    private EditText statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_weibo);
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
        }
        statusText = (EditText) findViewById(R.id.send_weibo_edit_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_weibo_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.send:
                sendWeibo(statusText.getText().toString()+"http://android.com.cn");
                //安全域名是自己的Android下载地址，而且要和自己所填的安全域名相同。。。
                break;
        }
        return true;
    }

    private Map<String,Object> getRequestMap(String token, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("status",status);
        return map;
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this,R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    public void sendWeibo(String statusText){
        Oauth2AccessToken token = Oauth2Util.readToken(this);
        if (token.isSessionValid()){
            Log.d("Home_frg","token true");
            String tkstr = token.getToken();
            WeiboApi weiboApi = WeiboFactory.getWeiBoApiSingleton();
            weiboApi.sendWeiboWithText(getRequestMap(tkstr,statusText))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(status -> finshAndNotify(),this::loadError);
        }else{
            Log.d("Home_frg","token false");
        }
    }

    public void finshAndNotify(){
        finish();
        Toast.makeText(this,R.string.load_success,Toast.LENGTH_SHORT).show();
    }

}
