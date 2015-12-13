package com.zhy.Adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dawnlightning.ucqa.R;
import com.zhy.Bean.Comment;
import com.zhy.Bean.MessageBean;

public class MessageAdapter extends BaseAdapter {
	private List<MessageBean> message;
	private Context context;
	private LayoutInflater layoutInflater;
	public MessageAdapter( List<MessageBean> message,Context context){
		this.message=message;
		this.context=context;
		this.layoutInflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return message.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return message.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}
	@SuppressLint("NewApi")
	@SuppressWarnings("unused")
	@Override
	public View getView(int arg0, View contentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LinearLayout layout = null;
		
		ViewHolder viewHolder = null;
		if (layout == null) {
			layout = (LinearLayout) layoutInflater.inflate(
					R.layout.pinglun_listview_item, null);
			
			viewHolder=new ViewHolder();
			viewHolder.tvName=(TextView)layout.findViewById(R.id.pinglunName);
			viewHolder.tvContent=(TextView)layout.findViewById(R.id.pinglunContent);
			viewHolder.time=(TextView)layout.findViewById(R.id.time);
			viewHolder.title=(TextView)layout.findViewById(R.id.pingluntitle);
		} else {
			viewHolder = (ViewHolder) layout.getTag();
		}
		viewHolder.time.setText(message.get(arg0).getDateline());
		viewHolder.tvContent.setText(message.get(arg0).getMessage());
		viewHolder.title.setVisibility(View.VISIBLE);
		viewHolder.title.setText(message.get(arg0).getNote());
		if(!message.get(arg0).getName().isEmpty()){
			viewHolder.tvName.setText(message.get(arg0).getName());
		}else{
		viewHolder.tvName.setText(message.get(arg0).getAuthor());
		}
		return layout;
	}
	public void setlist(List<MessageBean> allComments){
		this.message=allComments;
	}
	private static class ViewHolder{
		
		private TextView tvName;
		private TextView tvContent;
		private TextView time;
		private TextView title;
		
	}
	
	
}
