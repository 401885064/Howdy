package com.mayi.julyup.adapter;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import com.mayi.julyup.R;
import com.mayi.julyup.been.FilesDir_Month;
import com.mayi.julyup.been.FilesDir_Year;
import com.mayi.julyup.been.Upload_unfinish_Been;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

@EBean
public class TASK_ExpandListAdapter extends BaseExpandableListAdapter {
	@RootContext
	Context context;
	LayoutInflater inflater;
	@AfterInject
	void init(){
		inflater=LayoutInflater.from(context);
	}
	
	//标题栏
	private ArrayList<FilesDir_Year.Data> Years =new ArrayList<FilesDir_Year.Data>();
	//子标题栏
	public ArrayList<Upload_unfinish_Been> Months=new ArrayList<Upload_unfinish_Been>();
	
	public void SetYears(ArrayList<FilesDir_Year.Data> years){
		Years=years;
		notifyDataSetChanged();
	}
	
	public ArrayList<FilesDir_Year.Data> GetYears(){
		return Years;
	}
	
	public void SetMonth(ArrayList<Upload_unfinish_Been> months){
		this.Months=months;
		notifyDataSetChanged();
	}
	
	public ArrayList<Upload_unfinish_Been> GetMonth(){
		return Months;
	}
	/********************************************* 重写ExpandableListAdapter中的各个方法********************************************/
	
	//获取父视图的总数
	@Override
	public int getGroupCount() {
		return Years.size();
	}
	//获取父视图
	@Override
	public Object getGroup(int groupPosition) {
		return Years.get(groupPosition).getCATALOG_NAME();
	}
	//获取父视图ID
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	//获取子视图总数
	@Override
	public int getChildrenCount(int groupPosition) {
		return Months.get(groupPosition).getResult().size();
	}
	//获取子视图
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return  Months.get(groupPosition).getResult().get(childPosition).getMONTH();
	}
	//获取子视图的ID
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	//是否相同的ID总是指的是同一个对象。
	@Override
	public boolean hasStableIds() {
		return true;
	}

	//获取父视图
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		GroupViewHold grouphold=null;
		if(convertView==null){
			grouphold=new GroupViewHold();
			convertView=inflater.inflate(R.layout.expandlist_groupview, null);
			grouphold.group_view_text=(TextView) convertView.findViewById(R.id.group_view_text);
			convertView.setTag(grouphold);
		}else{
			grouphold=(GroupViewHold) convertView.getTag();
		}
		grouphold.group_view_text.setText(getGroup(groupPosition).toString());
		return convertView;
	}
	
	class GroupViewHold{
			TextView group_view_text;
	}
	
	//获取子视图
	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHold childhold=null;
		if(convertView==null){
			childhold=new ChildViewHold();
			convertView=inflater.inflate(R.layout.expandlist_childview, null);
			childhold.childview_time_text=(TextView) convertView.findViewById(R.id.childview_time_text);
			childhold.childview_count_text=(TextView) convertView.findViewById(R.id.childview_count_text);
			convertView.setTag(childhold);
		}else{
			childhold=(ChildViewHold) convertView.getTag();
		}
		childhold.childview_time_text.setText(getChild(groupPosition, childPosition).toString()+"月份");
		childhold.childview_count_text.setText(Months.get(groupPosition).getResult().get(childPosition).getSTATUS());
		return convertView;
	}
	
	class ChildViewHold{
		TextView childview_time_text;
		TextView childview_count_text;
	}
	
	//Whether the child at the specified position is selectable.
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	// 自己定义一个获得文字信息的方法
	TextView getTextView() {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64);
		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textView.setPadding(50, 0, 0, 0);
		textView.setTextSize(20);
		textView.setTextColor(Color.BLACK);
		return textView;
	}
}
