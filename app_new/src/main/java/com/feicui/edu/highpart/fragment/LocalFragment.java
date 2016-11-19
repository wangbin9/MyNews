package com.feicui.edu.highpart.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicui.edu.highpart.MainActivity;
import com.feicui.edu.highpart.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class LocalFragment extends Fragment {

    @Bind(R.id.favorite_toolbar)
    Toolbar favoriteToolbar;
    @Bind(R.id.news_appbar)
    AppBarLayout newsAppbar;
    @Bind(R.id.favorite_rv)
    RecyclerView favoriteRv;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当activity创建的时候，回调
        if (getActivity() instanceof MainActivity) {
            final MainActivity activity = (MainActivity) getActivity();
            activity.setSupportActionBar(favoriteToolbar);
            activity.backToMainActivity(favoriteToolbar);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, null);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
