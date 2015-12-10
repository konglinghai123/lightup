package com.zhy.Util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;



class SSLSocketFactoryEx extends SSLSocketFactory {
	 
    SSLContext sslContext = SSLContext.getInstance("TLS");
 
    public SSLSocketFactoryEx(KeyStore truststore)
            throws NoSuchAlgorithmException, KeyManagementException,
            KeyStoreException, UnrecoverableKeyException {
        super(truststore);
 
        TrustManager tm = new X509TrustManager() {
 
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
 
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain, String authType)
                    throws java.security.cert.CertificateException {
 
            }
 
            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain, String authType)
                    throws java.security.cert.CertificateException {
 
            }
        };
 
        sslContext.init(null, new TrustManager[] { tm }, null);
    }
 
    @Override
    public Socket createSocket(Socket socket, String host, int port,
            boolean autoClose) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(socket, host, port,
                autoClose);
    }
 
    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
    }
}
public class HttpUtil {

	  public static synchronized HttpClient getHttpClient() {
		  
		  HttpClient httpClient = null;
			if (null == httpClient) {
	            // 初始化工作
	            try {
	                KeyStore trustStore = KeyStore.getInstance(KeyStore
	                        .getDefaultType());
	                trustStore.load(null, null);
	                SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
	                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  //允许所有主机的验证
	 
	                HttpParams params = new BasicHttpParams();
	 
	                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	                HttpProtocolParams.setContentCharset(params,
	                        HTTP.DEFAULT_CONTENT_CHARSET);
	                HttpProtocolParams.setUseExpectContinue(params, true);
	 
	                // 设置连接管理器的超时
	                ConnManagerParams.setTimeout(params, 10000);
	                // 设置连接超时
	                HttpConnectionParams.setConnectionTimeout(params, 10000);
	                // 设置socket超时
	                HttpConnectionParams.setSoTimeout(params, 10000);
	 
	                // 设置http https支持
	                SchemeRegistry schReg = new SchemeRegistry();
	                schReg.register(new Scheme("http", PlainSocketFactory
	                        .getSocketFactory(), 80));
	                schReg.register(new Scheme("https", sf, 443));
	 
	                ClientConnectionManager conManager = new ThreadSafeClientConnManager(
	                        params, schReg);
	 
	                httpClient = new DefaultHttpClient(conManager, params);
	            } catch (Exception e) {
	                e.printStackTrace();
	                return new DefaultHttpClient();
	            }
	        }
	        return httpClient;
	    }
	 
	
	 
	
	 private static DefaultHttpClient getNewHttpClient() {
	        try {
	            KeyStore trustStore = KeyStore.getInstance(KeyStore
	                    .getDefaultType());
	            trustStore.load(null, null);
	            
	            SSLSocketFactory sf = new SSLSocketFactory(trustStore);
	            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	            HttpParams params = new BasicHttpParams();
	            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	            SchemeRegistry registry = new SchemeRegistry();
	            registry.register(new Scheme("http", PlainSocketFactory
	                    .getSocketFactory(), 80));
	            registry.register(new Scheme("https", sf, 443));

	            ClientConnectionManager ccm = new ThreadSafeClientConnManager(
	                    params, registry);

	            return new DefaultHttpClient(ccm, params);
	        } catch (Exception e) {
	            return new DefaultHttpClient();
	        }
	    }
	public  void doPost(final String reqUrl,final List<NameValuePair> params,ResultCallback callback){
		
		resultCallback=callback;
		
		new Thread(new Runnable() {
			
			@SuppressLint("NewApi")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Message message=new Message();
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters, 20000);
				HttpConnectionParams.setSoTimeout(httpParameters, 20000+12000);

				HttpPost httpPost =new HttpPost(new AjustUrl().ReturnUrl(reqUrl, params));
				Log.e("posturl",new AjustUrl().ReturnUrl(reqUrl, params));
				httpPost.setParams(httpParameters);
			
				try{
					httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
					HttpResponse httpResponse;
					try{
						//HttpClient client =getNewHttpClient();
						HttpClient client =getHttpClient();
						HttpProtocolParams.setUseExpectContinue(client.getParams(),false);
						HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
							@Override
							public boolean retryRequest(IOException exception,
							    int executionCount,HttpContext context) {
							        if (executionCount >= 5) {
							            // 如果超过最大重试次数，那么就不要继续了
							            return false;
							        }
							        if (exception instanceof NoHttpResponseException) {
							            // 如果服务器丢掉了连接，那么就重试
							            return true;
							        }
							        if (exception instanceof SSLHandshakeException) {
							            // 不要重试SSL握手异常
							            return false;
							        }
							        HttpRequest request = (HttpRequest) context.getAttribute(
							            ExecutionContext.HTTP_REQUEST);
							        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
							        if (idempotent) {
							            // 如果请求被认为是幂等的，那么就重试
							            return true;
							        }
							        return false;
							    }
							};
						((AbstractHttpClient) client).setHttpRequestRetryHandler(myRetryHandler);
						httpResponse=client.execute(httpPost);
						if(httpResponse.getStatusLine().getStatusCode()==200){
							String result = EntityUtils.toString(httpResponse.getEntity());
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
					}catch(Exception e){
						e.printStackTrace();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
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
	
	 ResultCallback resultCallback=new ResultCallback() {
		
		@Override
		public void getReslt(String result) {
			// TODO Auto-generated method stub
		}
	};
}
