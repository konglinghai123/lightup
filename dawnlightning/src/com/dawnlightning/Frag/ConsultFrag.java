package com.dawnlightning.Frag;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dawnlightning.ucqa.R;
import com.zhy.Activity.ConsultActivity;
import com.zhy.Activity.MainActivity_1;
import com.zhy.Activity.NewsContentActivity;

import com.zhy.Adapter.ConsultAdapter;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.ConsultBean;
import com.zhy.Bean.ConsultMessageBean;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.AppUtils;
import com.zhy.Util.DB;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;
import com.zhy.view.TitleBar;

@SuppressLint("ValidFragment")
public class ConsultFrag extends Fragment implements IXListViewRefreshListener, IXListViewLoadMore{
	private XListView newsListView;
	private TitleBar titlebar;
	private ConsultAdapter adapter;
	
	private String PostUrl;//post的地址
	private List<NameValuePair> parms=new ArrayList<NameValuePair>();//post的参数
	private String MessageType;//消息的类别
	private int currentpage=1;
	private  ArrayList<String> userinfo;
	private boolean isLoad = false;
	private List<ConsultMessageBean> list;
	private DB db;
	private String refreshDate = "";
	private Context context;
	
	private HashMap<String, String> mapid = new HashMap<String, String>();//症状分类
	private HashMap<String, String> mapname = new HashMap<String, String>();//科室分类
	List<String> listclass=new ArrayList();
	List<String> listvisor=new ArrayList();
	
