package com.zhy.Activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dawnlightning.ucqa.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.Bean.BaseBean;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.AppUtils;
import com.zhy.Util.DataCleanManager;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;
import com.zhy.Util.SdCardUtil;
import com.zhy.dialog.ActionItem;
import com.zhy.dialog.TitlePopup;
import com.zhy.dialog.TitlePopup.OnItemOnClickListener;
import com.zhy.upload.UploadPicture;
import com.zhy.view.TitleBar;
import com.zhy.wheelview.NumericWheelAdapter;
import com.zhy.wheelview.WheelView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends Activity{
	private ImageView icon;
	private TextView age;
	private TextView sex;
	private TextView name;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private Context context;
	private SharedPreferenceDb MySharedPreferenceDb;
	private TitleBar titlebar;
	private TitlePopup titlePopup;
	private View parentview;
	String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
	ArrayList<String> message;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		  requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		   setContentView(R.layout.profile_content);
		   context=this;
		   MySharedPreferenceDb=new SharedPreferenceDb(context);
		   initview();
		   initdata();
		   initevent();
	}
	private void initview(){
		parentview=findViewById(R.id.linearlayout_profile);
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.defalut)
				.showImageOnFail(R.drawable.defalut).cacheInMemory().cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(20)).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		icon=(ImageView) findViewById(R.id.usericon);
		age=(TextView) findViewById(R.id.userage);
		sex=(TextView) findViewById(R.id.usersex);
		name=(TextView) findViewById(R.id.username);
		titlebar=(TitleBar) findViewById(R.id.TitleBar4);
		titlebar.setBackgroundColor(getResources().getColor(R.color.blue));
		titlebar.showLeftAndRight("个人信息", getResources().getDrawable(R.drawable.app_back),getResources().getDrawable(R.drawable.menu_selector), new backlistener(), new commentlistener());
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
	private void initdata(){
		 Intent intent=getIntent();
		 message = intent.getStringArrayListExtra("userinfo");
		 imageLoader.loadImage(MySharedPreferenceDb.getuserAVATOR() ,options, new SimpleImageLoadingListener(){

				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					// TODO 自动生成的方法存根
					Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(loadedImage, 64, 64);
					icon.setImageBitmap(resizeBmp);
				}
				
			});
		 if(MySharedPreferenceDb.getsex()==""&&MySharedPreferenceDb.getsex().length()==0){
			 sex.setText("未设置");
		 }else{
			 sex.setText(MySharedPreferenceDb.getsex());
		 }
		 if(MySharedPreferenceDb.getage()==""&&MySharedPreferenceDb.getage().length()==0){
			 age.setText("未设置");
		 }else{
			 age.setText(MySharedPreferenceDb.getage());
		 }
		 if(MySharedPreferenceDb.getname()==""&&MySharedPreferenceDb.getname().length()==0){
			 name.setText("未设置");
		 }else{
			 name.setText(MySharedPreferenceDb.getname());
		 }
			//给标题栏弹窗添加子类
			titlePopup.addAction(new ActionItem(this, "上传头像", R.drawable.upload));
			titlePopup.addAction(new ActionItem(this, "补填资料", R.drawable.tianbuzhiliao));
	}
	private void initevent(){
		titlePopup.setItemOnClickListener(new OnItemOnClickListener(){

			@Override
			public void onItemClick(ActionItem item, int position) {
					switch(position){
					case 0:
						uploadpic();
					break;
					case 1:
						showdialog();
					break;
					}
				
			}
			
		});
	}
	private void uploadpic(){
		new SelectPopuWindow(context,parentview);
	}
	@SuppressLint("InflateParams")
	public void showdialog(){
		    //获取自定义布局  
		    LayoutInflater layoutInflater = getLayoutInflater();  
		    View mainView= layoutInflater.inflate(R.layout.edit_userinfo, null);
		    final EditText username = (EditText) mainView.findViewById(R.id.username);
		    final userinfo info=new userinfo();
			final RadioButton man = (RadioButton) mainView.findViewById(R.id.male);
			Calendar c = Calendar.getInstance();
			int mYear=1996;
			int mMonth=0;
			int mDay=1;
			int norYear = c.get(Calendar.YEAR);
			int curYear = mYear;
			int curMonth =mMonth+1;
			int curDate = mDay;
			final WheelView year = (WheelView) mainView.findViewById(R.id.year);
			year.setAdapter(new NumericWheelAdapter(1930, norYear));
			year.setLabel("年");
			year.setCyclic(true);
			final WheelView month = (WheelView) mainView.findViewById(R.id.month);
			month.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
			month.setLabel("月");
			month.setCyclic(true);
			final WheelView day = (WheelView) mainView.findViewById(R.id.day);
			initDay(curYear,curMonth,day);
			day.setLabel("日");
			day.setCyclic(true);
			year.setCurrentItem(curYear - 1930);
			month.setCurrentItem(curMonth - 1);
			day.setCurrentItem(curDate - 1);
		    //创建对话框  
		    new AlertDialog.Builder(context)  
		    .setTitle("资料填补")//设置对话框标题  
		    .setView(mainView)//为对话框添加组件  
		    .setPositiveButton("确定",new DialogInterface.OnClickListener(){  
		        public void onClick(DialogInterface dialog, int which) {  
		           if(man.isChecked()){
		        	   info.sexcode="1";
		           }else{
		        	   info.sexcode="2";
		           }
		           info.username=username.getText().toString();
		           String birthday=new StringBuilder().append((year.getCurrentItem()+1930)).append("-").append((month.getCurrentItem() + 1)).append("-").append((day.getCurrentItem()+1)).toString();
		           info.biryear=birthday.substring(0,birthday.indexOf("-"));
		           info.birday=birthday.substring(birthday.lastIndexOf("-")+1);
		           info.birmonth=birthday.substring(birthday.indexOf("-")+1,birthday.lastIndexOf("-"));
		           if(info.username!=null&&info.username.length()!=0){
		           edituserinfo(info);
		           }
		        }

				
		    })//设置对话框[肯定]按钮  
		    .setNegativeButton("取消", null)//设置对话框[否定]按钮  
		    .show();  
	}
	@SuppressLint({ "SimpleDateFormat", "UseValueOf" })
	public  final String calculateDatePoor(String birthday) {
		try {
			if (TextUtils.isEmpty(birthday))
				return "0";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthdayDate = sdf.parse(birthday);
			String currTimeStr = sdf.format(new Date());
			Date currDate = sdf.parse(currTimeStr);
			if (birthdayDate.getTime() > currDate.getTime()) {
				return "0";
			}
			long age = (currDate.getTime() - birthdayDate.getTime())
					/ (24 * 60 * 60 * 1000) + 1;
			String year = new DecimalFormat("0.00").format(age / 365f);
			if (TextUtils.isEmpty(year))
				return "0";
			return String.valueOf(new Double(year).intValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "0";
	}
	class userinfo{
		private String sexcode;
		private String birday;
		private String biryear;
		private String birmonth;
		private String username;
		@Override
		public String toString() {
			return "userinfo [sexcode=" + sexcode + ", birday=" + birday
					+ ", biryear=" + biryear + ", birmonth=" + birmonth
					+ ", username=" + username + "]";
		}
		
		
	}
	private void edituserinfo(final userinfo info){
		if(AppUtils.checkNetwork(context)==true){
			List<NameValuePair> allP=new ArrayList<NameValuePair>();
			allP.add(new BasicNameValuePair("profilesubmit","true")); 
			allP.add(new BasicNameValuePair("formhash",MySharedPreferenceDb.getformhash())); 
			Log.e("formhash",MySharedPreferenceDb.getformhash());
			allP.add(new BasicNameValuePair("name",info.username)); 
			allP.add(new BasicNameValuePair("sex",info.sexcode)); 
			allP.add(new BasicNameValuePair("birthyear",info.biryear)); 
			allP.add(new BasicNameValuePair("birthmonth",info.birmonth)); 
			allP.add(new BasicNameValuePair("birthday",info.birday)); 
			new	HttpUtil().doPost(HttpConstants.HTTP_EDIT_USEINFO.replace("!",message.get(1).toString()), allP, new ResultCallback() {

				@SuppressLint("NewApi")
				@Override
				public void getReslt(String result) {
					// TODO 自动生成的方法存根
					if(!result.isEmpty() ){
						
						BaseBean b=JSON.parseObject(result, BaseBean.class);
						Log.e("result", result);
						if("0".equals(b.getCode().toString().trim())){
							Toast.makeText(context, b.getMsg(), Toast.LENGTH_LONG).show();
							
							MySharedPreferenceDb.setage(calculateDatePoor(info.biryear+"-"+info.birmonth+"-"+info.birday));
							MySharedPreferenceDb.setname(info.username);
							name.setText(info.username);
							age.setText(calculateDatePoor(info.biryear+"-"+info.birmonth+"-"+info.birday));
							if(sex.getText().toString()=="未设置"||sex.getText().toString().isEmpty()){
								switch(info.sexcode){
								case "1":
									sex.setText("男");
									break;
								case "2":
									sex.setText("女");
									break;
								}
							
							}
							
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
	private void initDay(int arg1, int arg2,WheelView day) {
		day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
	}
	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}
	public class SelectPopuWindow extends PopupWindow {

		private Context context;

		@SuppressWarnings("deprecation")
		public SelectPopuWindow(Context mContext, View parent) {

			this.context = mContext;
			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			
			/**
			 * 拍照
			 */
			bt1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(openCameraIntent, 1);
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					if (SdCardUtil.checkSdCard() == true) {
						Intent intent = new Intent(Intent.ACTION_PICK);
						intent.setType("image/*");// 相片类型
						startActivityForResult(intent, 2);
					} else {
						Toast.makeText(context, "SD卡不存在",
								Toast.LENGTH_LONG).show();
					}

					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}
	
	

	public void saveImageToFile(Bitmap bitmap) {
		SdCardUtil.createFileDir(SdCardUtil.FILEDIR+"/"+"/icon" + "/");
		FileOutputStream fos = null;
		String fileName = SdCardUtil.getSdPath() + SdCardUtil.FILEDIR + "/"
				+ "/icon" + "/" + "userIcon"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg";

		File f = new File(fileName);
		if (f.exists()) {
			f.delete();
		}
		try {
			fos = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				if (bitmap != null) {
					bitmap.recycle();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				geticoninfo(fileName);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int respCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, respCode, data);

		if (requestCode == 1 && respCode == RESULT_OK) {
			
			Uri uri = null;
			if (data != null) {
				uri = data.getData();
				System.out.println("Data");
			}else{
				uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
			}
			
			cropImage(uri, 120, 120, 3);
			
			
		} else if (requestCode == 2 && respCode == RESULT_OK) {
		
			Uri uri = data.getData();
			try {
				
				cropImage(uri, 120, 120, 3);
	
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if(requestCode==3 && respCode==RESULT_OK){
			Bitmap photo = null;
			Uri photoUri = data.getData();
			if (photoUri != null) {
				photo = BitmapFactory.decodeFile(photoUri.getPath());
			}
			if (photo == null) {
				Bundle extra = data.getExtras();
				if (extra != null) {
	                photo = (Bitmap)extra.get("data");  
	                ByteArrayOutputStream stream = new ByteArrayOutputStream();  
	                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
	            }  
			}
			if(photo!=null){
				
				Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(photo, 64, 64);
				icon.setImageBitmap(resizeBmp);
				saveImageToFile(photo);
			}
			
		}
	}
	//截取图片
	public void cropImage(Uri uri, int outputX, int outputY, int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");  
        intent.setDataAndType(uri, "image/*");  
        intent.putExtra("crop", "true");  
        intent.putExtra("aspectX", 1);  
        intent.putExtra("aspectY", 1);  
        intent.putExtra("outputX", outputX);   
        intent.putExtra("outputY", outputY); 
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);  
	    startActivityForResult(intent, requestCode);
	}
	private void geticoninfo(final String filename){
		if(AppUtils.checkNetwork(context)==true){
			List<NameValuePair> allP=new ArrayList<NameValuePair>();
			allP.add(new BasicNameValuePair("m_auth",message.get(1).toString())); 
		
			
			
			new	HttpUtil().doPost(HttpConstants.HTTP_GET_AVATAR, allP, new ResultCallback() {

				@SuppressLint("NewApi")
				@Override
				public void getReslt(String result) {
					// TODO 自动生成的方法存根
					if(!result.isEmpty() ){
						BaseBean b=JSON.parseObject(result, BaseBean.class);
						Log.e("result", result);
						if("0".equals(b.getCode().toString().trim())){
							JSONObject js=(JSONObject) JSON.parse(b.getData());
							JSONObject uc_avatar=(JSONObject) JSON.parse(js.getString("uc_avatar"));
							String appid=uc_avatar.getString("appid");
							String input=uc_avatar.getString("input");
							String agent=uc_avatar.getString("agent");
							String avatartype=uc_avatar.getString("avatartype");
							upload(appid, input, agent, avatartype,filename);
							
						}else{
							Toast.makeText(context, "上传失败", Toast.LENGTH_LONG).show();
						
						}
					
						}else{
							Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
							
						}
					
				}
				
				
			});}else{
				Toast.makeText(context, "网络连接断开", Toast.LENGTH_LONG).show();
			}
	}
	private void upload(String appid,String input, String agent,String avatartype,String filename ){
		if(AppUtils.checkNetwork(context)==true){
		final File f=new File(filename);
		String posturl=HttpConstants.HTTP_UPLOAD_AVATAR.replace("!", agent).replace("@", avatartype).replace("#", input).replace("$", appid);
		new UploadPicture(posturl, null, null, "Filedata", f,new ResultCallback() {

			@SuppressLint("NewApi")
			@Override
			public void getReslt(String result) {
				// TODO 自动生成的方法存根
				if(!result.isEmpty() ){
					JSONObject js=(JSONObject) JSON.parse(result);
					if("0".equals(js.getString("code").trim())){
						
						getusericon();
						DataCleanManager.delete(f);
						Toast.makeText(context, "上传成功", Toast.LENGTH_LONG).show();
						
					}else{
						Toast.makeText(context, "上传失败", Toast.LENGTH_LONG).show();
					
					}
				
					}else{
						Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
						
					}
				
			}
			
			
		}).PicPost(); 
		}else{
			Toast.makeText(context, "网络连接断开", Toast.LENGTH_LONG).show();
		}
	}
	private void getusericon(){
		if(AppUtils.checkNetwork(context)==true){
			List<NameValuePair> allP=new ArrayList<NameValuePair>();
			allP.add(new BasicNameValuePair("m_auth",message.get(1).toString())); 
			new	HttpUtil().doPost(HttpConstants.HTTP_GET_USERICON.replace("!", message.get(1).toString()), allP, new ResultCallback() {

				@SuppressLint("NewApi")
				@Override
				public void getReslt(String result) {
					// TODO 自动生成的方法存根
					if(!result.isEmpty() ){
						BaseBean b=JSON.parseObject(result, BaseBean.class);
						Log.e("result", result);
						if("0".equals(b.getCode().toString().trim())){
							JSONObject js=(JSONObject) JSON.parse(b.getData());
							String url =js.getString("avatar_url");
							MySharedPreferenceDb.setAVATOR(url);
							File cacheDir = StorageUtils.getOwnCacheDirectory(context, SdCardUtil.FILEDIR+"/"+SdCardUtil.FILECACHE);   
							DataCleanManager.delete(cacheDir);
						}else{
							Toast.makeText(context, "获取失败", Toast.LENGTH_LONG).show();
						
						}
					
						}else{
							Toast.makeText(context, "服务器响应失败", Toast.LENGTH_LONG).show();
							
						}
					
				}
				
				
			});}else{
				Toast.makeText(context, "网络连接断开", Toast.LENGTH_LONG).show();
			}
	}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	           
	           finish();
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	@Override
	protected void onDestroy() {
		System.gc();
		super.onDestroy();
	}
}
