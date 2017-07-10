package com.airy.wheneverlight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.airy.wheneverlight.data.Constants;
import com.airy.wheneverlight.ui.activity.HomePageActivity;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class MainActivity extends AppCompatActivity {

    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        WbSdk.install(this,new AuthInfo(this, Constants.APP_KEY,Constants.REDIRECT_URL,Constants.SCOPE));
        mSsoHandler = new SsoHandler(MainActivity.this);

        //
        findViewById(R.id.login_button_web)
                .setOnClickListener(v -> mSsoHandler.authorizeWeb(new WLAuthListener()));

        findViewById(R.id.login_button_in_one)
                .setOnClickListener(v -> mSsoHandler.authorize(new WLAuthListener()));

        findViewById(R.id.homepage_button)
                .setOnClickListener(v -> startActivity(new Intent(MainActivity.this,HomePageActivity.class)));

        findViewById(R.id.get_token_button)
                .setOnClickListener(v -> {
                    Oauth2AccessToken token = readToken(MainActivity.this);
                    if (token.isSessionValid()){
                        Log.d("Main","token true");
                        System.out.println(token.getToken());
                    }else{
                        Log.d("Main","token false");
                    }
                });

    }

    private Oauth2AccessToken readToken(Context context) {
        return AccessTokenKeeper.readAccessToken(context);
    }

    private class WLAuthListener implements WbAuthListener{
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            MainActivity.this.runOnUiThread(() -> {
                mAccessToken = token;
                if (mAccessToken.isSessionValid()) {
                    // 显示 Token
                    System.out.println(token);
                    // 保存 Token 到 SharedPreferences
                    AccessTokenKeeper.writeAccessToken(MainActivity.this, mAccessToken);
                    Toast.makeText(MainActivity.this,
                            "授权成功", Toast.LENGTH_SHORT).show();
                }
            });

            startActivity(new Intent(MainActivity.this, HomePageActivity.class));
        }

        @Override
        public void cancel() {
            Toast.makeText(MainActivity.this,"你取消了授权",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
            Toast.makeText(MainActivity.this,"授权失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mSsoHandler!=null){
            mSsoHandler.authorizeCallBack(requestCode,resultCode,data);
        }
    }

}
