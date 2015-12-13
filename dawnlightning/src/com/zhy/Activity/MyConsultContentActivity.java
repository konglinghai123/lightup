package com.zhy.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cn.sharesdk.framework.ShareSDK;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dawnlightning.Frag.ConsultFrag;
import com.dawnlightning.ucqa.R;

import com.zhy.Adapter.ContentAdapter;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.Comment;
import com.zhy.Bean.Detailed;
import com.zhy.Bean.Pics;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.AppUtils;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;
import com.zhy.dialog.ActionItem;
import com.zhy.dialog.TitlePopup;
import com.zhy.dialog.TitlePopup.OnItemOnClickListener;
import com.zhy.view.TitleBar;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MyConsultContentActivity extends BaseActivity {
	
	private ArrayList<String> message;
	private Context context;
	private Detailed detailed;//详细
	private ListView contentlist;
	private TextView totalreplay;
	private ImageView write;
	private ImageView commonlist;
	private ImageView share;
	private ContentAdapter contentadapter;
	private final static String [] fileds={"开头","正文","图片"};
	private HashMap<String,Detailed> content;
	private TitleBar contenttitlebar;
	private int page=1;
	private int totlenum=0;
	private TitlePopup titlePopup;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_content);
		initview();
		initdata();
		
		getcontent();
	}

	private void initview(){
		ShareSDK.initSDK(this);
		contenttitlebar=(TitleBar)findViewById(R.id.content_TitleBar);	
		contenttitlebar.setBackgroundColor(getResources().getColor(R.color.blue));
		contentlist=(ListView) findViewById(R.id.contentListView);
		totalreplay=(TextView) findViewById(R.id.action_comment_count);
		write=(ImageView) findViewById(R.id.action_write_comment);
		commonlist=(ImageView) findViewById(R.id.action_view_comment);
		share=(ImageView) findViewById(R.id.action_share);
		
		
	}
	private void initdata(){
		context=this;
		Intent intent=getIntent();
		message=intent.getStringArrayListExtra("message");
		totlenum=Integer.parseInt(message.get(message.size()-1));
		totalreplay.setText(String.valueOf(totlenum));
		
		titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//给标题栏弹窗添加子类
		titlePopup.addAction(new ActionItem(this, "删除咨询", R.drawable.guanbizhixun));
		titlePopup.addAction(new ActionItem(this, "采纳关闭", R.drawable.cainazhixun));
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
		contenttitlebar.showLeftAndRight("详细", getResources().getDrawable(R.drawable.app_back),getResources().getDrawable(R.drawable.menu_selector), new backlistener(), new commentlistener());
		
		
		
	}
	private void initevent(){
		
		write.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				showUpdataDialog();
			}
			
		});
		
		commonlist.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(detailed.getComment().size()!=0){
					Intent i=new Intent();
					i.setClass(context, CommentActivity.class);
					i.putStringArrayListExtra("message", message);
					startActivity(i);
					}else{
						Toast.makeText(context, "没有评论", Toast.LENGTH_SHORT).show();
					}
				
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
		if(AppUtils.checkNetwork(context)==true){
	
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
			
			
		});}else{
			Toast.makeText(context, "网络连接断开", Toast.LENGTH_LONG).show();
		}
	}
	public void editstauts(){
		if(AppUtils.checkNetwork(context)==true){

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
			
			
		});}else{
			Toast.makeText(context, "网络连接断开", Toast.LENGTH_LONG).show();
		}
	}
	public void setcomment(final String str){
		if(AppUtils.checkNetwork(context)==true){
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
						
						
						 Toast.makeText(context, "评论成功", Toast.LENGTH_LONG).show();
						 totlenum=Integer.parseInt(message.get(message.size()-1))+1;
						 totalreplay.setText(String.valueOf(totlenum));
						 
					}else{
						Toast.makeText(context, "评论失败", Toast.LENGTH_LONG).show();
					
					}
				
					}else{
						Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
						
					}
				
			}
			
			
		});}else{
			Toast.makeText(context, "网络连接断开", Toast.LENGTH_LONG).show();
		}
	
	}
	
	@SuppressLint("NewApi")
	private void getcontent(){
		if(AppUtils.checkNetwork(context)==true){
		page=1;
		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		allP.add(new BasicNameValuePair("uid",message.get(4)));
		allP.add(new BasicNameValuePair("id",message.get(3)));
		allP.add(new BasicNameValuePair("page",String.valueOf(page)));
		initProgressDialog();
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
					
						
						 contentadapter=new ContentAdapter(context,content);
						 contentlist.setAdapter(contentadapter);
						 initevent();
						
					
						
					}else{
						Toast.makeText(context, "获取详细失败", Toast.LENGTH_SHORT).show();
						 close();
						 
					}
				}else{
					Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
					 close();
					
					
					}
				
				
			}
		
	
		
		});}else{
			Toast.makeText(context, "网络连接断开", Toast.LENGTH_LONG).show();}
		}
	public  Bitmap decodeResource(Resources resources, int id) {  
		  
	    int densityDpi = resources.getDisplayMetrics().densityDpi;  
	    Bitmap bitmap;  
	    TypedValue value = new TypedValue();  
	    resources.openRawResource(id, value);  
	    BitmapFactory.Options opts = new BitmapFactory.Options();  
	    opts.inPreferredConfig = Bitmap.Config.ALPHA_8;  
	    if (densityDpi > DisplayMetrics.DENSITY_HIGH) {  
	        opts.inTargetDensity = value.density;  
	        bitmap = BitmapFactory.decodeResource(resources, id, opts);  
	    }else{  
	        bitmap = BitmapFactory.decodeResource(resources, id);  
	    }  
	  
	    return bitmap;  
	}  
	
	public void showToast(String str){
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
	public String TimeStamp2Date(String timestampString){  
		  Long timestamp = Long.parseLong(timestampString)*1000;  
		  String date = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new java.util.Date(timestamp));  
		  return date;  
		}
	

	
	protected void showUpdataDialog() {

        final com.zhy.dialog.CustomDialogUpd.Builder builder = new com.zhy.dialog.CustomDialogUpd.Builder(context);
        builder.setTitle("发表评论");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub
                String  content= builder.getEditText().getText().toString().trim();
                setcomment(content);
                dialog.dismiss();
            }
        });
		// 当点取消按钮时进行登录
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

        builder.create().show();
    }

	 
	

	
	
	
}
