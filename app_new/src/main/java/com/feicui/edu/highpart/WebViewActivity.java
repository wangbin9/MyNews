package com.feicui.edu.highpart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;
import com.feicui.edu.highpart.base.MyBaseActivity;
import com.feicui.edu.highpart.bean.BaseEntity;
import com.feicui.edu.highpart.bean.Comment;
import com.feicui.edu.highpart.bean.News;
import com.feicui.edu.highpart.biz.LocalCommentDBManager;
import com.feicui.edu.highpart.biz.LocalNawsDBManager;
import com.feicui.edu.highpart.biz.LocalNewsSQLiteOP;
import com.feicui.edu.highpart.common.CommonUtil;
import com.feicui.edu.highpart.common.Const;
import com.feicui.edu.highpart.common.OkHttpUtil;
import com.feicui.edu.highpart.common.SharedPreferenceUtil;
import com.feicui.edu.highpart.common.UrlComposeUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class WebViewActivity extends MyBaseActivity {

    private WebView wbv;
    private EditText et_comment;
    private MenuItem item;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //Init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        et_comment = (EditText) findViewById(R.id.et_comment);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回箭头可用
        //给图片设置的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wbv = (WebView) findViewById(R.id.wbv);
        //设置一些webview的属性
        wbv.getSettings().setJavaScriptEnabled(true);//可以执行网页的javascript代码
//        wbv.getSettings().setSupportZoom(true);
//        wbv.getSettings().setBuiltInZoomControls(true);
        final Intent intent = getIntent();

        ShareSDK.initSDK(this,"sharesdk的appkey");

        if (intent != null) {
            news = (News) intent.getSerializableExtra("news");
             String url = news.getLink();
            //设置webview的客户端,
            wbv.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            wbv.loadUrl(url);
        }
        final int nid = news.getNid();
        //发送评论
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment(nid);
            }
        });

        //加载当前新闻评论的数量
        loadCommentCount(nid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_comment_count,menu);
        item = menu.findItem(R.id.menu_comment_count);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            showToast("2222222");
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.menu_comment_count:
                showToast("评论界面");
                //跳到展示评论页面
                Intent intent = new Intent(WebViewActivity.this, ShowCommentActivity.class);
                intent.putExtra("news", news);
                startActivity(intent);
                break;
            case R.id.menu_comment_share://分享
                showToast("分享新闻");
                showShare();

                break;
            case R.id.menu_comment_favorite://本地收藏
                showToast("本地收藏");
                //判断收藏是否存在
                MyApplication application= (MyApplication) getApplication();
                LocalNewsSQLiteOP localNewsSQLiteOP=application.sqLiteOP;
                String mag;
                if (LocalNawsDBManager.isExistNews(localNewsSQLiteOP,news.getNid())){
                    mag="亲 *.* 已经收藏了";
                }else {
                    long insert=LocalNawsDBManager.insert(localNewsSQLiteOP,news);
                    if(insert>0){
                        mag="收藏成功";
                    }else {
                        mag="收藏失败";
                    }
                }

                showToast(mag);
                break;

        }
//        if (itemId == R.id.menu_comment_count) {
//            //跳到展示评论页面
//            Intent intent = new Intent(WebViewActivity.this, ShowCommentActivity.class);
//            intent.putExtra("news", news);
//            startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }

    /*分享*/
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);

    }

    private void loadCommentCount(int nid) {
        //cmt_num?ver=版本号& nid=新闻编号
        Map<String, String> p = new HashMap<>();
        p.put("ver", CommonUtil.getVersionCode(this) + "");
        p.put("nid", nid+"");
        String urlPath = UrlComposeUtil.getUrlPath(Const.URL_USER_COMMENT_COUNT, p);
        new LoadCommentCountTask().execute(urlPath);
    }

    class LoadCommentCountTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String path = params[0];
            return OkHttpUtil.getString(path);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //解析json字符串
            Gson gson = new Gson();
            BaseEntity entity = gson.fromJson(s, BaseEntity.class);
            if (entity == null) {
                Toast.makeText(WebViewActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            } else {
                double count = (double) entity.getData();//评论数量
                item.setTitle("评论数"+(int)count);
            }

        }
    }

    private void sendComment(int nid) {

        String content = et_comment.getText().toString().trim();
        //cmt_commit?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容

        Map<String, String> p = new HashMap<>();
        p.put("ver", CommonUtil.getVersionCode(this) + "");
        p.put("nid", nid+"");
        p.put("token", SharedPreferenceUtil.getToken(this));
        p.put("imei", "8989"/*SystemUtils.getIMEI(this)*/);
        p.put("ctx", content);

        //发送评论给服务器,需要异步请求
        new UpLoadCommentTask().execute(p);
    }
    class UpLoadCommentTask extends AsyncTask< Map<String, String>, Void, String> {

        @Override
        protected String doInBackground(Map<String, String>... params) {
            try {
                return OkHttpUtil.postString(Const.URL_USER_COMMENT, params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Comment comment=new Comment();
                comment.setContent(et_comment.getText().toString());//评论内容
                comment.setStamp(CommonUtil.getDate());//评论时间
                comment.setUid("窝窝");
                comment.setPortrait("file///assets/ddd.jpg");
                LocalCommentDBManager.insert(WebViewActivity.this,comment);
                Toast.makeText(WebViewActivity.this, s, Toast.LENGTH_SHORT).show();
                wbv.requestFocus();//获取焦点
                et_comment.setText("");//清空评论
                showToast("成功");
            } else {
                Toast.makeText(WebViewActivity.this, s, Toast.LENGTH_SHORT).show();
                showToast("失败");
            }
            CommonUtil.hideKeyBoard(WebViewActivity.this);
        }
    }
}
