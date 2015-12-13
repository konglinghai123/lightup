package com.zhy.Adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import com.dawnlightning.ucqa.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.Bean.ConsultMessageBean;
import com.zhy.Db.SharedPreferenceDb;
import com.zhy.Util.HttpConstants;
import com.zhy.Util.SdCardUtil;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ConsultAdapter extends BaseAdapter{
	private ViewHolder holder;
	private LayoutInflater layoutInflater;
	private Context context;
	private List<ConsultMessageBean> list;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private ConsultMessageBean item;
	public ConsultAdapter(Context c,List<ConsultMessageBean> list) {
		super();
		this.context=c;
		this.list=list;
		SdCardUtil.createFileDir(SdCardUtil.FILEDIR+"/"+SdCardUtil.FILECACHE);
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, SdCardUtil.FILEDIR+"/"+SdCardUtil.FILECACHE);  
		layoutInflater = (LayoutInflater) LayoutInflater.from(c);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c)
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
				.showStubImage(R.drawable.defalut)
				.showImageOnFail(R.drawable.defalut).cacheInMemory().cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(20)).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	
	}
	
	public void setList(List<ConsultMessageBean> list){
		this.list = list;
	}
	public void addList(List<ConsultMessageBean> list){
		this.list.addAll(list);
 	}
	public void clearList(){
		this.list.clear();
 	}
	public List<ConsultMessageBean> getList(){
		return list;
	}
	public void removeItem(int position){
		if(list.size() > 0){
			list.remove(position);
		}
	}
	@Override
	public int getCount() {
		return list.size();
	}
	
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
		
			convertView = layoutInflater.inflate(R.layout.consult_items, null);
			holder = new ViewHolder();
			holder.suject=(TextView) convertView.findViewById(R.id.subject);
			holder.pic=(ImageView) convertView.findViewById(R.id.consultpic);
			holder.message=(TextView) convertView.findViewById(R.id.message);
			holder.viewnum=(TextView) convertView.findViewById(R.id.numview);
			holder.replynum=(TextView) convertView.findViewById(R.id.numreply);
			holder.time=(TextView) convertView.findViewById(R.id.time);
			holder.status=(TextView) convertView.findViewById(R.id.consult_status);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
			item = list.get(position); // ��ȡ��ǰ�����
		
		if (null != item) {
			holder.suject.setText(item.getSubject());
			holder.message.setText(Html.fromHtml(item.getMessage()).toString());
			holder.viewnum.setText("浏览:"+item.getViewnum());
			holder.replynum.setText("回复:"+item.getReplynum());
			holder.time.setText(item.getDateline());
			
			if(item.getAvatar_url().length()!=0&&item.getAvatar_url()!=null){
			
				holder.pic.setVisibility(View.VISIBLE);
				
				imageLoader.displayImage(item.getAvatar_url(), holder.pic, options);
				
			}else{
				holder.pic.setVisibility(holder.pic.GONE);
			
			}
			Log.e("status",item.getStatus());
			if(item.getStatus().contains("1")){
				holder.status.setBackground(context.getResources().getDrawable(R.drawable.jiejue));
			}else{
				holder.status.setBackground(null);
			}
		
		
			
			
		}
		return convertView;
	}
    private class ViewHolder {
    	TextView suject;
    	ImageView pic;
    	TextView message;
    	TextView viewnum;
    	TextView replynum;
    	TextView time;
    	TextView status;
    }
}
