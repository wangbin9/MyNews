package com.feicui.edu.highpart.adapter;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class LeadImgAdapter extends PagerAdapter {

    private List<View> list;

    public LeadImgAdapter(List<View> list) {
        this.list = list;
    }

    /** 设置控件中显示界面的数量*/
    @Override
    public int getCount() {
        return list.size();
    }


    /** 判断是否由对象生成界面*/
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**销毁 position位置的界面*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    /** 初始化 position位置的界面*/
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position);
        container.addView(view);
        return view;

    }
}
