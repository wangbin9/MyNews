package com.feicui.edu.highpart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.feicui.edu.highpart.adapter.LeadImgAdapter;
import com.feicui.edu.highpart.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;


public class ActivityLead extends MyBaseActivity {
    private ViewPager viewPager;
    private ImageView[] points=new ImageView[4];//图片
    private LeadImgAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        points[0]=(ImageView) findViewById(R.id.lead_iv1);
        points[1]=(ImageView) findViewById(R.id.lead_iv2);
        points[2]=(ImageView) findViewById(R.id.lead_iv3);
        points[3]=(ImageView) findViewById(R.id.lead_iv4);
        setPoint(0);


        //初始化控件
        viewPager=(ViewPager) findViewById(R.id.lead_viewpager);
        /**设置每一个具体界面的样式*/
        List<View> viewList=new ArrayList<>();

        viewList.add(getLayoutInflater().inflate(R.layout.activity_lead_1, null));
        viewList.add(getLayoutInflater().inflate(R.layout.activity_lead_2, null));
        viewList.add(getLayoutInflater().inflate(R.layout.activity_lead_3, null));
        viewList.add(getLayoutInflater().inflate(R.layout.activity_lead_4, null));
        viewPager.setOnPageChangeListener(listener);
        //初始化适配器
        adapter=new LeadImgAdapter(viewList);
        //设置适配器
        viewPager.setAdapter(adapter);


    }
    /**设置滑动能够实现当滑动时改变底部小图标 */
    public void setPoint(int point) {
        for (int i = 0; i <points.length ; i++) {
            if (i==point){
                points[i].setAlpha(255);
            }else {
                points[i].setAlpha(100);
            }
        }
    }

    /**当界面切换后调用*/
    public ViewPager.OnPageChangeListener listener=new ViewPager.OnPageChangeListener() {
        /**界面切换时调用*/
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**当界面切换后调用*/
        @Override
        public void onPageSelected(int position) {
            setPoint(position);
            if (position>=3){
                openActivity(ActivityLogo.class);
                finish();
                SharedPreferences preferences=getSharedPreferences("runconfig",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("isFirstRun", false);
                editor.commit();
            }

        }

        /**滑动状态变化时调用*/
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
