package com.zhy.Activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.R;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.UserBean;
import com.zhy.Bean.UserBeanData;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

public class WelcomeActivity extends Activity{
	private SharedPreferenceDb mySharedPreferenceDb;
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about);  
        mySharedPreferenceDb=new SharedPreferenceDb(this);
        //延迟两秒后执行run方法中的页面跳转  
        new Handler().postDelayed(new Runnable() {  
  
            @Override  
            public void run() {
            	login(mySharedPreferenceDb.getName(),mySharedPreferenceDb.getUserId());
               
               
               
            }  
        }, 2000);  
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
							
							UserBeanData dt=JSON.parseObject(b.getData(), UserBeanData.class);
						
							UserBean u=JSON.parseObject(dt.getSpace(),UserBean.class);
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
						
							intent.setClass(WelcomeActivity.this, MainActivity.class);
							startActivity(intent);
							
							finish();
						
							
						}else{
						
							Toast.makeText(WelcomeActivity.this, b.getMsg(), Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(WelcomeActivity.this, "服务器响应失败", Toast.LENGTH_SHORT).show();
						
					}
				}
			});
		}
}
