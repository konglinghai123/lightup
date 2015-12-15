package com.zhy.Activity;


import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.alibaba.fastjson.JSON;
import com.zhy.Bean.BaseBean;
import com.zhy.Bean.UserBean;
import com.zhy.Bean.UserBeanData;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.AppUtils;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.MoodApplication;
import com.zhy.Util.ResultCallback;
import com.dawnlightning.ucqa.R;
import com.zhy.view.EditTextWithDel;
import com.zhy.view.TitleBar;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class LoginActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout tvRegister;
	
	private Button btnLogin;
	
	private EditTextWithDel etPhone;
	
	private EditTextWithDel etPassWord;
	
	private TextView noRegister;
	
	private CheckBox IsSave;
	
	private String  CancelLogin="no";
	
	
	private SharedPreferenceDb mySharedPreferenceDb=null;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	
		setContentView(R.layout.userlogin);
		 initViews();
		Intent recintent=getIntent();
		if(recintent!=null){
			CancelLogin=recintent.getStringExtra("cancel");
		}
		mySharedPreferenceDb=new SharedPreferenceDb(this);
		
	
		 etPhone.setText(mySharedPreferenceDb.getName());
		 etPassWord.setText(mySharedPreferenceDb.getUserId());
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		System.gc();
	}

	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		
	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		TitleBar titleBar=(TitleBar)findViewById(R.id.TitleBar);
		
		titleBar.setBackgroundColor(getResources().getColor(R.color.blue));
		
		 titleBar.showLeft("登陆", null, new OnClickListener() {
				
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
	
		// tvRegister=(RelativeLayout)findViewById(R.id.newUserRegister);
		 //tvRegister.setOnClickListener(this);
		Typeface typeFace =Typeface.createFromAsset(getAssets(),"attr/HelveticaNeue.ttf");
		
		 etPhone=(EditTextWithDel)findViewById(R.id.accounts);
		 etPassWord=(EditTextWithDel)findViewById(R.id.password);
		 etPassWord.setTypeface(typeFace,Typeface.BOLD);
		 etPhone.setTypeface(typeFace,Typeface.BOLD);
		
		 //mylogo.setBackgroundDrawable(R.drawable.mylogo);
		 btnLogin=(Button)findViewById(R.id.login);
		 
		 btnLogin.setOnClickListener(this);
		 noRegister=(TextView)findViewById(R.id.not_resigter);
		 noRegister.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
		 noRegister.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i=new Intent();
				i.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(i);
				
			}
			 
		 });
		 IsSave=(CheckBox) findViewById(R.id.ch_is_savepassword);
		 
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		if(view==tvRegister){
			SkipActivityforClass(LoginActivity.this, RegisterActivity.class);
			if(new SharedPreferenceDb(LoginActivity.this).getAnimation()==true){
				overridePendingTransition(R.anim.fade, R.anim.hold);
			}
			
		}else if(view==btnLogin){
			
			if(!etPhone.getText().toString().isEmpty()){
				
				if(!etPassWord.getText().toString().isEmpty()){
					
					
					if(AppUtils.checkNetwork(LoginActivity.this)==true){
						//SkipActivityforClass(LoginActivity.this, MainActivity.class);
						login(etPhone.getText().toString().trim(),etPassWord.getText().toString().trim());
						
						
					}else{
						showToast("亲，您还没有联网了!");
					}
					
				}else{
					showToast("密码不能为空");
				}
			}else{
				showToast("用户名不能为空");
			}
			

			
			
		}
			
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
					Log.e("avater", result);
					BaseBean b=JSON.parseObject(result, BaseBean.class);
					
					if("0".equals(b.getCode())){
						
						UserBeanData dt=JSON.parseObject(b.getData(), UserBeanData.class);
					
						UserBean u=JSON.parseObject(dt.getSpace(),UserBean.class);
						ArrayList<String> userinfo =new ArrayList<String>();
						userinfo.add(u.getUsername());
						userinfo.add(dt.getM_auth());
						userinfo.add(u.getUid());
					
						Log.e("avater", u.getAvatar_url());
						if(IsSave.isChecked()){
							mySharedPreferenceDb.setName( username);
							mySharedPreferenceDb.setUserId(password);
							mySharedPreferenceDb.setuserAutoLogin(true);
						}
						
						if(mySharedPreferenceDb.getAnimation()==true){
							overridePendingTransition(R.anim.slide_left,
									R.anim.slide_right);
						}
						mySharedPreferenceDb.setuserAVATOR(u.getAvatar_url());
						mySharedPreferenceDb.setformhash(dt.getFormhash());
						mySharedPreferenceDb.setage(u.getAge());
						mySharedPreferenceDb.setsex(u.getSex());
						mySharedPreferenceDb.setname(u.getName());
						
						Intent intent=new Intent();
						intent.putStringArrayListExtra("user",userinfo);
					
						intent.setClass(LoginActivity.this, MainActivity_1.class);
						startActivity(intent);
						
						finish();
					
						close();
					}else{
						showToast(b.getMsg());
						close();
						etPhone.setText(username);
						etPassWord.setText(password);
					}
				}else{
					Toast.makeText(LoginActivity.this, "服务器响应失败", Toast.LENGTH_LONG).show();
					close();
				}
			}
		});
	}
	
	public void showToast(String str){
		Toast.makeText(LoginActivity.this, str, Toast.LENGTH_LONG).show();
	}
	
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	           
	           finish();
	        }
	        return super.onKeyDown(keyCode, event);
	    }
}
