package com.feicui.edu.highpart.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
/*
* 提示不同的子类继承该基础适配器时，必须重写抽象方法 getMyView(),用来重新设置适
配对象的子条目样式。使用步骤如下：
1、自定义适配器，重写 getMyView()方法
class DemoAdapter extends MyBaseAdapter{
@override
public  View  getMyView(int  position,View  convertView,ViewGroup
parent){
……//设置适配对象子条目的样式
}
2、对适配对象设置适配器
DemoAdapter adapter = new DemoAdapter(Context对象);
适配对象.setAdapter(adapter); */

/**
 *所有父类的适配器
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    //上下文
    protected Context context;
    //定义布局过滤器
    protected LayoutInflater inflater;
    protected List<T> myList=new ArrayList<T>();//定义数据集合，并初始化

    public MyBaseAdapter(Context context){
    //初始化 context，inflate对象
        this.context=context;
        inflater= LayoutInflater.from(context);
    }
    //泛型集合
    protected ArrayList<T> list=new ArrayList<T>();
    /*略*/
    //清除所有数据
    public void clear(){
        myList.clear();
    }
    //查找所有数据
    public List<T> getAdapterData(){
        return list;
    }

    /**
     * 封装添加一条记录方法
     * t 一条数据的对象
     * isClearOld 是否清除原数据
     */
    public void appendData(T t,boolean isClearOld){
        if(t==null){ //非空验证
            return;
        }
        if(isClearOld){//如果 true 清空原数据
            list.clear();
        }//添加一条新数据到最后
        list.add(t);

    }
    /**
    * 添加多条记录
    * @param alist 数据集合
    * @param isClearOld 是否清空原数据
    */
    public void addendData(ArrayList<T> alist, boolean isClearOld){
        if(alist==null){
            return;
        }
        if(isClearOld){
            list.clear();
        }
        list.addAll(alist);
    }

    /**
     * 添加一条记录到第一条
     * @param t
     * @param isClearOld
     */
    public void appendDataTop(T t,boolean isClearOld){
        if(t==null){ //非空验证
            return;
        }
        if(isClearOld){//如果 true 清空原数据
            list.clear();
        }
        //添加一条新数据到第一条
        list.add(0,t);
    }
    /**
     * 添加多条记录到顶部
     * @param alist 数据集合
     * @param isClearOld 是否清空原数据
     */
    public void addendDataTop(ArrayList<T> alist, boolean isClearOld){
        if(alist==null){
            return;
        }
        if(isClearOld){
            list.clear();
        }
        list.addAll(0,alist);
    }

    /**
     *  在MyBaseAdapter<T>类中定义更新方法，用来对适配器进行刷新，可供外部调用
     * @return
     */
    public void update(){
    //刷新适配器
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list==null){
        return 0;
        }else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (list==null){
            return null;
        }
        //如果已经没有数据就返回为空
        if (position>list.size()-1){
        return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //调用本身的抽象方法
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getMyView(position, convertView, parent);
    }
    //作为预留方法，定义为抽象方法，要求子类继承该基础类时，必须重写该方法
    public abstract View getMyView(int position, View convertView, ViewGroup parent);
}
