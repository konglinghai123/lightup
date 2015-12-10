package com.zhy.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dawnlightning.ucqa.R;
import com.zhy.Adapter.ContentAdapter;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.Comment;
import com.zhy.Bean.Detailed;
import com.zhy.Bean.Pics;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;
import com.zhy.dialog.ActionItem;
import com.zhy.dialog.TitlePopup;
import com.zhy.dialog.TitlePopup.OnItemOnClickListener;
import com.zhy.view.TitleBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyConsultContentActivity extends Activity {

	private String text;
	private TitleBar contenttitlebar;
	private ArrayList<String> message;
	private Context context;
	private Detailed detailed;//详细
	private  List<Comment> comList=new ArrayList();
	private ListView contentlist;
	private EditText setcomment;
	private Button send;
	private ContentAdapter contentadapter;
	private final static String [] fileds={"开头","正文","图片","评论"};
	private HashMap<String,Detailed> content;
	private TitlePopup titlePopup;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_content);
		initview();
		initdata();
		initevent();
		getcontent();
	}

	private void initview(){
		contenttitlebar=(TitleBar)findViewById(R.id.content_TitleBar);	
		contenttitlebar.setBackgroundColor(getResources().getColor(R.color.blue));
		contentlist=(ListView) findViewById(R.id.contentListView);
		setcomment=(EditText) findViewById(R.id.etPingLunContent);
		send=(Button) findViewById(R.id.bt_send);
		
		titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}
	private void initdata(){
		context=this;
		Intent intent=getIntent();
		message=intent.getStringArrayListExtra("message");
		//给标题栏弹窗添加子类
				titlePopup.addAction(new ActionItem(this, "删除咨询", R.drawable.guanbizhixun));
				titlePopup.addAction(new ActionItem(this, "采纳关闭", R.drawable.cainazhixun));
				
	}
	private void initevent(){
		contenttitlebar.showLeftAndRight("详细", getResources().getDrawable(R.drawable.app_back),getResources().getDrawable(R.drawable.menu_selector), new backlistener(), new commentlistener());
		
		contentlist.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
			}
			
		});
		titlePopup.setItemOnClickListener(new OnItemOnClickListener(){

			@Override
			public void onItemClick(ActionItem item, int position) {
					switch(position){
					case 0:
						delectconsult();
					break;
					case 1:
						editstauts();
					break;
					}
				
			}
			
		});
		send.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setcomment(setcomment.getText().toString().trim());
				
			}
			
		});
	}
	class backlistener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			finish();
		}
	
	}
	class commentlistener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			titlePopup.show(v);
		}
	
	}
	@Override
	protected void onStart() {
		
		
	
		
	
		super.onStart();
	}

	private void delectconsult(){
		final List<NameValuePair> allP=new ArrayList<NameValuePair>();
	
		
		allP.add(new BasicNameValuePair("id",message.get(3).toString()));
		allP.add(new BasicNameValuePair("m_auth",message.get(1).toString()));
	
		new	HttpUtil().doPost(HttpConstants.HTTP_DELETE_CONSULT, allP, new ResultCallback() {
			
			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				// TODO 自动生成的方法存根
				if(!result.isEmpty() ){
		
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					if("0".equals(b.getCode().toString().trim())){
						
						Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
						finish();
					}else{
						Toast.makeText(context, b.getMsg(), Toast.LENGTH_LONG).show();
					
					}
				
					}else{
						Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
						
					}
				
			}
			
			
		});
	}
	public void editstauts(){
		final List<NameValuePair> allP=new ArrayList<NameValuePair>();
	
		
		allP.add(new BasicNameValuePair("id",message.get(3).toString()));
		allP.add(new BasicNameValuePair("m_auth",message.get(1).toString()));
		new	HttpUtil().doPost(HttpConstants.HTTP_EDIT_CONSULT_STAUTS.replace("!", message.get(3)).replace("@", message.get(1).toString()), allP, new ResultCallback() {
			
			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				// TODO 自动生成的方法存根
				if(!result.isEmpty() ){
				
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					if("0".equals(b.getCode().toString().trim())){
						
						Toast.makeText(context, b.getMsg(), Toast.LENGTH_LONG).show();
						finish();
					}else{
						Toast.makeText(context, b.getMsg(), Toast.LENGTH_LONG).show();
					
					}
				
					}else{
						Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
						
					}
				
			}
			
			
		});
	}
	public void setcomment(final String str){
		
		final List<NameValuePair> allP=new ArrayList<NameValuePair>();
		allP.add(new BasicNameValuePair("commentsubmit","true"));
		allP.add(new BasicNameValuePair("formhash",new SharedPreferenceDb(context).getformhash()));
		allP.add(new BasicNameValuePair("id",message.get(3).toString()));
		allP.add(new BasicNameValuePair("idtype","bwztid"));
		allP.add(new BasicNameValuePair("message",str));
		new	HttpUtil().doPost(HttpConstants.HTTP_COMMENT.replace("!", message.get(1)), allP, new ResultCallback() {
			
			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				// TODO 自动生成的方法存根
				if(!result.isEmpty() ){
				
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					if("0".equals(b.getCode().toString().trim())){
						 detailed.getComment().add(new Comment(message.get(0),new SharedPreferenceDb(context).getname(),str));
						 contentadapter.notifyDataSetChanged();
						 send.setText("评论"+detailed.getComment().size());
						 send.setClickable(false);
						 setcomment.clearFocus();
						 setcomment.setText("");
						 setcomment.setFocusable(false);
						 setcomment.setFocusableInTouchMode(false);
						 InputMethodManager imm = (InputMethodManager)setcomment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
						 imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
					}else{
						Toast.makeText(context, "评论失败", Toast.LENGTH_LONG).show();
					
					}
				
					}else{
						Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
						
					}
				
			}
			
			
		});
	
	}
	
	@SuppressLint("NewApi")
	private void getcontent(){
		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		allP.add(new BasicNameValuePair("uid",message.get(4)));
		allP.add(new BasicNameValuePair("id",message.get(3)));
	
		new	HttpUtil().doPost(HttpConstants.HTTP_CONSULT_DETAIL, allP, new ResultCallback(){

			@Override
			public void getReslt(String result) {
				if(!result.isEmpty() ){
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					Log.e("msg", b.getData());
					if("0".equals(b.getCode())){
						//try{
						JSONObject j=(JSONObject) JSON.parse(b.getData());
						JSONObject js=(JSONObject) JSON.parse(j.getString("bwzt"));
						detailed=new Detailed();
						 detailed.setAge(js.getString("age"));
						 detailed.setContent(js.getString("message"));
						 detailed.setDatetime( TimeStamp2Date(js.getString("dateline")));
						 detailed.setSubject(js.getString("subject"));
						 detailed.setUid(js.getString("uid"));
						 detailed.setUsename(js.getString("username"));
						 detailed.setAvatar_url(js.getString("avatar_url"));
						 detailed.setName(js.getString("name"));
						 detailed.setComment(JSON.parseArray(js.getString("replylist"),Comment.class));
						 JSONArray jsonArray = JSONArray.parseArray(js.getString("pics")); 
						 List<Pics> list=new ArrayList<Pics>();
						 if(jsonArray!=null){
						 for(int k=0;k<jsonArray.size();k++){  
				               System.out.print(jsonArray.get(k) + "\t");  
				               Log.e("strpic",jsonArray.get(k).toString());
				               JSONObject objpic=(JSONObject) JSON.parse(jsonArray.get(k).toString());
				              
				               Pics p=new Pics( objpic.getString("picurl"),"");
				               list.add(p);
				           }  
						
						
						 detailed.setPics(list);
						 }
						 content=new HashMap<String,Detailed>();
						 for(int i=0;i<fileds.length;i++){
							 Log.e("Tag",fileds[i]);
							 content.put(fileds[i], detailed);
						 }
						 send.setText("评论"+detailed.getComment().size());
						 if(detailed.getComment().size()==0){
							 	setcomment.setFocusable(true);
								setcomment.setFocusableInTouchMode(true);
								setcomment.requestFocus();
								setcomment.requestFocusFromTouch();
								setcomment.setCursorVisible(true);
								send.setClickable(true);
								send.setText("发送");
						 }
						
						 contentadapter=new ContentAdapter(context,content);
						 contentadapter.setpinglunOnclickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								setcomment.setFocusable(true);
								setcomment.setFocusableInTouchMode(true);
								setcomment.requestFocus();
								setcomment.requestFocusFromTouch();
								setcomment.setCursorVisible(true);
								InputMethodManager imm = (InputMethodManager)setcomment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED); 
								
								 send.setClickable(true);
								 send.setText("发送");
							}
							 
						 });
						 contentlist.setAdapter(contentadapter);
					
						
					}else{
						Toast.makeText(context, "获取详细失败", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
					
					}
				
				
			}
		
	
		
		});}
	
	public void showToast(String str){
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
	public String TimeStamp2Date(String timestampString){  
		  Long timestamp = Long.parseLong(timestampString)*1000;  
		  String date = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new java.util.Date(timestamp));  
		  return date;  
		}
	
	

	
	
	
}
