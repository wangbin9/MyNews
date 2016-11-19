package com.feicui.edu.highpart.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.feicui.edu.highpart.R;


/**
 * Created by Administrator on 2016/8/31 0031.
 */
public   class MyBaseActivity extends AppCompatActivity {

    private static final String TAG = "MyBaseActivity";
    protected int screen_w, screen_h;

    public Toolbar toolbar;
    public ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        screen_w = getWindowManager().getDefaultDisplay().getWidth();
        screen_h = getWindowManager().getDefaultDisplay().getHeight();

    }


    /**
     * Toolar 调用
     */
    public void initToolar(){

        //      初始化toolbar
        toolbar= (Toolbar) findViewById(R.id.toolBar);
        //设置支持toolar
        setSupportActionBar(toolbar);
        //支持图标导航
        actionBar = getSupportActionBar();
        //关闭当前界面 回到上层界面
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * V7包toolbar回调方法
     */
    @Override
    public boolean onSupportNavigateUp() {
        //结束当前Activity生命周期
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 父类 activity用来调试打印 activity生命周期和节目的进入和退出动画
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, getClass().getSimpleName() + "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //通过 class名字进行界面跳转并带 Bundle，Uri数据
    public void openActivity(Class<?> pClass, Bundle bundle, Uri uri) {
        Intent intent = new Intent(this, pClass);
        if (bundle != null)
            intent.putExtras(bundle);
        if (uri != null)
            intent.setData(uri);
        startActivity(intent);
    }

    //通过 class名字进行界面跳转只带 Bundle数据
    public void openActivity(Class<?> pClass, Bundle bundle) {
        openActivity(pClass, bundle, null);
    }

    //通过 class名字进行界面跳转
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null, null);
    }

    //通过 action字符串进行界面跳转
    public void openActivity(String action) {
        openActivity(action, null, null);
    }

    //通过 action字符串进行界面跳转，只带 Bundle数据
    public void openActivity(String action, Bundle bundle) {
        openActivity(action, bundle, null);
    }

    //通过 action字符串进行界面跳转，并带 Bundle和 Url数据
    public void openActivity(String action, Bundle bundle, Uri uri) {
        Intent intent = new Intent(action);
        if (bundle != null)
            intent.putExtras(bundle);
        if (uri != null)
            intent.setData(uri);
        startActivity(intent);
    }
    /**
     * 公共功能封装
     */
    private Toast toast;
    public void showToast(int resId){
        showToast(getString(resId));
    }

    public void showToast(String msg) {
        if (toast == null)
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
    public void myFinish() {
        super.finish();
//设置切换的动画，两个动画在 res/anim中定义
        overridePendingTransition(R.anim.anim_activity_bottom_in,
                R.anim.anim_activity_right_out);
    }

 /*   *//**显示一个等待对话框
     * @param context
     *            上下文环境
     * @param msg
     *            消息
     * @param cancelable
     *            是否可取消
     * @return返回 Dialog这个对象
     *//*
    public void showLoadingDialog(Context context, String msg, boolean cancelable) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // 得到加载 view
        View v = inflater.inflate(R.layout.dialog_loading, null);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
//自定义图片
// 提示文字
// 设定图片的动画，并加载
        Animation animation = AnimationUtils.loadAnimation(context,
                R.anim.loading_animation);
// 使用 ImageView显示动画
        if(null != msg) {
// 设置加载信息
            文本.setText(msg);
        }
        // 创建 dialog样式
// 不可以用“返回键”取消
        dialog.setCancelable(cancelable);
// 设置布局
        dialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
//显示 dialog
        dialog.show();
    }*/
}


