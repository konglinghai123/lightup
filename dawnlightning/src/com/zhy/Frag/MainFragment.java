package com.zhy.Frag;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileOutputStream;

import java.util.ArrayList;


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
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Update.UpdateManager;

import com.zhy.Util.SdCardUtil;

import com.zhy.View.TitleBar;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment
{

	
	private Context context;
	private ArrayList<String> userinfo;
	private TitleBar titlebar;
	private ImageView usericon;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private View myconsult;
	private View relogin;
	private View info;
	private View about;
	private View update;
	private TextView username;
	private  SharedPreferenceDb mySharedPreferenceDb=null;
	private UpdateManager updatemanager;
	public final static int UPDATA_CLIENT=0;
	public final static int UPDATA_NO=1;
	public final static int UPDATA_ERROR=2;
	private Boolean IsUpdate=false;
	private TextView versioncode;
	private TextView updatestatus;
	public MainFragment(Context context,ArrayList<String> userinfo)
	{
		
		this.context=context;
		this.userinfo=userinfo;
		this.mySharedPreferenceDb=new  SharedPreferenceDb(context);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{	 initview();
		super.onActivityCreated(savedInstanceState);
		
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_profile, null);
		
		return view;
	}

	@SuppressLint("InflateParams")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		updatemanager=new UpdateManager(context, handler);
		updatemanager.checkUpdate();
		super.onCreate(savedInstanceState);
	}
	private void initview(){
		
		
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, SdCardUtil.FILECACHE);  
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
	    // default
	    .threadPriority(Thread.NORM_PRIORITY - 2)
	    .denyCacheImageMultipleSizesInMemory()
	    .discCacheFileNameGenerator(new Md5FileNameGenerator())
	    .tasksProcessingOrder(QueueProcessingType.LIFO)
	    .denyCacheImageMultipleSizesInMemory()
	    .memoryCache(new LruMemoryCache((int) (6 * 1024 * 1024)))
	    .memoryCacheSize((int) (2 * 1024 * 1024))
	    .memoryCacheSize(13)
	    // default
	    .discCache(new UnlimitedDiscCache(cacheDir))
	    .discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
	    .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
	    .build();
		imageLoader.init(config);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.defalut)
				.showImageOnFail(R.drawable.defalut).cacheInMemory().cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(20)).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		 username=(TextView) getView().findViewById(R.id.tv_name);
		 username.setText(userinfo.get(0));
		 titlebar=(TitleBar) getView().findViewById(R.id.TitleBar3);
		 titlebar.setBackgroundColor(getResources().getColor(R.color.blue));
		 titlebar.showLeft("我", null, new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		 usericon=(ImageView) getView().findViewById(R.id.iv_avatar);
		 imageLoader.loadImage(mySharedPreferenceDb.getuserAVATOR() ,options, new SimpleImageLoadingListener(){

				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					// TODO 自动生成的方法存根
					Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(loadedImage, 80, 80);
					usericon.setImageBitmap(resizeBmp);
				}

			

				
			});
		 myconsult=getView().findViewById(R.id.re_zixun);
		 myconsult.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent();
				i.setClass(context, MyConsultActivity.class);
				i.putStringArrayListExtra("userinfo", userinfo);
				context.startActivity(i);
				
			}
		});
		 relogin=getView().findViewById(R.id.re_denglu);
		 relogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent();
				i.setClass(context, LoginActivity.class);
				new SharedPreferenceDb(context).setuserAutoLogin(false);
				context.startActivity(i);
				getActivity().finish();
				
			}
		});
		 info=getView().findViewById(R.id.re_myinfo);
		 info.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i=new Intent();
				i.setClass(context, UserInfoActivity.class);
				i.putStringArrayListExtra("userinfo", userinfo);
				context.startActivity(i);
				
				
			}
			 
		 });
		 about=getView().findViewById(R.id.re_about);
		 about.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent i=new Intent();
					i.setClass(context, AboutActivity.class);
					context.startActivity(i);
					
					
				}
				 
			 });
		 versioncode=(TextView) getView().findViewById(R.id.versioncode);
		 versioncode.setText("V "+updatemanager.getversionname());
		if(IsUpdate){
			updatestatus=(TextView) getView().findViewById(R.id.update_status);
			updatestatus.setVisibility(View.VISIBLE);
		}
		update=getView().findViewById(R.id.re_updata);
		update.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(IsUpdate){
					updatemanager.showNoticeDialog();
				}else{
					Toast.makeText(context, "当前为最新版本", Toast.LENGTH_SHORT).show();
				}
				
			}
			
		});
	}

	public void saveImageToFile(Bitmap bitmap,String  url){
		SdCardUtil.createFileDir(SdCardUtil.FILEDIR+"/"+SdCardUtil.FILECACHE+"/");
		//FileOutputStream fos = null;
		String fileName=SdCardUtil.getSdPath()+SdCardUtil.FILEDIR+"/"+SdCardUtil.FILECACHE+"/"+url+".jpg";
		Log.e("fileName",fileName);
	
	
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        int options = 100;
	        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);  
	        while (baos.toByteArray().length / 1024 > 300) {   
	            baos.reset();  
	            options -= 10;  
	            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);  
	        }  
	        try {  
	            FileOutputStream fos = new FileOutputStream(fileName);  
	            fos.write(baos.toByteArray());  
	            fos.flush();  
	            fos.close();
	            mySharedPreferenceDb.setAVATOR("file://"+fileName.trim());
	        } catch (Exception e) { 
	        	Log.e("Exception",e.toString());
	            e.printStackTrace();  
	        }  
	}
	@Override
	public void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		if(IsUpdate){
			updatestatus=(TextView) getView().findViewById(R.id.update_status);
			updatestatus.setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	public void onResume() {
		
		super.onResume();
		if(IsUpdate){
			updatestatus=(TextView) getView().findViewById(R.id.update_status);
			updatestatus.setVisibility(View.VISIBLE);
		}
		imageLoader.displayImage(mySharedPreferenceDb.getuserAVATOR(), usericon, options);
	
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

	  @SuppressLint("HandlerLeak")
	Handler handler = new Handler(){  
          
	        @Override  
	        public void handleMessage(Message msg) {  
	            // TODO Auto-generated method stub  
	            super.handleMessage(msg);  
	            switch (msg.what) {  
	         
				case UPDATA_CLIENT: 
					if(!mySharedPreferenceDb.getIsUpdateShow())
					{
					updatemanager.showNoticeDialog();
					mySharedPreferenceDb.setIsUpdateShow(true);
					}
					IsUpdate=true;
	                break;  
	            case UPDATA_ERROR:  
	               
	               
	                break;    
	            case UPDATA_NO:  
	              
	                break;    
	            }  
	        }  
	    }; 
	
}
