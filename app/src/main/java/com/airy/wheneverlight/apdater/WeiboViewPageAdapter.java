package com.airy.wheneverlight.apdater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.airy.wheneverlight.ui.fragment.WeiboDetailViewPageFragment;

/**
 * 评论列表所用的fragmentAdapter
 *
 * Created by Airy on 2017/7/14.
 */

public class WeiboViewPageAdapter extends FragmentPagerAdapter {

    String[] tabTitleAarry={"转发","评论"};
    String weiboId;

    public WeiboViewPageAdapter(FragmentManager fragmentManager,String weiboId){
        super(fragmentManager);
        this.weiboId = weiboId;
        this.tabTitleAarry = tabTitleAarry;
    }

    @Override
    public Fragment getItem(int position) {
        int params;
        switch (position){
            case 0:
                params = WeiboDetailViewPageFragment.REPOST_PAGE;
                break;
            case 1:
                params = WeiboDetailViewPageFragment.COMMENT_PAGE;
                break;
            default:
                params = WeiboDetailViewPageFragment.COMMENT_PAGE;//默认评论
                break;
        }
        return WeiboDetailViewPageFragment.newInstance(params,weiboId);
    }

    @Override
    public int getCount() {
        return tabTitleAarry.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleAarry[position];
    }
}
