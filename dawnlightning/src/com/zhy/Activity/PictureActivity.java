package com.zhy.Activity;



import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.dawnlightning.ucqa.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhy.Util.HttpConstants;
import com.zhy.view.ImageTouchView;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class PictureActivity extends Activity{
	private ImageTouchView myView = null;
	private String path="";
	private List<String> allImageUrl;
	private ProgressDialog dialog;
	private String psit;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);
      
        myView = (ImageTouchView)findViewById(R.id.myView);
        Intent i=getIntent();
      
        psit=getIntent().getStringExtra("image");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
        	    this)
        	    .threadPoolSize(3)
        	   
        	    // default
        	    .threadPriority(Thread.NORM_PRIORITY - 2)
        	    .denyCacheImageMultipleSizesInMemory()
        	    .discCacheFileNameGenerator(new Md5FileNameGenerator())
        	    .tasksProcessingOrder(QueueProcessingType.LIFO)
        	    .denyCacheImageMultipleSizesInMemory()
        	    .memoryCache(new LruMemoryCache((int) (6 * 1024 * 1024)))
        	    .memoryCacheSize((int) (2 * 1024 * 1024))
        	    .memoryCacheSize(13)
        	    // default
        	    .discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
        	    .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
        	    .build();
        imageLoader.init(config);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.images)
				.showImageOnFail(R.drawable.images)
				.displayer(new RoundedBitmapDisplayer(20)).imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		
		imageLoader.displayImage(psit, myView, options);
       
    }
	
   
   
}
