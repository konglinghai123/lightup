package com.zhy.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList; 

import com.dawnlightning.ucqa.R;
import com.zhy.Db.SharedPreferenceDb;

import android.content.Intent; 
import android.content.SharedPreferences; 
import android.os.Parcelable; 
import android.support.v4.view.PagerAdapter; 
import android.support.v4.view.ViewPager; 
import android.support.v4.view.ViewPager.OnPageChangeListener; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.View.OnClickListener;
import android.view.ViewGroup; 
import android.view.ViewGroup.LayoutParams; 
import android.widget.Button; 
import android.widget.ImageView; 
import android.widget.LinearLayout;
  
public class GuideActivity extends Activity { 
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
    /** Called when the activity is first created. */
    @SuppressLint("InflateParams")
	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        mySharedPreferenceDb=new SharedPreferenceDb(this);
         if(mySharedPreferenceDb.getIsFirstInstall()){
        	 Intent i=new Intent();
        	 i.setClass(this, LoginActivity.class);
        	 startActivity(i);
        	 finish();
        	
         }else{
        //将要分页显示的View装入数组中 
        LayoutInflater inflater = getLayoutInflater(); 
        pageViews = new ArrayList<View>(); 
        pageViews.add(inflater.inflate(R.layout.viewpager_page1, null)); 
        pageViews.add(inflater.inflate(R.layout.viewpager_page2, null)); 
        pageViews.add(inflater.inflate(R.layout.viewpager_page3, null)); 
        
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
                imageViews[i].setBackgroundResource(R.drawable.ch_is_select); 
            }else{ 
                imageViews[i].setBackgroundResource(R.drawable.ch_no_select); 
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
                imageViews[position].setBackgroundResource(R.drawable.ch_is_select); 
                //不是当前选中的page，其小圆点设置为未选中的状态 
                if(position !=i){ 
                    imageViews[i].setBackgroundResource(R.drawable.ch_no_select); 
                } 
            } 
              
        } 
    }


	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		setContentView(R.layout.viewnull);
		viewPager=null;
	}    
 } 

