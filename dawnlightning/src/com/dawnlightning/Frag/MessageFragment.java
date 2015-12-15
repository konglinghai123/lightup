package com.dawnlightning.Frag;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dawnlightning.ucqa.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.Activity.AboutActivity;
import com.zhy.Activity.LoginActivity;
import com.zhy.Activity.MyConsultActivity;
import com.zhy.Activity.UserInfoActivity;
import com.zhy.Adapter.MainListViewAdapter;
import com.zhy.Adapter.MessageAdapter;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.Comment;
import com.zhy.Bean.MessageBean;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;
import com.zhy.Util.SdCardUtil;
import com.zhy.view.TitleBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MessageFragment extends Fragment implements IXListViewLoadMore,IXListViewRefreshListener {

	private Context context;
	private ArrayList<String> userinfo;
	private TitleBar titlebar;
	private TextView tvcount;
	private  SharedPreferenceDb mySharedPreferenceDb=null;
	private int page=1;
	private XListView listview;
	private MessageAdapter messageadapter;
	private TitleBar messagetitlebar;
	private String messagecount="";
	private LayoutInflater inflater;
	private List<MessageBean> messages=new ArrayList<MessageBean>();

	public MessageFragment(Context context,ArrayList<String> userinfo,TextView count)
	{
		
		this.context=context;
		this.userinfo=userinfo;
		this.mySharedPreferenceDb=new  SharedPreferenceDb(context);
		this.inflater=LayoutInflater.from(context);
		this.tvcount=count;
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		messageadapter=new MessageAdapter(messages,context);
		getdata();
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{	
		 initview();
		 initevent();
		 initdata();
		super.onActivityCreated(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.list_comment, null);
		
		return view;
	}

	@Override
	public void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		
	}

	@Override
	public void onResume() {
		
		super.onResume();
	
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO 自动生成的方法存根
		super.onAttach(activity);
	
		
	}

	@Override
	public void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
	
	}

	@Override
	public void onStop() {
		
		super.onStop();
		
	}
	private void initdata() {
		
		
		
		
	}

	private void initevent() {
		
		listview.setPullRefreshEnable(this);
		listview.setPullLoadEnable(this);
		
		
	}



	private void initview() {
		
		
		messagetitlebar= (TitleBar)getView().findViewById(R.id.comment_TitleBar);
		messagetitlebar.setBackgroundColor(getResources().getColor(R.color.blue));
		messagetitlebar.showCenterTitle("未读消息");
		listview=(XListView) getView().findViewById(R.id.commentListView);
		listview.setRefreshTime(getDate());
		listview.NotRefreshAtBegin();
		listview.setAdapter(messageadapter);
		
		
	}

	@Override
	public void onLoadMore() {
		page++;
		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		allP.add(new BasicNameValuePair("m_auth",userinfo.get(1)));
		allP.add(new BasicNameValuePair("page",String.valueOf(page)));
		new	HttpUtil().doPost(HttpConstants.HTTP_UNREAD_MESSAGE, allP, new ResultCallback(){

			@SuppressWarnings("unused")
			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				if(!result.isEmpty() ){
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					
					if("0".equals(b.getCode())){
						//try{
						JSONObject j=(JSONObject) JSON.parse(b.getData());
						JSONObject js=(JSONObject) JSON.parse(j.getString("notices"));
					
						
						
						List<MessageBean> list=JSON.parseArray(js.getString("list"),MessageBean.class);
						
						
						
						for(MessageBean m:list){
							messages.add(m);
						}
						
						messageadapter.notifyDataSetChanged();
						tvcount.setVisibility(View.VISIBLE);
						tvcount.setText(messagecount);
						listview.stopLoadMore();
						listview.stopRefresh();
					
						
					}else{
						Toast.makeText(context, "没有更多的消息", Toast.LENGTH_SHORT).show();
						listview.stopLoadMore();
						listview.disablePullLoad();
						listview.stopRefresh();
					}
				}else{
					Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
					listview.stopRefresh();
					listview.stopLoadMore();
					}
				
			}
			
		});
		
	}

	@Override
	public void onRefresh() {
		page=1;
		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		allP.add(new BasicNameValuePair("m_auth",userinfo.get(1)));
		allP.add(new BasicNameValuePair("page",String.valueOf(page)));
		new	HttpUtil().doPost(HttpConstants.HTTP_UNREAD_MESSAGE, allP, new ResultCallback(){

			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				if(!result.isEmpty() ){
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					
					if("0".equals(b.getCode())){
						Log.e("msg", result);
						JSONObject j=(JSONObject) JSON.parse(b.getData());
						JSONObject js=(JSONObject) JSON.parse(j.getString("notices"));
						messagecount=js.getString("count");
						
						tvcount.setVisibility(View.VISIBLE);
						tvcount.setText(messagecount);
						
						messages=JSON.parseArray(js.getString("list"),MessageBean.class);
						
						messageadapter=new MessageAdapter(messages,context);
						listview.setAdapter(messageadapter);
						listview.stopRefresh();
						listview.stopLoadMore();
						
					}else{
						Toast.makeText(context, "没有更多的消息", Toast.LENGTH_SHORT).show();
						listview.stopRefresh();
						listview.stopLoadMore();
					}
				}else{
					Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
					listview.stopRefresh();
					listview.stopLoadMore();
					
					}
				
			}
			
		});
	}
	public String getDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);  
	    return sdf.format(new java.util.Date()); 
	}
	public void getdata() {
		page=1;
		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		allP.add(new BasicNameValuePair("m_auth",userinfo.get(1)));
		allP.add(new BasicNameValuePair("page",String.valueOf(page)));
		new	HttpUtil().doPost(HttpConstants.HTTP_UNREAD_MESSAGE, allP, new ResultCallback(){

			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				if(!result.isEmpty() ){
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					if("0".equals(b.getCode())){
						
						JSONObject j=(JSONObject) JSON.parse(b.getData());
						JSONObject js=(JSONObject) JSON.parse(j.getString("notices"));
						messagecount=js.getString("count");
						messages=JSON.parseArray(js.getString("list"),MessageBean.class);
						messageadapter.setlist(messages);
						tvcount.setVisibility(View.VISIBLE);
						tvcount.setText(messagecount);
						
						
					}else{
						
						
					}
				}else{
					Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
					
					
					}
				
			}
			
		});
	}
	
}
