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

public class MenuIndexAdapter2 extends BaseAdapter {
	int click_po = -1;
	private Context contextNative;
	private LayoutInflater inflater;
	ListView listview1;
	ListView listview2;
	int menuClass;
	private ArrayList<?> mlist;

	public MenuIndexAdapter2(Context paramContext,
			ArrayList<MenuContents> paramArrayList, ListView paramListView) {
		this.contextNative = paramContext;
		this.mlist = paramArrayList;
		this.listview1 = paramListView;
		this.inflater = ((LayoutInflater) paramContext
				.getSystemService("layout_inflater"));
	}

	public int getCount() {
		if (mlist == null && mlist.size() == 0)
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
			paramView = this.inflater.inflate(R.layout.layout_menu_2,
					paramViewGroup, false);
			localViewHolder.tx_1 = ((TextView) paramView
					.findViewById(R.id.tv_1));
			localViewHolder.tx_2 = ((TextView) paramView
					.findViewById(R.id.tv_2));
			localViewHolder.img_index = ((ImageView) paramView
					.findViewById(R.id.img_index));

			localMenuContents = (MenuContents) mlist.get(position);
			localViewHolder.tx_1.setText(localMenuContents.name);
			localViewHolder.tx_2.setText(localMenuContents.fname);
			if (click_po == position) {
				localViewHolder.img_index.setVisibility(View.VISIBLE);
			} else {
				localViewHolder.img_index.setVisibility(View.INVISIBLE);
			}
			paramView.setOnClickListener(new ItemOnClicked_1(position));
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return paramView;
	}

	class ItemOnClicked_1 implements View.OnClickListener {
		private int pos;

		public ItemOnClicked_1(int p) {
			this.pos = p;

		}

		public void onClick(View paramView) {
			click_po = pos;
			MenuIndexAdapter2.this.notifyDataSetInvalidated();
			// listview2.setSelectionFromTop(this.pos, 1);
			((MainActivity) MenuIndexAdapter2.this.contextNative)
					.refreshPage(((MenuContents) MenuIndexAdapter2.this.mlist
							.get(this.pos)).fragmentindex);
		}
	}

	static class ViewHolder {
		ImageView img_index;
		TextView tv_icon;
		TextView tx_1;
		TextView tx_2;
	}
}