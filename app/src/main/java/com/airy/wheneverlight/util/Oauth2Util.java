package com.airy.wheneverlight.util;

import android.content.Context;

import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by Airy on 2017/7/8.
 */

public class Oauth2Util {

    public static Oauth2AccessToken readToken(Context context) {
        return AccessTokenKeeper.readAccessToken(context);
    }

}
