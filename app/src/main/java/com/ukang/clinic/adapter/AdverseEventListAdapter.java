package com.ukang.clinic.adapter;

/**
 * 不良事件Adapter 
 */

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ukang.clinic.R;
import com.ukang.clinic.activity.add.AdverseEventAddActivity;
import com.ukang.clinic.fragments.AdverseEventListFragment;
import com.ukang.clinic.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/***
 * @author Administrator
 */
public class AdverseEventListAdapter extends BaseAdapter {
	AdverseEventListFragment frg;
	private Context contextNative;
	private JSONArray InfoArray;
	private LayoutInflater inflater;
	String nums = "";
	int clicked = 0;

	/**
	 * 
	 * @param context
	 * @param nursingAdviceRecordInfoArra
	 * @param nums
	 */
	public AdverseEventListAdapter(Context context,
			JSONArray nursingAdviceRecordInfoArra, String nums,
			AdverseEventListFragment frg) {
		this.contextNative = context;
		this.InfoArray = nursingAdviceRecordInfoArra;
		this.nums = nums;
		this.frg = frg;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (InfoArray == null) {
			return 0;
		} else {
			return InfoArray.length();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Object res = null;
		try {
			res = InfoArray.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		try {
			viewHolder = new ViewHolder();
			convertView = (View) inflater.inflate(
					R.layout.adapter_adverse_list, parent, false);
			viewHolder.tx_1 = (TextView) convertView.findViewById(R.id.tv1);
			viewHolder.tx_2 = (TextView) convertView.findViewById(R.id.tv2);
			viewHolder.mdate1 = (TextView) convertView
					.findViewById(R.id.mdate1);
			viewHolder.mdate2 = (TextView) convertView
					.findViewById(R.id.mdate2);
			viewHolder.tx_3 = (TextView) convertView.findViewById(R.id.tv3);
			viewHolder.btn_edit = (Button) convertView.findViewById(R.id.btn1);
			viewHolder.btn_del = (Button) convertView.findViewById(R.id.btn2);

			JSONObject jo = InfoArray.getJSONObject(position);
			String columns1 = "", columns2 = "", columns3 = "", columns4 = "", columns5 = "";

			columns1 = jo.getString("name");
			viewHolder.tx_1.setText(columns1);

			columns2 = jo.getString("miaoshu");
			viewHolder.tx_2.setText(columns2);

			columns3 = jo.getString("startdate");
			if (!TextUtils.isEmpty(columns3)) {
				viewHolder.mdate1.setText(columns3);
			}

			columns4 = jo.getString("enddate");
			if (!TextUtils.isEmpty(columns4)) {
				viewHolder.mdate2.setText(columns4);
			}

			columns5 = jo.getString("yanz");
			if (columns5.equals("0")) {
				viewHolder.tx_3.setText("轻");
			} else if (columns5.equals("1")) {
				viewHolder.tx_3.setText("中");
			} else {
				viewHolder.tx_3.setText("重");
			}
			if (position % 2 == 0) {
				convertView.setBackgroundResource(R.color.transparent);
			} else {
				convertView.setBackgroundResource(R.color.gray_1);
			}
			((MainActivity)contextNative).setSubmitVisibily(viewHolder.btn_edit);
			((MainActivity)contextNative).setSubmitVisibily(viewHolder.btn_del);
			viewHolder.btn_edit.setOnClickListener(new ItemOnClicked(position));
			viewHolder.btn_del.setOnClickListener(new ItemOnClicked1(position));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	class ItemOnClicked implements View.OnClickListener {

		private int pos;

		public ItemOnClicked(int position) {
			this.pos = position;
		}

		public void onClick(View v) {
			clicked = pos;
			// notifyDataSetChanged();
			JSONObject jo = null;
			try {
				jo = InfoArray.getJSONObject(clicked);
				Intent i = new Intent(contextNative,
						AdverseEventAddActivity.class);
				i.putExtra("id", jo.getString("id"));
				i.putExtra("name", jo.getString("name"));
				i.putExtra("miaoshu", jo.getString("miaoshu"));
				i.putExtra("startdate", jo.getString("startdate"));
				i.putExtra("enddate",
						jo.has("enddate") ? jo.getString("enddate") : "");
				i.putExtra("yanz", jo.has("yanz") ? jo.getString("yanz") : "");
				i.putExtra("cuoshi", jo.has("cuoshi") ? jo.getString("cuoshi")
						: "");
				i.putExtra("yingxiang",
						jo.has("yingxiang") ? jo.getString("yingxiang") : "");
				i.putExtra("guanxi", jo.has("guanxi") ? jo.getString("guanxi")
						: "");
				i.putExtra("gcp", jo.has("gcp") ? jo.getString("gcp") : "");
				i.putExtra("jieju", jo.has("jieju") ? jo.getString("jieju")
						: "");
				i.putExtra("tuichu", jo.has("tuichu") ? jo.getString("tuichu")
						: "");
				contextNative.startActivity(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class ItemOnClicked1 implements View.OnClickListener {

		private int pos;

		public ItemOnClicked1(int position) {
			this.pos = position;
		}

		public void onClick(View v) {
			clicked = pos;
			// notifyDataSetChanged();
			JSONObject jo = null;
			try {
				jo = InfoArray.getJSONObject(clicked);
				frg.deleteDataFromWebservice(jo.getString("id"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	static class ViewHolder {
		TextView tx_1;
		TextView tx_2;
		TextView tx_3;
		TextView mdate1, mdate2;
		Button btn_edit, btn_del;
	}

}
