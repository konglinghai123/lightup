package com.zhy.Activity;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.R;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.SecurityCodeBean;
import com.zhy.Bean.UserDataBean;
import com.zhy.Bean.UserBaseBean;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.AppUtils;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;
import com.zhy.View.EditTextWithDel;
import com.zhy.View.TitleBar;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import android.widget.Toast;



@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class RegisterActivity extends BaseActivity implements OnClickListener,OnCheckedChangeListener {


	private Context context;
	private EditTextWithDel etPhone;
	
	private EditTextWithDel etPwd;

	
	private EditTextWithDel etSecorndpwd;
	SecurityCodeBean sb;//验证码类
	

	
	private Button btnRegister;
	
	

	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_register);
		context=this;
		initViews();
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		TitleBar titleBar=(TitleBar)findViewById(R.id.titleBar);
		 if(!"0".equals(String.valueOf(new SharedPreferenceDb(RegisterActivity.this).getChangeTheme()))){
			 titleBar.setBackgroundColor(new SharedPreferenceDb(RegisterActivity.this).getChangeTheme());
		 }else{
			 titleBar.setBackgroundColor(getResources().getColor(R.color.blue));
		 }
		 titleBar.showLeft("注册", getResources().getDrawable(R.drawable.app_back), new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
	}
	
	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		
		
		
		etPhone=(EditTextWithDel)findViewById(R.id.accounts);
		etPwd=(EditTextWithDel)findViewById(R.id.password);
		etSecorndpwd=(EditTextWithDel)findViewById(R.id.secondpassword);
		
	
		btnRegister=(Button)findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
	
		if(AppUtils.checkNetwork(RegisterActivity.this)==true){
		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		new	HttpUtil().doPost(HttpConstants.HTTP_FRIST_REGISTER, allP, new ResultCallback() {

			@Override
			public void getReslt(String result) {
				// TODO 自动生成的方法存根
				if(!result.isEmpty() ){
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					if("0".equals(b.getCode().toString().trim())){
						sb=JSON.parseObject(b.getData(),SecurityCodeBean.class);
						
						
					}else{
						Toast.makeText(RegisterActivity.this, b.getMsg(), Toast.LENGTH_SHORT).show();
						close();
					}
				
					}else{
						Toast.makeText(RegisterActivity.this, "服务器响应失败", Toast.LENGTH_SHORT).show();
						close();
					}
				
			}
			
			
		});}else{
			showToast("亲，您还没有联网了!");
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		 if(arg0==btnRegister){
			
			checkUserInput();
		}
	}

	
	public void checkUserInput(){
		if(!"".equals(etPhone.getText().toString().trim())){
			
		
				
				if(!etPwd.getText().toString().trim().isEmpty()){
					
				
						
						if(AppUtils.isMobileNO(etPhone.getText().toString())){
							if(AppUtils.checkNetwork(RegisterActivity.this)==true){
								if(etPwd.getText().toString().trim().equals(etSecorndpwd.getText().toString().trim()))
								{
								
								      executeHttp();
								
									
								}else{
									showToast("两次密码不一样");
								}
							}else{
								showToast("亲，您还没有联网了!");
							}
						}else{
							showToast("请输出正确的手机号码");
						}
						
				
					
				}else{
					showToast("密码不能为空");
				}
				
		
		}else {
			showToast("用户名不能为空");
		}
	}
	
	
	public void executeHttp(){
		
		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		allP.add(new BasicNameValuePair("phone", etPhone.getText().toString().trim()));
		allP.add(new BasicNameValuePair("password", etPwd.getText().toString().trim()));
		allP.add(new BasicNameValuePair("scpassword",etSecorndpwd.getText().toString().trim()));
		allP.add(new BasicNameValuePair("inputcode",sb.getSeccode()));
		allP.add(new BasicNameValuePair("key", sb.getSeccode_auth().toString().trim()));
		initProgressDialog();
		new	HttpUtil().doPost(HttpConstants.HTTP_REGISTER, allP, new ResultCallback() {

			@Override
			public void getReslt(String result) {
				// TODO 自动生成的方法存根
				
				if(!result.isEmpty() ){
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					if("0".equals(b.getCode().toString().trim())){
						Toast.makeText(RegisterActivity.this, b.getMsg(), Toast.LENGTH_SHORT).show();
						login(etPhone.getText().toString().trim(),etPwd.getText().toString().trim());
						close();
					}else{
						Toast.makeText(RegisterActivity.this, b.getMsg(), Toast.LENGTH_SHORT).show();
						close();
					}
					
				}else{
					Toast.makeText(RegisterActivity.this, "服务器连接失败...", Toast.LENGTH_SHORT).show();
					close();
				}
				
			}
			
			
		});
		
		
	}
	
	public void showToast(String str){
		Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
		
	}
	public void login(final String username,final String password){

		List<NameValuePair> allP=new ArrayList<NameValuePair>();
		allP.add(new BasicNameValuePair("username", username));
		allP.add(new BasicNameValuePair("password",password ));
		
	
		
		initProgressDialog();
		
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
					
						Log.e("avater", u.getAvatar_url());
						
						new SharedPreferenceDb(context).setName( username);
						new SharedPreferenceDb(context).setUserId(password);
						new SharedPreferenceDb(context).setuserAutoLogin(true);
						new SharedPreferenceDb(context).setuserAVATOR(u.getAvatar_url());
						new SharedPreferenceDb(context).setformhash(dt.getFormhash());
						new SharedPreferenceDb(context).setage(u.getAge());
						new SharedPreferenceDb(context).setsex(u.getSex());
						new SharedPreferenceDb(context).setname(u.getName());
						
						Intent intent=new Intent();
						intent.putStringArrayListExtra("user",userinfo);
					
						intent.setClass(context, MainActivity.class);
						startActivity(intent);
						
						finish();
						
						close();
					}else{
						showToast(b.getMsg());
						close();
					
					}
				}else{
					Toast.makeText(context, "服务器响应失败", Toast.LENGTH_SHORT).show();
					close();
				}
			}
		});
	}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	           
	           finish();
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	 
}