	public ConsultFrag(String messagetype,Context context, ArrayList<String> userinfo,String PostUrl,List<NameValuePair> parms){
		this.MessageType = messagetype;
		this.context=context;
		this.userinfo=userinfo;
		this.parms=parms;
		this.PostUrl=PostUrl;
	
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init();
		listclass.add("全部");
		
		
		
	}
	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initComponent();
		getspinnerdate();
		if(isLoad == false){
			isLoad = true;
			refreshDate = getDate();
			newsListView.setRefreshTime(refreshDate);
			//������ݿ��е����
			list = db.query(MessageType);
			adapter.setList(list);
			adapter.notifyDataSetChanged();
			
			newsListView.startRefresh();
			
		}else{
			//newsListView.startRefresh();
			newsListView.NotRefreshAtBegin();
			//newsListView.stopLoadMore();
		}
		//getspinnerdate();
		System.out.println("onActivityCreate");
		super.onActivityCreated(savedInstanceState);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView");
		return inflater.inflate(R.layout.tab1, null);
	}

	private void init(){
		db = new DB(getActivity());
		list=new ArrayList(); 
		adapter = new ConsultAdapter(getActivity(),list); 
	}
	private void initComponent(){
		
			titlebar=(TitleBar)getView().findViewById(R.id.TitleBar2);
			titlebar.setBackgroundColor(getResources().getColor(R.color.blue));
			titlebar.showLeft("", null, new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		 titlebar.showLeftAndRight("", null,getResources().getDrawable(R.drawable.edit),null,new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				Intent i =new Intent();
				i.putStringArrayListExtra("user",userinfo);
				i.setClass(context, ConsultActivity.class);
				startActivity(i);
				
				}
			});
		newsListView = (XListView) getView().findViewById(R.id.newsListView);
		newsListView.setAdapter(adapter);
		newsListView.setPullRefreshEnable(this);
		newsListView.setPullLoadEnable(this);
		newsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ConsultMessageBean item=(ConsultMessageBean)adapter.getItem(arg2-1);
				Log.e("TAG",item.getUid());
				ArrayList<String> message =new ArrayList<String>();
				for(int i=0;i<userinfo.size();i++){
					message.add(userinfo.get(i));
				}
				
				message.add(item.getBwztid());
				message.add(item.getUid());
				Intent i=new Intent();
				i.setClass(getActivity(), NewsContentActivity.class);
				i.putStringArrayListExtra("message", message);
				startActivity(i);
			
			}
		});
	}
	
	@Override
	public void onLoadMore() {
		System.out.println("loadmore");
		currentpage=currentpage+1;
		moreloaddata(currentpage,PostUrl,parms);
		
	}
	@Override
	public void onRefresh() {
		System.out.println("refresh");
		 newsListView.setPullLoadEnable(this);
		currentpage=1;
		resfreshdata(currentpage,PostUrl,parms);
	}
	public String getDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);  
	    return sdf.format(new java.util.Date()); 
	}
	
	public void resfreshdata(int currentpage,String url,List<NameValuePair> parms){
		parms.add(new BasicNameValuePair("page",String.valueOf(currentpage)));
	
		//showdialog();
	new	HttpUtil().doPost(url, parms, new ResultCallback(){

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
						Log.e("Tag",dt.getMessages());
						List<ConsultMessageBean> newlist=JSON.parseArray(dt.getMessages().toString(),ConsultMessageBean.class);		
						
				
						 if(newlist.size()!=0){
								adapter.setList(newlist);
								adapter.notifyDataSetChanged();
								newsListView.stopRefresh(getDate());
								newsListView.setSelection(0);
								for(int i=0;i<newlist.size();i++){
									newlist.get(i).messagetype=MessageType;
								}
						     db.delete(MessageType);
							 db.insert(newlist);
							
							
						 }else{
								adapter.clearList();
							 	adapter.notifyDataSetChanged();
								Toast.makeText(context,"没有更多的咨询", Toast.LENGTH_LONG).show();
								 newsListView.stopRefresh(getDate());
								 newsListView.disablePullLoad();
								
						 }
					}else{
						 newsListView.stopRefresh(getDate());
						
					}
				}else{
					Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
					newsListView.stopRefresh(getDate());
					
					}
				
				
			}
		
	
		
		});}
		
	public void moreloaddata(int currentpage,String url,List<NameValuePair> parms){
		List<NameValuePair> newparms=new ArrayList<NameValuePair>();
		for(NameValuePair np: parms){
			if(np.getName()!="page"){
			newparms.add(np);
			}
		}
		//showdialog();
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
									
										newsListView.stopLoadMore();
										
										for(int i=0;i<newlist.size();i++){
											newlist.get(i).messagetype=MessageType;
										}
								 db.delete(MessageType);
								 db.insert(newlist);
								
								
							 }else{
									Toast.makeText(context,"没有更多的咨询", Toast.LENGTH_LONG).show();
									
									newsListView.stopLoadMore();
									newsListView.disablePullLoad();
							 }
						}else{
							
							newsListView.stopLoadMore();
						}
					}else{
						Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
					
						newsListView.stopLoadMore();
						}
					
					
				}
			
		
			
			});
}
	class select implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			if(arg2==0){
				parms.clear();
				adapter.clearList();
				adapter.notifyDataSetChanged();
				MessageType="全部";
				PostUrl=HttpConstants.HTTP_CONSULT_ALL;
				refreshDate = getDate();
				newsListView.setRefreshTime(refreshDate);
				newsListView.startRefresh();
			
				// mViewPager.setCurrentItem(0);
				 
				 Log.e("SELECT", "0");
				 
			}else{
				parms.clear();
				adapter.clearList();
				adapter.notifyDataSetChanged();
				parms.add(new BasicNameValuePair("bwztclassid",mapid.get(listclass.get(arg2))));
				MessageType=listclass.get(arg2);
				PostUrl=HttpConstants.HTTP_CONSULT_ALL_CLASS;
				refreshDate = getDate();
				newsListView.setRefreshTime(refreshDate);
				newsListView.startRefresh();
				
			}
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			parms.clear();
			adapter.clearList();
			adapter.notifyDataSetChanged();
			MessageType="全部";
			PostUrl=HttpConstants.HTTP_CONSULT_ALL;
			
		}
		
	}
	private void getspinnerdate(){
	if(AppUtils.checkNetwork(context)==true){
		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		//showdialog();
		new	HttpUtil().doPost(HttpConstants.HTTP_SELECTION, allP, new ResultCallback() {

			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				// TODO 自动生成的方法存根
				if(!result.isEmpty() ){
					//close();
					Log.e("Tag", result);
					BaseBean b=JSON.parseObject(result, BaseBean.class);
				
					if("0".equals(b.getCode().toString().trim())){
						
						JSONObject js=(JSONObject) JSON.parse(b.getData());
						
						JSONObject bwztid=(JSONObject) JSON.parse(js.getString("bwztclassarr"));
						JSONObject bwztname=(JSONObject) JSON.parse(js.getString("bwztdivisionarr"));
						Iterator it=bwztname.keySet().iterator();
						Iterator iterator=bwztid.keySet().iterator();
						
						while (iterator.hasNext()){ 
							String s=iterator.next().toString();
							mapid.put(bwztid.getString(s),s);
							listclass.add(bwztid.getString(s));
						
						}
						while(it.hasNext()){
							String s=it.next().toString();
							mapname.put(bwztname.getString(s), s);
							listvisor.add(bwztname.getString(s));
						}
					
						
						titlebar.showSelection(listclass, new select(), context);
						
						
					}else{
						Toast.makeText(context, "获取分类失败", Toast.LENGTH_LONG).show();
					
					}
				
					}else{
						Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
						
					}
				
			}
			
			
		});}else{
			Toast.makeText(context, "网络连接断开", Toast.LENGTH_LONG).show();
		}
}
	private  Dialog loadbar = null;
	private void showdialog(){
		
		if (loadbar == null) {
	            loadbar = new Dialog(context, R.style.load_dialog);
	            LayoutInflater mInflater = getActivity().getLayoutInflater();
	            View v = mInflater.inflate(R.layout.anim_load, null);
	            loadbar.setContentView(v);
	            loadbar.setCancelable(false);
	            loadbar.show();
	        } else {
	            loadbar.show();
	        }
	}
	 private void close() {
	        if (loadbar != null) {
	            if (loadbar.isShowing()) {
	                loadbar.dismiss();
	            }
	        }
	    }


}
	
