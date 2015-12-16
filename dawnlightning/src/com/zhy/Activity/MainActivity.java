package com.zhy.Activity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.http.NameValuePair;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import com.dawnlightning.ucqa.R;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.Frag.ConsultFrag;
import com.zhy.Frag.MainFragment;
import com.zhy.Frag.MessageFragment;
import com.zhy.Frag.MyFragmentPagerAdapter;
import com.zhy.Push.ExampleUtil;
import com.zhy.Update.UpdateManager;
import com.zhy.Util.DataCleanManager;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.SdCardUtil;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;

import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("HandlerLeak")
public class MainActivity extends BaseActivity {
	private ViewPager mViewPager;// 用来放置界面切换
	
	private ArrayList<Fragment> mViews = new ArrayList<Fragment>();// 用来存放Tab01-04
	private Context context;
	public static ArrayList<String> userinfo;
	private MessageReceiver mMessageReceiver;//推送
	// 两个按钮
	private ImageView me;
	private ImageView consult;
	private ImageView message;
	private TextView count;
	public static boolean isForeground = false;
	
	private MyFragmentPagerAdapter adapter; 
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	private static final int MSG_SET_ALIAS = 1001;
	
	private static final String TAG = "JPush";
	private int currentindex=0;

	 private static boolean isExit = false;
	 ConsultFrag frgconsult;
	 Fragment MainFragment;
	 MessageFragment frmessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		initdata();
		initView();
		initViewPage();
		
		
	}
	private void initdata(){
		context=this;
		Intent intent=getIntent();
		userinfo = intent.getStringArrayListExtra("user");
	}
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpage);	
		// 初始化四个按钮
		count=(TextView) findViewById(R.id.top_message_count);
		me = (ImageView) findViewById(R.id.me);
		consult = (ImageView) findViewById(R.id.consult);
		message=(ImageView) findViewById(R.id.message);
		
	}
	private void initViewPage() {
		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		 frgconsult=new ConsultFrag("全部", context, userinfo,HttpConstants.HTTP_CONSULT_ALL,allP);
		 MainFragment=new MainFragment(context,userinfo);
		 frmessage=new MessageFragment(context,userinfo,count);
		 mViews.add(frgconsult);
		 mViews.add(frmessage);
		 mViews.add(MainFragment);
		 adapter= new MyFragmentPagerAdapter(getSupportFragmentManager(), mViews);
		 mViewPager.setAdapter( adapter);
		 mViewPager.setCurrentItem(currentindex);
		 mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO 自动生成的方法存根
				if(arg0==0){
					consult.setSelected(true);
					me.setSelected(false);
					message.setSelected(false);
				}else if(arg0==1){
					consult.setSelected(false);
					message.setSelected(true);
					me.setSelected(false);
				}else if(arg0==2){
					message.setSelected(false);
					consult.setSelected(false);
					me.setSelected(true);
				}
				
			}
			 
		 });
		 this.consult.setSelected(true);
	
	}
	private void initpushservice(){
		if(userinfo!=null){
			
			registerMessageReceiver();
			//调用JPush API设置Alias
			mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, userinfo.get(2)));
			
			}

	}
	@Override
	protected void onStart() {
		try{
			JPushInterface.init(getApplicationContext());// 初始化 JPush
			JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
			}catch(Exception e){
				
			}
		initpushservice();
		super.onStart();
	}
	@Override
	protected void onResume() {
		isForeground = true;
		JPushInterface.onResume(context);
		super.onResume();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		JPushInterface.onPause(context);
		super.onPause();
	}


	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				
				
				
			}
		}
	}
	

	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i(TAG, logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i(TAG, logs);
                if (ExampleUtil.isConnected(getApplicationContext())) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                } else {
                	Log.i(TAG, "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
              
            }
            
         
        }
	    
	};
	 private final Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(android.os.Message msg) {
	            super.handleMessage(msg);
	            switch (msg.what) {
	            case MSG_SET_ALIAS:
	                Log.d(TAG, "Set alias in handler.");
	                JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
	                break;
	            default:
	                Log.i(TAG, "Unhandled msg - " + msg.what);
	            }
	        }
	    };
	    public void onTabClicked(View view) {
	        int index=0;
			switch (view.getId()) {
	        case R.id.re_weixin:
	            index = 0;
	            break;
	        case R.id.re_message:
	        	index=1;
	        	break;
	        case R.id.re_profile:
	            index = 2;
	            break;

	        }
			
			 mViewPager.setCurrentItem(index);
			 if(index==0){
					consult.setSelected(true);
					me.setSelected(false);
					message.setSelected(false);
				}else if(index==1){
					consult.setSelected(false);
					message.setSelected(true);
					me.setSelected(false);
				}else if(index==2){
					message.setSelected(false);
					consult.setSelected(false);
					me.setSelected(true);
				}
	      
	    }
	    @SuppressLint("HandlerLeak")
		@Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	            exit();
	            return false;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
		
		 private void exit() {
		        if (!isExit) {
		            isExit = true;
		            Toast.makeText(getApplicationContext(), "再按一次退出程序",
		                    Toast.LENGTH_SHORT).show();
		            // 利用handler延迟发送更改状态信息
		            ExitHandler.sendEmptyMessageDelayed(0, 2000);
		           
		        } else {
		        	File cacheDir = StorageUtils.getOwnCacheDirectory(context, SdCardUtil.FILEDIR+"/"+SdCardUtil.FILECACHE);   
					DataCleanManager.delete(cacheDir);
		            finish();
		            System.exit(0);
		          
		        }
		    }
		 @SuppressLint("HandlerLeak")
		Handler ExitHandler = new Handler() {

		        @Override
		        public void handleMessage(Message msg) {
		            super.handleMessage(msg);
		            isExit = false;
		        }
		    };
		
	
		   
		  
}
