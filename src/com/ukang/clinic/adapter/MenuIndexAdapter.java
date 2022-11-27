package com.ukang.clinic.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ukang.clinic.R;
import com.ukang.clinic.entity.MenuContents;
import com.ukang.clinic.main.MainActivity;

public class MenuIndexAdapter extends BaseAdapter {
	int click_po = 0;
	private Context contextNative;
	private LayoutInflater inflater;
	ListView listview;
	int menuClass;
	private ArrayList<?> mlist;

	public MenuIndexAdapter(Context paramContext, ArrayList<?> paramArrayList,
			ListView paramListView) {
		this.contextNative = paramContext;
		this.mlist = paramArrayList;
		this.listview = paramListView;
		this.inflater = ((LayoutInflater) paramContext
				.getSystemService("layout_inflater"));
	}

	public int getCount() {
		if (this.mlist.size() == 0)
			return 0;
		return this.mlist.size();
	}

	public Object getItem(int position) {
		return this.mlist.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View paramView, ViewGroup paramViewGroup) {
		ViewHolder localViewHolder;
		MenuContents localMenuContents;
		try {
			localViewHolder = new ViewHolder();
			paramView = this.inflater.inflate(R.layout.layout_menu_1,
					paramViewGroup, false);
			localViewHolder.tx_1 = ((TextView) paramView
					.findViewById(R.id.tv_2));
			localViewHolder.img_index = ((ImageView) paramView
					.findViewById(R.id.img_index));
			localMenuContents = (MenuContents) this.mlist.get(position);
			localViewHolder.tx_1.setText(localMenuContents.name);
			localViewHolder.tv_icon = ((TextView) paramView
					.findViewById(R.id.img_1));
			int j = 0;
			if (click_po == position) {
				j = this.contextNative.getResources().getIdentifier(
						localMenuContents.pic, "drawable", "com.ukang.clinic");
			} else {
				j = this.contextNative.getResources().getIdentifier(
						localMenuContents.picq, "drawable", "com.ukang.clinic");
			}
			localViewHolder.tv_icon.setBackgroundResource(j);
			paramView.setOnClickListener(new ItemOnClicked(position));

		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return paramView;
	}

	class ItemOnClicked implements View.OnClickListener {
		private int pos;

		public ItemOnClicked(int pos) {
			this.pos = pos;
		}

		public void onClick(View paramView) {
			click_po = pos;
			((MainActivity) MenuIndexAdapter.this.contextNative)
					.getDataFromLocalDB(1,
							((MenuContents) MenuIndexAdapter.this.mlist
									.get(this.pos)).id, true,
							(MenuContents) MenuIndexAdapter.this.mlist
									.get(this.pos));
			MenuIndexAdapter.this.notifyDataSetInvalidated();

			// 保存当前第一个可见的item的索引和偏移量
			int index = listview.getFirstVisiblePosition();
			View v = listview.getChildAt(0);
			int top = (v == null) ? 0 : v.getTop();
			// 根据上次保存的index和偏移量恢复上次的位置
			listview.setSelectionFromTop(index, top);
		}
	}

	static class ViewHolder {
		ImageView img_index;
		TextView tv_icon;
		TextView tx_1;
	}
}