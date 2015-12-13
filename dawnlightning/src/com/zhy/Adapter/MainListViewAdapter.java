package com.zhy.Adapter;

import java.util.List;

import com.dawnlightning.ucqa.R;
import com.zhy.Bean.Comment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;



public class MainListViewAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater layoutInflater;
	private List<Comment> allComment;
	
	
	public MainListViewAdapter(Context context,List<Comment> allComments) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.allComment=allComments;
		layoutInflater=LayoutInflater.from(context);
	}
	public MainListViewAdapter(){
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allComment.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return allComment.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
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
		} else {
			viewHolder = (ViewHolder) layout.getTag();
		}
		viewHolder.time.setText(allComment.get(arg0).getDateline());
		viewHolder.tvContent.setText(allComment.get(arg0).getMessage());
		if(!allComment.get(arg0).getName().isEmpty()){
			viewHolder.tvName.setText(allComment.get(arg0).getName());
		}else{
		viewHolder.tvName.setText(allComment.get(arg0).getAuthor());
		}
		return layout;
	}
	public void setlist(List<Comment> allComments){
		this.allComment=allComments;
	}
	private static class ViewHolder{
		
		private TextView tvName;
		private TextView tvContent;
		private TextView time;
		
	}
	
}
