package com.zhy.Adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;



import com.dawnlightning.ucqa.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.SdCardUtil;




import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;



@SuppressLint("UseSparseArrays")
public class MainGridViewAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<String> allImageUrl;
	@SuppressWarnings("unused")
	private Context ctx;
	private DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	//暂且用bitmap加载图片
	private HashMap<Integer,String> map=new HashMap<Integer,String>();
	
	@SuppressLint("UseSparseArrays")
	public MainGridViewAdapter(Context context,List<String> allImageUrl) {
		// TODO Auto-generated constructor stub
		this.ctx=context;
		this.allImageUrl=allImageUrl;
		layoutInflater = (LayoutInflater) LayoutInflater.from(context);
		
		SdCardUtil.createFileDir(SdCardUtil.FILEDIR+"/"+SdCardUtil.FILECACHE);
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, SdCardUtil.FILEDIR+"/"+SdCardUtil.FILECACHE);  
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
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
	    .discCache(new UnlimitedDiscCache(cacheDir))
	    .discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
	    .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
	    .build();
		imageLoader.init(config);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.bg_images)
				.showImageOnFail(R.drawable.bg_images).cacheInMemory().cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(20)).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allImageUrl.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return allImageUrl.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("InflateParams")
	@SuppressWarnings({ "unused" })
	@Override
	public View getView(final int arg0, View contentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		RelativeLayout view=null;
		final ViewHolder viewHolder;
		if(view==null){
			view=(RelativeLayout) layoutInflater.inflate(R.layout.item_gridview_main, null);
			viewHolder=new ViewHolder();
			viewHolder.imageView=(ImageView)view.findViewById(R.id.main_gridview_imageView);
			view.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder)view.getTag();
		}
		//AsynImageLoader.showImageAsyn(viewHolder.imageView, HttpConstants.HTTP_REQUEST
				//+ allImageUrl.get(arg0), R.drawable.ic_launcher);
		
		imageLoader.displayImage(HttpConstants.HTTP_HEAD+allImageUrl.get(arg0), viewHolder.imageView, options,new SimpleImageLoadingListener(){

			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
			
				super.onLoadingComplete(imageUri, view, loadedImage);
				Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(loadedImage, 88, 88);
				viewHolder.imageView.setImageBitmap(resizeBmp);
				map.put(arg0,imageUri);
			}
			
		});
		

		return view;
	}
	public String getBitmap(int postion){
		if(map!=null){
			return map.get(postion);
		}else{
			return null;
		}
		
	}
	private static class ViewHolder{
		
		private ImageView imageView;
	}

}
