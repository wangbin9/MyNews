package com.feicui.edu.highpart.fragment;


import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feicui.edu.highpart.MainActivity;
import com.feicui.edu.highpart.MyApplication;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.adapter.MyRecycleViewAdapter;
import com.feicui.edu.highpart.bean.News;
import com.feicui.edu.highpart.biz.LocalNawsDBManager;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class FavoriteFragment extends Fragment {

    @Bind(R.id.favorite_toolbar)
    Toolbar favoriteToolbar;
    @Bind(R.id.favorite_rv)
    RecyclerView favoriteRv;
    @Bind(R.id.news_appbar)
    AppBarLayout newsAppbar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当activity创建的时候，回调
        if (getActivity() instanceof MainActivity) {
            final MainActivity activity = (MainActivity) getActivity();
            favoriteToolbar.setTitle("新闻收藏");
            activity.setSupportActionBar(favoriteToolbar);
            activity.backToMainActivity(favoriteToolbar);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, null);
        ButterKnife.bind(this, view);
        ArrayList<News> newss=new ArrayList<>();
        final FragmentActivity activity=getActivity();
        final MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(newss, getContext());
        favoriteRv.setLayoutManager(new LinearLayoutManager(activity));
        favoriteRv.setAdapter(adapter);
        new Thread(){
            @Override
            public void run() {
                super.run();
                FragmentActivity activity = getActivity();
                MyApplication application = (MyApplication)activity .getApplication();
                final ArrayList<News> newss = LocalNawsDBManager.query(application.sqLiteOP);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewses(newss);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
