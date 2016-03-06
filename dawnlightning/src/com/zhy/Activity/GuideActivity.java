package com.zhy.Activity;

import android.annotation.SuppressLint;

import android.os.Bundle;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList; 
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cn.jpush.android.api.JPushInterface;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.R;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.UserDataBean;
import com.zhy.Bean.UserBaseBean;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;

import android.content.Intent; 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable; 
import android.support.v4.view.PagerAdapter; 
import android.support.v4.view.ViewPager; 
import android.support.v4.view.ViewPager.OnPageChangeListener; 
import android.view.KeyEvent;
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.Button; 
import android.widget.ImageView; 
import android.widget.LinearLayout;
import android.widget.Toast;
  
public class GuideActivity extends BaseActivity { 
    private ViewPager viewPager; 
      
    /**装分页显示的view的数组*/
    private ArrayList<View> pageViews;     
    private ImageView imageView; 
      
    /**将小圆点的图片用数组表示*/
    private ImageView[] imageViews; 
   
    
    //包裹滑动图片的LinearLayout 
    private ViewGroup viewPics; 
      
    //包裹小圆点的LinearLayout 
    private ViewGroup viewPoints; 
      
    private SharedPreferenceDb mySharedPreferenceDb;
    
    LayoutInflater inflater;
  
    
    /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
	@SuppressLint({ "InflateParams", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        mySharedPreferenceDb=new SharedPreferenceDb(this);
        mySharedPreferenceDb.setIsUpdateShow(false);
         if(mySharedPreferenceDb.getIsFirstInstall()){
        	 if(mySharedPreferenceDb.getAutoLogin()==false){
        	 Intent i=new Intent();
        	 i.setClass(this, LoginActivity.class);
        	 startActivity(i);
        	 finish();
        	 }else{
        		 if(mySharedPreferenceDb.getName()!=""&&mySharedPreferenceDb.getUserId()!=""){
        			
     				//login(mySharedPreferenceDb.getName(),mySharedPreferenceDb.getUserId());
        			Intent i=new Intent();
      	        	i.setClass(this, WelcomeActivity.class);
      	        	startActivity(i);
      	        	finish();
     			}else{
     				
     				Intent i=new Intent();
     	        	i.setClass(this, LoginActivity.class);
     	        	startActivity(i);
     	        	finish();
     			}
        	 }
        	
         }else{
        //将要分页显示的View装入数组中 
        inflater= getLayoutInflater(); 
        pageViews = new ArrayList<View>(); 
        View view1=inflater.inflate(R.layout.guide_page_1, null);
       
        view1.setBackgroundDrawable(getbmp(R.drawable.guide_1)); 
        
        View view2=inflater.inflate(R.layout.guide_page_2, null);
      
        view2.setBackgroundDrawable(getbmp(R.drawable.guide_2)); 
        
        
       
        View view3=inflater.inflate(R.layout.guide_page_3, null);
        view3.setBackgroundDrawable(getbmp(R.drawable.guide_3)); 
        pageViews.add(view1); 
        pageViews.add(view2); 
        pageViews.add(view3); 
        
        //创建imageviews数组，大小是要显示的图片的数量 
        imageViews = new ImageView[pageViews.size()]; 
        //从指定的XML文件加载视图 
        viewPics = (ViewGroup) inflater.inflate(R.layout.activity_guide, null); 
       // login=(Button) viewPics. findViewById(R.id.guidelogin);
       // register=(Button) viewPics. findViewById(R.id.guideRegister);
        //实例化小圆点的linearLayout和viewpager 
        viewPoints = (ViewGroup) viewPics.findViewById(R.id.viewGroup); 
        viewPager = (ViewPager) viewPics.findViewById(R.id.guidePages); 
       // login.setOnClickListener(new  LoginOnclick());
       // register.setOnClickListener(new RegisterOnclick());
        //添加小圆点的图片 
        for(int i=0;i<pageViews.size();i++){ 
            imageView = new ImageView(GuideActivity.this); 
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(  
            		 20, 20);  
            		layout.setMargins(2, 0, 2, 0); 
            //设置小圆点imageview的参数 
            imageView.setLayoutParams(layout);//创建一个宽高均为20 的布局 
           
            imageView.setPadding(15, 2, 15, 2); 
            //将小圆点layout添加到数组中 
            imageViews[i] = imageView; 
              
            //默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是 
            if(i==0){ 
                imageViews[i].setBackgroundResource(R.drawable.ic_select_pressed); 
            }else{ 
                imageViews[i].setBackgroundResource(R.drawable.ic_select_normal); 
            } 
              
            //将imageviews添加到小圆点视图组 
            viewPoints.addView(imageViews[i]); 
        } 
          
        //显示滑动图片的视图 
        setContentView(viewPics); 
          
        //设置viewpager的适配器和监听事件 
        viewPager.setAdapter(new GuidePageAdapter()); 
        viewPager.setOnPageChangeListener(new GuidePageChangeListener()); 
        }       
    } 
      
    private Button.OnClickListener  Button_OnClickListener = new Button.OnClickListener() { 
        public void onClick(View v) { 
         
              
            //跳转 
            Intent mIntent = new Intent(); 
            mIntent.setClass(GuideActivity.this,LoginActivity.class); 
            mySharedPreferenceDb.setIsFirstInstall();
            startActivity(mIntent); 
          	finish(); 
          
        } 
    };  
  
   
      
      
    class GuidePageAdapter extends PagerAdapter{ 
  
        //销毁position位置的界面 
        @Override
        public void destroyItem(View v, int position, Object arg2) { 
            // TODO Auto-generated method stub 
            ((ViewPager)v).removeView(pageViews.get(position)); 
              
        } 
  
        @Override
        public void finishUpdate(View arg0) { 
            // TODO Auto-generated method stub 
              
        } 
          
        //获取当前窗体界面数 
        @Override
        public int getCount() { 
            // TODO Auto-generated method stub 
            return pageViews.size(); 
        } 
  
        //初始化position位置的界面 
        @Override
        public Object instantiateItem(View v, int position) { 
            // TODO Auto-generated method stub 
            ((ViewPager) v).addView(pageViews.get(position));   
              
             // 测试页卡1内的按钮事件   
            if (position == 2) {   
                Button btn = (Button) v.findViewById(R.id.btn_close_guide);   
                btn.setOnClickListener(Button_OnClickListener);   
            }   
              
            return pageViews.get(position);   
        } 
  
        // 判断是否由对象生成界面 
        @Override
        public boolean isViewFromObject(View v, Object arg1) { 
            // TODO Auto-generated method stub 
            return v == arg1; 
        } 
  
  
  
        @Override
        public void startUpdate(View arg0) { 
            // TODO Auto-generated method stub 
              
        } 
  
        @Override
        public int getItemPosition(Object object) { 
            // TODO Auto-generated method stub 
            return super.getItemPosition(object); 
        } 
  
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) { 
            // TODO Auto-generated method stub 
              
        } 
  
        @Override
        public Parcelable saveState() { 
            // TODO Auto-generated method stub 
            return null; 
        } 
    } 
      
      
    class GuidePageChangeListener implements OnPageChangeListener{ 
  
