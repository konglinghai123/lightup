package com.zhy.Activity;

import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.R.color;
import com.zhy.view.TitleBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class AboutActivity extends Activity {
	private TitleBar title;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		title=(TitleBar) findViewById(R.id.about_TitleBar);
		title.setBackgroundColor(getResources().getColor(R.color.blue));
		title.showLeft("关于我们",getResources().getDrawable(R.drawable.app_back), new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
				
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
