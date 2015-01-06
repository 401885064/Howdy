package com.mayi.julyup.adapter;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mayi.julyup.R;
import com.mayi.julyup.pictures.Bimp;

@SuppressLint("HandlerLeak")
public class Published_GridAdapter extends BaseAdapter {
	private LayoutInflater inflater; // 视图容器
	private int selectedPosition = -1;// 选中的位置
	private Context context;
	public boolean IsUpLoading=false;
	private Map<String,Integer> pb_maps=new HashMap<String, Integer>();
	
	public Published_GridAdapter(Context context) {
		this.context=context;
		for(int i=0;i<9;i++){
			pb_maps.put("max"+i,100);
			pb_maps.put("current"+i, 0);
			pb_maps.put("complete"+i, 0);
		}
		
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return (Bimp.bmp.size() + 1);
	}
	@Override
	public Object getItem(int position) {

		return null;
	}
	@Override
	public long getItemId(int position) {
		
		return position;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}
	
	public Map<String, Integer> getPbMap(){
		return pb_maps;
	}
	
	public int getSelectedPosition() {
		return selectedPosition;
	}

	/**
	 * ListView Item设置
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.e("mayi","position ："+position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_published_grida,null, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
			holder.pb=(ProgressBar) convertView.findViewById(R.id.img_pb);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		//"+"号
		if (position == Bimp.bmp.size()) {
			if(IsUpLoading){
				holder.image.setVisibility(View.GONE);
			}else{
				holder.image.setVisibility(View.VISIBLE);
				holder.image.setImageBitmap(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.icon_addpic_unfocused));
				holder.pb.setVisibility(View.GONE);
			}
			if (position == 9) {
				holder.image.setVisibility(View.GONE);
			}
		} else {
			if(pb_maps.get("complete"+position)==88){
				holder.image.setVisibility(View.INVISIBLE);
				holder.pb.setVisibility(View.INVISIBLE);
			}else{
				holder.image.setVisibility(View.VISIBLE);
				holder.image.setImageBitmap(Bimp.bmp.get(position));
				holder.pb.setVisibility(View.VISIBLE);
				holder.pb.setMax(pb_maps.get("max"+position));
				holder.pb.setProgress(pb_maps.get("current"+position));
			}
		}
		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
		public ProgressBar pb;
	}
}