        @Override
        public void onPageScrollStateChanged(int arg0) { 
            // TODO Auto-generated method stub 
              
        } 
  
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) { 
            // TODO Auto-generated method stub 
              
        } 
  
        @Override
        public void onPageSelected(int position) { 
            // TODO Auto-generated method stub 
            for(int i=0;i<imageViews.length;i++){ 
                imageViews[position].setBackgroundResource(R.drawable.ic_select_pressed); 
                //不是当前选中的page，其小圆点设置为未选中的状态 
                if(position !=i){ 
                    imageViews[i].setBackgroundResource(R.drawable.ic_select_normal); 
                } 
            } 
              
        } 
    }
    private BitmapDrawable getbmp( int resId){
    BitmapFactory.Options opt = new BitmapFactory.Options();

    opt.inPreferredConfig = Bitmap.Config.RGB_565;

    opt.inPurgeable = true;

    opt.inInputShareable = true;

    //获取资源图片
	InputStream is = getResources().openRawResource(resId);

    Bitmap bitmap = BitmapFactory.decodeStream(is,null, opt);

    try {
		is.close();
	} catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}

    return new BitmapDrawable(getResources(),bitmap);
    }
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		System.gc();
	}    
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	           
	           finish();
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	 
	 public void login(final String username,final String password){

			List<NameValuePair> allP=new ArrayList<NameValuePair>();
			allP.add(new BasicNameValuePair("username", username));
			allP.add(new BasicNameValuePair("password",password ));		
			new	HttpUtil().doPost(HttpConstants.HTTP_LOGIN, allP, new ResultCallback() {
				
				@SuppressLint("NewApi")
				@Override
				public void getReslt(String result) {
					// TODO Auto-generated method stub
					//&& "1".equals(result)
					if(!result.isEmpty() ){
					
						BaseBean b=JSON.parseObject(result, BaseBean.class);
						
						if("0".equals(b.getCode())){
							
							UserBaseBean dt=JSON.parseObject(b.getData(), UserBaseBean.class);
						
							UserDataBean u=JSON.parseObject(dt.getSpace(),UserDataBean.class);
							ArrayList<String> userinfo =new ArrayList<String>();
							userinfo.add(u.getUsername());
							userinfo.add(dt.getM_auth());
							userinfo.add(u.getUid());
						
							
							mySharedPreferenceDb.setuserAVATOR(u.getAvatar_url());
							mySharedPreferenceDb.setformhash(dt.getFormhash());
							mySharedPreferenceDb.setage(u.getAge());
							mySharedPreferenceDb.setsex(u.getSex());
							mySharedPreferenceDb.setname(u.getName());
							
							Intent intent=new Intent();
							intent.putStringArrayListExtra("user",userinfo);
						
							intent.setClass(GuideActivity.this, MainActivity.class);
							startActivity(intent);
							
							finish();
						
							
						}else{
						
							Toast.makeText(GuideActivity.this, b.getMsg(),Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(GuideActivity.this, "服务器响应失败", Toast.LENGTH_SHORT).show();
						
					}
				}
			});
		}
		@Override
		protected void onResume() {
			
			JPushInterface.onResume(GuideActivity.this);
			super.onResume();
		}


		@Override
		protected void onPause() {
			
			JPushInterface.onPause(GuideActivity.this);
			super.onPause();
		}

 } 

