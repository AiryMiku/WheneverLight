package com.airy.wheneverlight.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Airy on 2017/7/5.
 */

public class WeiboFactory {


    static WeiboApi weiBoApiSingleton = null;


    //return Singleton
    public static WeiboApi getWeiBoApiSingleton() {
            if (weiBoApiSingleton == null) {
                weiBoApiSingleton = new weiboApiRetrofit().getWeiboApiService();
            }
            return weiBoApiSingleton;
    }

    static class weiboApiRetrofit{

        private WeiboApi weiboApiService;
        static final String BASE_URL = "https://api.weibo.com/2/";

        public WeiboApi getWeiboApiService(){
            return weiboApiService;
        }



        weiboApiRetrofit() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            weiboApiService = retrofit.create(WeiboApi.class);

        }

    }

}
