package com.ukang.clinic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.ukang.clinic.R;
import com.ukang.clinic.entity.ImgInfo;

import java.util.List;

public class TuPianAdapter extends BaseAdapter {

	private Context context;
	private List<ImgInfo> list;
	BitmapUtils bitmapUtils;

	public TuPianAdapter(Context context, List<ImgInfo> list) {
		super();
		this.context = context;
		this.list = list;
		bitmapUtils = new BitmapUtils(context);
	}

	public List<ImgInfo> getList() {
		return list;
	}

	public void setList(List<ImgInfo> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(R.layout.draw_itme,
					null);
			holder.draw_imageview = (ImageView) arg1
					.findViewById(R.id.draw_imageview);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		ImgInfo imginfo = list.get(arg0);
		if (!imginfo.isNetImg()) {
			holder.draw_imageview.setImageBitmap(imginfo.getBmp());
		} else {
			bitmapUtils.display(holder.draw_imageview, imginfo.getImgPath());
		}
		return arg1;
	}

	class ViewHolder {
		ImageView draw_imageview;
	}

}
