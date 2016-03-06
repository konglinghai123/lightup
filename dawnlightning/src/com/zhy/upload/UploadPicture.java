package com.zhy.Upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.zhy.Util.HttpUtil;
import com.zhy.Util.ResultCallback;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TimeFormatException;

public class UploadPicture  {
	private String urlStr;
	private Map<String,String> myparams;
	private Map<String, String> headers;
	private String filename;
	private File file;
	public static final String server_time_out = "服务器连接超时";
	public static final String connect_failed= "网络连接失败";
	public static final String connect_server_failed= "服务器连接失败";
	public static final String please_check_network= "网络未连接";
	public ResultCallback resultCallback=new ResultCallback() {
		
		@SuppressLint("HandlerLeak")
		@Override
		public void getReslt(String result) {
			// TODO Auto-generated method stub
		}
	};
	@SuppressLint("HandlerLeak")
	Handler myHandler=new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			
			if(msg.what==1){
				resultCallback.getReslt("1");
			}else if(msg.what==0){
				String result=(String) msg.obj;
				resultCallback.getReslt(result);
			}
		};
	};
	public void PicPost(){
		new Thread(new Runnable() {
		
		@SuppressLint("NewApi")
		@Override
		public void run() {
		
			try {
				HttpClient client = HttpUtil.getHttpClient();
				HttpPost httpPost = new HttpPost(urlStr);
				HttpEntity entity;
				Message message=new Message();
				if(file == null && myparams != null){
					ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
					if(myparams != null){
						Set<String> keys = myparams.keySet();
						for(Iterator<String> i = keys.iterator(); i.hasNext();) {
							String key = i.next();
							if(myparams.get(key) == null) continue;
							pairs.add(new BasicNameValuePair(key, myparams.get(key)));
							Log.i("TAG", "postForm params:"+key+"="+myparams.get(key));
						}
					}
					entity = new UrlEncodedFormEntity(pairs, "utf-8");
				}else {
//					if(progressListener == null){
//						entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//					}else{
//						entity = new ProgressMultiPartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, progressListener);
//					}
					entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
					if(myparams != null){
						Set<String> keys = myparams.keySet();
						for(Iterator<String> i = keys.iterator(); i.hasNext();) {
							String key = i.next();
							((MultipartEntity)entity).addPart(key, new StringBody(myparams.get(key), Charset.forName("utf-8")));
							//Log.i("TAG", "postForm params:"+key+"="+params.get(key));
						}
					}
					if(file != null){
						
							((MultipartEntity)entity).addPart(filename, new FileBody(file));
						
					}
				}

				if(headers != null){
					Iterator<Entry<String, String>> it = headers.entrySet().iterator();
					while(it.hasNext()){
						Entry<String,String> entry = it.next();
						//Log.i(TAG, "addHeader: "+entry.getKey()+"="+entry.getValue());
						httpPost.addHeader(entry.getKey(), entry.getValue());
					}
				}
				
				httpPost.setEntity(entity);
				Log.i("TAG", "post总字节数:"+entity.getContentLength());
				
				HttpResponse response = client.execute(httpPost);
			
				
				if(response.getStatusLine().getStatusCode()==200){
					String result = EntityUtils.toString(response.getEntity());
					Log.e("msg",result);
					if(!result.isEmpty()){
					
						message.what=0;
						message.obj=result;
						myHandler.sendMessage(message);
					}else{
					
						message.what=1;
						myHandler.sendMessage(message);
					}
				}else{
					message.what=1;
					myHandler.sendMessage(message);
				}

			}catch(TimeFormatException e){
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
				if(e instanceof SocketTimeoutException){
					
				}else if(e instanceof ConnectException
						|| e instanceof SocketException){
				
				}else if(e instanceof FileNotFoundException){
					
				}else{
					
				}
			}
			
		
		}}).start();
	}
	public UploadPicture(String urlStr, Map<String, String> myparams,
			Map<String, String> headers, String filename, File file,
			ResultCallback resultCallback) {
		super();
		this.urlStr = urlStr;
		this.myparams = myparams;
		this.headers = headers;
		this.filename = filename;
		this.file = file;
		this.resultCallback = resultCallback;
	}

}
