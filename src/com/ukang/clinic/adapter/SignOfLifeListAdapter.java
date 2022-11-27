package com.ukang.clinic.adapter;

/**
 * 头颅MRA Adapter 
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ukang.clinic.R;

/***
 * @author Administrator
 */
public class SignOfLifeListAdapter extends BaseAdapter {

	private Context contextNative;
	private JSONArray InfoArray;
	private LayoutInflater inflater;
	int clicked = 0;

	public SignOfLifeListAdapter(Context context,
			JSONArray nursingAdviceRecordInfoArra) {
		this.contextNative = context;
		this.InfoArray = nursingAdviceRecordInfoArra;

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
			convertView = (View) inflater.inflate(R.layout.adapter_mra, parent,
					false);
			viewHolder.tx_1 = (TextView) convertView.findViewById(R.id.tv1);
			viewHolder.tx_2 = (TextView) convertView.findViewById(R.id.tv2);
			viewHolder.tx_3 = (TextView) convertView.findViewById(R.id.tv3);
			viewHolder.tx_4 = (TextView) convertView.findViewById(R.id.tv4);
			viewHolder.tx_5 = (TextView) convertView.findViewById(R.id.tv5);

			JSONObject jo = InfoArray.getJSONObject(position);
			String columns1 = "", columns2 = "", columns3 = "";
			columns1 = jo.getString("inspectiondate");
			columns2 = jo.getString("status").equals("1") ? "正常" : "异常";
			columns3 = jo.getString("add_time");

			viewHolder.tx_1.setText(position + 1 + "");
			viewHolder.tx_2.setText(columns1);
			viewHolder.tx_3.setText(columns2);
			viewHolder.tx_4.setText(columns3);
			viewHolder.tx_5.setText("编辑");

			if (position % 2 == 0) {
				convertView.setBackgroundResource(R.color.transparent);
			} else {
				convertView.setBackgroundResource(R.color.gray_1);
			}
			convertView.setOnClickListener(new ItemOnClicked(position));
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
			notifyDataSetChanged();
			// try {
			// ((MWDApplication) contextNative.getApplicationContext()).pid =
			// InfoArray
			// .getJSONObject(clicked).getString("id").toString();
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// contextNative.startActivity(new Intent(
			// CombinationDrugListAdapter.this.contextNative,
			// MainActivity.class));
		}
	}

	static class ViewHolder {
		LinearLayout ll;
		TextView tx_1;
		TextView tx_2;
		TextView tx_3;
		TextView tx_4;
		TextView tx_5;
	}

}
