package com.zhy.Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dawnlightning.ucqa.R;
import com.zhy.Adapter.MainListViewAdapter;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.CommentBean;
import com.zhy.Bean.DetailedBean;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;
import com.zhy.View.TitleBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

public class CommentActivity extends Activity implements IXListViewLoadMore,IXListViewRefreshListener{
	private DetailedBean detailedBean=new DetailedBean();
	private TitleBar commenttitlebar;
	private ArrayList<String> message;
	private Context context;
	
	@SuppressWarnings("unused")
	private int totlenum=0;
	private int page=1;
	private XListView listview;
	private MainListViewAdapter commentadapter;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_comment);
		initobject();
		initview();
		initevent();
		initdata();
	}

	private void initdata() {
		listview.setRefreshTime(getDate());
		listview.startRefresh();
		
		
	}

	private void initevent() {
		
		listview.setPullRefreshEnable(this);
		listview.setPullLoadEnable(this);
		listview.NotRefreshAtBegin();
		
	}



	private void initview() {
		commenttitlebar=(TitleBar) findViewById(R.id.comment_TitleBar);
		commenttitlebar.setBackgroundColor(getResources().getColor(R.color.blue));
		commenttitlebar.showLeftAndRight("评论", getResources().getDrawable(R.drawable.app_back),null, new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
			
		}, null);
		listview=(XListView) findViewById(R.id.commentListView);
		
	}

	private void initobject() {
		context=this;
		Intent intent=getIntent();
		message=intent.getStringArrayListExtra("message");
		
	}

	@Override
	public void onLoadMore() {
		page++;
		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		allP.add(new BasicNameValuePair("uid",message.get(4)));
		allP.add(new BasicNameValuePair("id",message.get(3)));
		allP.add(new BasicNameValuePair("page",String.valueOf(page)));
		new	HttpUtil().doPost(HttpConstants.HTTP_CONSULT_DETAIL, allP, new ResultCallback(){

			@SuppressWarnings("unused")
			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				if(!result.isEmpty() ){
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					
					if("0".equals(b.getCode())){
						//try{
						JSONObject j=(JSONObject) JSON.parse(b.getData());
						JSONObject js=(JSONObject) JSON.parse(j.getString("bwzt"));
						int i=0;
						for(CommentBean commentBean:JSON.parseArray(js.getString("replylist"),CommentBean.class)){
							detailedBean.getComment().add(commentBean);
							i++;
						}
						
						totlenum=detailedBean.getComment().size();
						commentadapter.setlist(detailedBean.getComment());
						commentadapter.notifyDataSetChanged();
						
						listview.stopLoadMore();
						listview.stopRefresh();
					
						
					}else{
						Toast.makeText(context, "没有更多的评论", Toast.LENGTH_SHORT).show();
						listview.stopLoadMore();
						listview.disablePullLoad();
						listview.stopRefresh();
					}
				}else{
					Toast.makeText(context, "服务器响应失败", Toast.LENGTH_SHORT).show();
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
		allP.add(new BasicNameValuePair("uid",message.get(4)));
		allP.add(new BasicNameValuePair("id",message.get(3)));
		allP.add(new BasicNameValuePair("page",String.valueOf(page)));
		new	HttpUtil().doPost(HttpConstants.HTTP_CONSULT_DETAIL, allP, new ResultCallback(){

			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				if(!result.isEmpty() ){
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					
					if("0".equals(b.getCode())){
						//try{
						JSONObject j=(JSONObject) JSON.parse(b.getData());
						JSONObject js=(JSONObject) JSON.parse(j.getString("bwzt"));
						detailedBean.setComment(JSON.parseArray(js.getString("replylist"),CommentBean.class));
						totlenum=detailedBean.getComment().size();
						commentadapter=new MainListViewAdapter(context,detailedBean.getComment());
						listview.setAdapter(commentadapter);
						listview.stopRefresh();
						listview.stopLoadMore();
						
					}else{
						Toast.makeText(context, "没有更多的评论", Toast.LENGTH_SHORT).show();
						listview.stopRefresh();
						listview.stopLoadMore();
					}
				}else{
					Toast.makeText(context, "服务器响应失败", Toast.LENGTH_SHORT).show();
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
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	           
	           finish();
	        }
	        return super.onKeyDown(keyCode, event);
	    }
}
