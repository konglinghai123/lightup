package com.zhy.Update;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.zhy.Frag.MainFragment;
import com.zhy.Util.HttpConstants;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Message;

public class UpdateManager {
	 
	
	 private Context mContext;
	 private HashMap<String, String> mHashMap = null;
	 private Handler handler;
	 public UpdateManager(Context context,Handler handler)
	    {
	        this.mContext = context;
	        this.handler=handler;
	    }

	 /**
     * 检测软件更新
     */
    public void checkUpdate()
    {	new Thread(new CheckVersionTask()).start();
    	
    	
    	
    	
    	
    }
	 /**
     * 检查软件是否有更新版本
     * 
     * @return
     */
    
    /* 
     * 从服务器获取xml解析并进行比对版本号  
     */  
    public class CheckVersionTask implements Runnable{  
      
        public void run() {  
            try {  
            	  Message msg = new Message();  
                //从资源文件获取服务器 地址   
                String path = HttpConstants.Update;
                //包装成url的对象   
                URL url = new URL(path);  
                HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
                conn.setConnectTimeout(5000);  
                InputStream is =conn.getInputStream();   
                ParseXmlService service = new ParseXmlService();
                // 获取当前软件版本
                int versionCode = getVersionCode(mContext);
                // 把version.xml放到网络上，然后获取文件信
        		try
                {	
                    mHashMap = service.parseXml(is);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                if (null != mHashMap)
                {
                    int serviceCode = Integer.valueOf(mHashMap.get("version"));
                    // 版本判断
                    if (serviceCode > versionCode)
                    {	msg.what=MainFragment.UPDATA_CLIENT;
                    	handler.sendMessage(msg); 
                    }else{
                    	 msg.what=MainFragment.UPDATA_NO;
                     	handler.sendMessage(msg); 
                    }
                }
               
               
            } catch (Exception e) {  
                // 待处理   
                Message msg = new Message();  
                msg.what =MainFragment.UPDATA_ERROR;  
                handler.sendMessage(msg);  
                e.printStackTrace();  
            }   
        }  
    }  
      
  
    /**
     * 获取软件版本号
     * 
     * @param context
     * @return
     */
    public int getVersionCode(Context context)
    {
        int versionCode = 0;
        try
        {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("com.dawnlightning.ucqa", 0).versionCode;
        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }
    /**
     * 显示软件更新对话框
     */
    public void showNoticeDialog()
    {
        // 构造对话框
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("更新提醒");
        builder.setMessage(mHashMap.get("note"));
        // 更新
        builder.setPositiveButton("更新", new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                Intent intent = new Intent(mContext,UpdateService.class);
    			intent.putExtra("Key_App_Name",mHashMap.get("name"));
    			intent.putExtra("Key_Down_Url",mHashMap.get("url"));						
    			mContext.startService(intent);
            }
        });
        // 稍后更新
        builder.setNegativeButton("稍后更新", new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
    public String getversionname(){
    try {
		return mContext.getPackageManager().getPackageInfo("com.dawnlightning.ucqa", 0).versionName;
	} catch (NameNotFoundException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
		
	}
    	return "";
    }

}
