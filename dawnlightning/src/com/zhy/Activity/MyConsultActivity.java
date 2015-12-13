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
import com.zhy.Adapter.ConsultAdapter;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.ConsultBean;
import com.zhy.Bean.ConsultMessageBean;
import com.zhy.Util.AppUtils;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;
import com.zhy.view.TitleBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyConsultActivity extends Activity implements IXListViewRefreshListener, IXListViewLoadMore{
	public static XListView newsListView;
	private TitleBar titlebar;
	private ConsultAdapter adapter;
	private Context context;
	private String MessageType="我";
	private List<ConsultMessageBean> list;
	private String PostUrl=HttpConstants.HTTP_CONSULT;//post的地址
	private int currentpage=1;
	private  ArrayList<String> userinfo;
	private List<NameValuePair> parms=new ArrayList<NameValuePair>();//post的参数
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1);
		context=this;
		list=new ArrayList(); 
		adapter = new ConsultAdapter(context,list); 
		Intent rec=getIntent();
		userinfo=rec.getStringArrayListExtra("userinfo");
		initview();
		initdata();
		getdata();
	}
	private void initview(){
		
		titlebar=(TitleBar)findViewById(R.id.TitleBar2);
		titlebar.setBackgroundColor(getResources().getColor(R.color.blue));
		titlebar.showLeft("我的咨询", getResources().getDrawable(R.drawable.app_back), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		newsListView = (XListView)findViewById(R.id.newsListView);
		newsListView.setAdapter(adapter);
		newsListView.setPullRefreshEnable(this);
		newsListView.setPullLoadEnable(this);
		newsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ConsultMessageBean item=(ConsultMessageBean)adapter.getItem(arg2-1);
				ArrayList<String> message =new ArrayList<String>();
				for(int i=0;i<userinfo.size();i++){
					message.add(userinfo.get(i));
				}
				
				message.add(item.getBwztid());
				message.add(item.getUid());
				message.add(item.getReplynum());
				Intent i=new Intent();
				
				//NewsItem item = (NewsItem)adapter.getItem(arg2-1);
				//Intent i = new Intent();
				i.setClass(context, MyConsultContentActivity.class);
				i.putStringArrayListExtra("message", message);
				startActivity(i);
				//getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
				//System.out.println(arg2);
				
			}
		});
	}
	private void initdata(){
		
		
	}
	private void getdata(){
		parms.add(new BasicNameValuePair("uid",userinfo.get(2)));
		parms.add(new BasicNameValuePair("m_auth",userinfo.get(1)));
		
		newsListView.startRefresh();
	}
	public void resfreshdata(int currentpage,String url,List<NameValuePair> parms){
		if(AppUtils.checkNetwork(context)==true){
		parms.add(new BasicNameValuePair("page",String.valueOf(currentpage)));
	
		//showdialog();
	new	HttpUtil().doPost(url, parms, new ResultCallback(){

			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				if(!result.isEmpty() ){
					
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					if("0".equals(b.getCode())){
					
						ConsultBean dt=new ConsultBean();
						JSONObject js=(JSONObject) JSON.parse(b.getData());
						dt.setMessages(js.getString("list"));
						dt.setCount(js.getIntValue("count"));
						List<ConsultMessageBean> newlist=JSON.parseArray(dt.getMessages().toString(),ConsultMessageBean.class);		
						
				
						 if(newlist.size()!=0){
								adapter.setList(newlist);
								adapter.notifyDataSetChanged();
								newsListView.stopRefresh(getDate());
								newsListView.setSelection(0);
								
								for(int i=0;i<newlist.size();i++){
									newlist.get(i).messagetype=MessageType;
								}
						  
						 }else{
							 	adapter.clearList();
							 	adapter.notifyDataSetChanged();
								Toast.makeText(context,"没有更多的咨询", Toast.LENGTH_LONG).show();
								newsListView.stopRefresh(getDate());
							
						 }
					}else{
						 newsListView.stopRefresh(getDate());
					
					}
				}else{
					Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
					newsListView.stopRefresh(getDate());
					
					}
				
				
			}
		
	
		
		});}else{
			Toast.makeText(context, "网络连接失败", Toast.LENGTH_LONG).show();
		}}
		
	public void moreloaddata(int currentpage,String url,List<NameValuePair> parms){
		if(AppUtils.checkNetwork(context)==true){
		List<NameValuePair> newparms=new ArrayList<NameValuePair>();
		for(NameValuePair np: parms){
			if(np.getName()!="page"){
			newparms.add(np);
			}
		}
		
		newparms.add(new BasicNameValuePair("page",String.valueOf(currentpage)));
			new	HttpUtil().doPost(url, newparms, new ResultCallback(){

				@SuppressLint("NewApi")
				@Override
				public void getReslt(String result) {
					if(!result.isEmpty() ){
						
						Log.e("TAG",result);
						BaseBean b=JSON.parseObject(result, BaseBean.class);
						if("0".equals(b.getCode())){
						
							ConsultBean dt=new ConsultBean();
							JSONObject js=(JSONObject) JSON.parse(b.getData());
							dt.setMessages(js.getString("list"));
							dt.setCount(js.getIntValue("count"));
							List<ConsultMessageBean> newlist=JSON.parseArray(dt.getMessages().toString(),ConsultMessageBean.class);		
							
					
							 if(newlist.size()!=0){
									adapter.addList(newlist);
									adapter.notifyDataSetChanged();
									 newsListView.stopRefresh(getDate());
									 newsListView.setSelection(0);
										for(int i=0;i<newlist.size();i++){
										
											newlist.get(i).messagetype=MessageType;
										}
								
								
								
							 }else{
									Toast.makeText(context,"没有更多的咨询", Toast.LENGTH_LONG).show();
									adapter.clearList();
								 	adapter.notifyDataSetChanged();
									newsListView.stopLoadMore();
							 }
						}else{
							
							newsListView.stopLoadMore();
						}
					}else{
						Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
					
						newsListView.stopLoadMore();
						}
					
					
				}
			
		
			
			});}else{
				Toast.makeText(context, "网络连接失败", Toast.LENGTH_LONG).show();
			}
}
	public String getDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);  
	    return sdf.format(new java.util.Date()); 
	}
	@Override
	public void onRefresh() {
		System.out.println("refresh");
		currentpage=1;
		resfreshdata(currentpage,PostUrl,parms);
		
	}
	@Override
	public void onLoadMore() {
		System.out.println("loadmore");
		currentpage=currentpage+1;
		moreloaddata(currentpage,PostUrl,parms);
		
	}
	@Override
	protected void onDestroy() {
		System.gc();
		super.onDestroy();
	}
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	           
	           finish();
	        }
	        return super.onKeyDown(keyCode, event);
	    }
}
