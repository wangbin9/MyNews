package com.feicui.edu.highpart;


import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.feicui.edu.highpart.base.MyBaseActivity;

/**Logo界面***/
public class ActivityLogo extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        ImageView logo = (ImageView) findViewById(R.id.logo_iv);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            //动画启动时调用
            @Override
            public void onAnimationStart(Animation animation) {

            }
            //动画结束时调用
            @Override
            public void onAnimationEnd(Animation animation) {
                openActivity(MainActivity.class);
                ActivityLogo.this.finish();

            }
            //动画重复时调用
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logo.setAnimation(animation);
    }
}
