package com.zhy.Util;
import java.util.LinkedList;
import java.util.List;




import android.app.Activity;
import android.os.Bundle;

public class MoodApplication extends Activity {

	private List<Activity> allActivity=null;
	
	private static MoodApplication moodApplication=null;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MoodApplication() {
		// TODO Auto-generated constructor stub
		allActivity=new LinkedList();
	}
	
	public static MoodApplication getInstance(){
		if(moodApplication==null){
			moodApplication=new MoodApplication();
		}
		return moodApplication;
	}
	
	public void addActivity(Activity act){
		if(allActivity!=null && allActivity.size()>0){
			if(!allActivity.contains(act)){
				allActivity.add(act);
			}
		}else{
			allActivity.add(act);
		}
	}
	
	public void exit(){
		if(allActivity!=null && allActivity.size()>0){
			for(Activity act:allActivity){
				act.finish();
			}
			
		}
		System.exit(0);
	}
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		new App().init();
	}
	
	
}
