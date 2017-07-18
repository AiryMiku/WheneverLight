package com.airy.wheneverlight.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;

/**
 * Created by Airy on 2017/7/15.
 */

public class SingleImage extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener{

    private String url;
    private boolean isAttachedToWindow;

    public SingleImage(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public SingleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public SingleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    public void onAttachedToWindow() {
        isAttachedToWindow = true;
        setImageUrl(url);
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setImageUrl(String url) {

//        int width = ScreenUtil.instance(getContext()).getScreenWidth();
//        int height = width/3;

        if (!TextUtils.isEmpty(url)) {
            this.url = url;
            if (isAttachedToWindow) {
                Glide.with(getContext().getApplicationContext()).load(url).into(this);
            }
        }
    }


//    //打开PicActivity
//    private void startPictureActivity(SingleImage image) {
//        Intent intent = PicActivity.newIntent(getContext(), image.url,null,0);
//        //异常处理
//        getContext().startActivity(intent);
//    }

    @Override
    public void onClick(View v) {
//        startPictureActivity(this);
    }


}
