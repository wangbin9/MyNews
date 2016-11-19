package com.feicui.edu.highpart.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feicui.edu.highpart.MainActivity;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.adapter.BigNewsAdapter;
import com.feicui.edu.highpart.asyntask.HttpUtil;
import com.feicui.edu.highpart.bean.BaseEntity;
import com.feicui.edu.highpart.bean.BigNews;
import com.feicui.edu.highpart.bean.NewsGroup;
import com.feicui.edu.highpart.common.CommonUtil;
import com.feicui.edu.highpart.common.Const;
import com.feicui.edu.highpart.common.OkHttpUtil;
import com.feicui.edu.highpart.common.UrlComposeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class PicFragment extends Fragment {

    private static final String TAG ="PicFragment" ;
    private List<String> titles = new ArrayList<>();//标题名
    private List<Integer> subids = new ArrayList<>();//新闻的ID




    @Bind(R.id.favorite_toolbar)
    Toolbar favoriteToolbar;
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
//        BigNewsAdapter adapter=new BigNewsAdapter(getActivity(),bigNewses);

        //加载数据
        asyncLoadData();
        return view;
    }

    private void asyncLoadData() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                //知道新闻的编号
                parseNewsGroupJson();
                //url  拼接  知道某个新闻的编号
                Map<String,String> map=new HashMap<String, String>();
                map.put("ver", CommonUtil.getVersionCode(getContext())+"");
                map.put("nid",2+"");
                String url= UrlComposeUtil.getUrlPath(Const.URL_BIG_PIC,map);
                String s=OkHttpUtil.getString(url);
                if (s!=null&&s.contains("OK")){
                    //成功，解析数据
                    List<BigNews> bigNewses = pareseBigNews(s);
                    //将数据添加到适配器，刷新适配器。必须在主线程中进行
                    //把数据显示在rv中
                    setDataTORV(bigNewses);


                    Log.d(TAG,"解析大图数据:"+bigNewses);
                }else {
                    //失败
                    Toast.makeText(getContext(), "解析大图失败", Toast.LENGTH_SHORT).show();
                }

            }
        }.start();
    }

    private void setDataTORV(final List<BigNews> bigNewses) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //必须在主线程中运行
                BigNewsAdapter adapter=new BigNewsAdapter(getActivity(),bigNewses);
                favoriteRv.setLayoutManager(new LinearLayoutManager(getContext()));
                favoriteRv.setAdapter(adapter);
            }
        });
    }

    private List<BigNews> pareseBigNews(String s) {

        Gson g=new Gson();
        Type t=new TypeToken<BaseEntity<List<BigNews>>>(){}.getType();
        BaseEntity entity = g.fromJson(s, t);
        return (List<BigNews>) entity.getData();
    }

  /*  class LoadNewsGroupTask extends AsyncTask<String,Void,String >{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }*/


    //解析json数据
    public void parseNewsGroupJson() {
        String url = "http://118.244.212.82:9092/newsClient/news_sort?ver=1&imei=1";
        String data = HttpUtil.getJsonString(url);
        if (data == null) {
            Toast.makeText(getContext(), "解析大图数据失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "onCreate: " + data);
        Gson gson = new Gson();
        Type type = new TypeToken<NewsGroup<List<NewsGroup.DataBean<List<NewsGroup.DataBean.SubgrpBean>>>>>() {
        }.getType();

        NewsGroup newsGroup = gson.fromJson(data, type);
        Log.d(TAG, "parseNewsGroupJsonString: " + newsGroup.getMessage());
        List<NewsGroup.DataBean> data1 = (List<NewsGroup.DataBean>) newsGroup.getData();
        for (NewsGroup.DataBean dataBean : data1) {
            String group = dataBean.getGroup();
            Log.d(TAG, "group: " + group);
            List<NewsGroup.DataBean.SubgrpBean> subgrp = (List<NewsGroup.DataBean.SubgrpBean>) dataBean.getSubgrp();
            for (NewsGroup.DataBean.SubgrpBean subgrpBean : subgrp) {
                Log.d(TAG, "sub grp: "+subgrpBean.getSubid());
                titles.add(subgrpBean.getSubgroup());
                subids.add(subgrpBean.getSubid());
            }
        }

    }


    /**
     * 解除butter的绑定
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
