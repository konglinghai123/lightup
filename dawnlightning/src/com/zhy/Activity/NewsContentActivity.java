package com.zhy.Activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dawnlightning.ucqa.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhy.Activity.ConsultActivity.SelectPopuWindow;
import com.zhy.Adapter.ContentAdapter;
import com.zhy.Adapter.MainGridViewAdapter;
import com.zhy.Adapter.MainListViewAdapter;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.Comment;
import com.zhy.Bean.ConsultBean;
import com.zhy.Bean.ConsultMessageBean;
import com.zhy.Bean.Detailed;
import com.zhy.Bean.Pics;
import com.zhy.Bean.SeccodeBean;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.AppUtils;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.LvHeightUtil;
import com.zhy.Util.ResultCallback;
import com.zhy.Util.UnitTransformUtil;
import com.zhy.upload.HttpStringResult;
import com.zhy.view.NoScrollGridView;
import com.zhy.view.TitleBar;

public class NewsContentActivity extends BaseActivity implements OnClickListener{

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
		ShareSDK.initSDK(this);
		contenttitlebar=(TitleBar)findViewById(R.id.content_TitleBar);	
		contenttitlebar.setBackgroundColor(getResources().getColor(R.color.blue));
		contentlist=(ListView) findViewById(R.id.contentListView);
		setcomment=(EditText) findViewById(R.id.etPingLunContent);
		send=(Button) findViewById(R.id.bt_send);
		

	}
	private void initdata(){
		context=this;
		Intent intent=getIntent();
		message=intent.getStringArrayListExtra("message");
	
		
		
	}
	private void initevent(){
		contenttitlebar.showLeftAndRight("详细", getResources().getDrawable(R.drawable.app_back),null, new backlistener(), new commentlistener());
		
		contentlist.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
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
			
			
		}
	
	}
	@Override
	protected void onStart() {
		
		
	
		
	
		super.onStart();
	}

	@Override
	public void onClick(View arg0) {
		
		
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
						Toast.makeText(NewsContentActivity.this, "评论失败", Toast.LENGTH_LONG).show();
					
					}
				
					}else{
						Toast.makeText(NewsContentActivity.this, "服务器响应失败", Toast.LENGTH_LONG).show();
						
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
						 contentadapter.setfengxiangOnclickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
									
								showShare(context, null, true,detailed);
								Log.e("tag","点击了");
								
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
		Toast.makeText(NewsContentActivity.this, str, Toast.LENGTH_LONG).show();
	}
	public String TimeStamp2Date(String timestampString){  
		  Long timestamp = Long.parseLong(timestampString)*1000;  
		  String date = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(new java.util.Date(timestamp));  
		  return date;  
		}
	/**
	 * 演示调用ShareSDK执行分享
	 *
	 * @param context
	 * @param platformToShare  指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
	 * @param showContentEdit  是否显示编辑页
	 */
	public  void showShare(Context context, String platformToShare, boolean showContentEdit,Detailed detailed) {
		OnekeyShare oks = new OnekeyShare();
		oks.setSilent(!showContentEdit);
		if (platformToShare != null) {
			oks.setPlatform(platformToShare);
		}
		//ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
		oks.setTheme(OnekeyShareTheme.CLASSIC);
		// 令编辑页面显示为Dialog模式
		oks.setDialogMode();
		// 在自动授权时可以禁用SSO方式
		oks.disableSSOWhenAuthorize();
		//oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
		oks.setTitle(detailed.getSubject());
		oks.setTitleUrl("http://mob.com");
		oks.setText(detailed.getContent());
		oks.setImagePath(context.getResources().getString(R.drawable.mylogo));  //分享sdcard目录下的图片
		//oks.setImageUrl(randomPic()[0]);
		oks.setUrl("http://www.mob.com"); //微信不绕过审核分享链接
		//oks.setFilePath("/sdcard/test-pic.jpg");  //filePath是待分享应用程序的本地路劲，仅在微信（易信）好友和Dropbox中使用，否则可以不提供
		oks.setComment("分享"); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
		oks.setSite("点亮");  //QZone分享完之后返回应用时提示框上显示的名称
		oks.setSiteUrl("http://mob.com");//QZone分享参数
		oks.setVenueName("ShareSDK");
		oks.setVenueDescription("This is a beautiful place!");
		oks.setShareFromQQAuthSupport(false);
		// 将快捷分享的操作结果将通过OneKeyShareCallback回调
		//oks.setCallback(new OneKeyShareCallback());
		// 去自定义不同平台的字段内容
		//oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
		// 在九宫格设置自定义的图标
		// Bitmap enableLogo = BitmapFactory.decodeResource(context.getResources(), R.drawable.mylogo);
		 //Bitmap disableLogo = BitmapFactory.decodeResource(context.getResources(), R.drawable.mylogo);
		 //String label = "点亮";
		 //OnClickListener listener = new OnClickListener() {
		 	//public void onClick(View v) {

		 	//}
		 //};
		 //oks.setCustomerLogo(enableLogo, disableLogo, label, listener);

		// 为EditPage设置一个背景的View
		//oks.setEditPageBackground(getPage());
		// 隐藏九宫格中的新浪微博
		// oks.addHiddenPlatform(SinaWeibo.NAME);

		// String[] AVATARS = {
				// 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
				// 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
				// 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
				// 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
				// 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
				// 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
				// oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

		// 启动分享
		oks.show(context);
	}
	
	

	
	
}
