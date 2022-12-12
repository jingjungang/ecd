package com.ukang.clinic.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ukang.clinic.R;
import com.ukang.clinic.activity.add.AddCaseActivity;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.patient.MenuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * gridview的數據
 * 
 * @author Administrator
 * 
 */
public class CardAdapter extends BaseAdapter {
	public static String NUMBER = "number"; // 访视进行阶段
	public static String ISCHECKED = "ischecked"; // 访视提交情况
	public static String ISBREAK = "isbreak"; // 访视终止情况
	JSONArray gridInfo;
	private LayoutInflater inflater;
	Context paramContext;
	JSONArray ja;
	int selectedPosition = 0;
	MWDApplication application;

	public CardAdapter(Context paramContext, int page, JSONArray ja) {
		this.inflater = ((LayoutInflater) paramContext
				.getSystemService("layout_inflater"));
		this.paramContext = paramContext;
		application = (MWDApplication) paramContext.getApplicationContext();
		gridInfo = new JSONArray();
		this.ja = ja;
		int index = 0;
		int count = MenuActivity.PER_SIZE;
		// 每一页
		for (int i = page * count; i < page * count + count; i++) {
			if (i == ja.length())
				break;
			try {
				gridInfo.put(index, ja.get(i));
				index++;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getCount() {
		return gridInfo == null ? 0 : gridInfo.length();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// if (convertView == null) {
		holder = new ViewHolder();
		convertView = this.inflater.inflate(R.layout.gridview_item, parent,
				false);
		holder.iconiView = (ImageView) convertView.findViewById(R.id.image);
		holder.TV1 = (TextView) convertView.findViewById(R.id.name);
		holder.TV2 = (TextView) convertView.findViewById(R.id.sex);
		holder.TV3 = (TextView) convertView.findViewById(R.id.age);
		holder.TV4 = (TextView) convertView.findViewById(R.id.pid);
		holder.TV5 = (TextView) convertView.findViewById(R.id.phone_num);
		holder.TV6 = (TextView) convertView.findViewById(R.id.refresh_time);
		holder.TV7 = (TextView) convertView.findViewById(R.id.step);
		holder.btn_in = (Button) convertView.findViewById(R.id.plan_in);
		holder.btn_out = (Button) convertView.findViewById(R.id.plan_out);
		holder.male_bg = (LinearLayout) convertView.findViewById(R.id.male_bg);
		holder.creater = (TextView) convertView.findViewById(R.id.creater);
		convertView.setTag(holder);
		// } else {// 3、有可利用的item时就获取赋值使用
		// holder = (ViewHolder) convertView.getTag();
		// }
		JSONObject info;
		try {
			info = gridInfo.getJSONObject(position);
			holder.TV2.setText(info.getString("sex").equals("1") ? "男" : "女");
			holder.TV3.setText(info.getString("age") + "岁");
			String tx = info.has("medicationno") ? info.getString(
					"medicationno").replace("null", "") : "暂无";
			holder.TV4.setText("药物编号：" + tx);
			holder.TV5.setText(info.getString("phone"));
			holder.TV6.setText(info.getString("update_time"));
			String temp = "";
			if (!TextUtils.isEmpty(info.getString(ISCHECKED))) {
				temp = info.getString(ISCHECKED);
				if (temp.equals("0")) { // 0未提交 1待审核 2审核通过 3审核未通过
					temp = "未提交";
				} else if (temp.equals("1")) {
					temp = "待审核";
				} else if (temp.equals("2")) {
					temp = "审核通过";
				} else if (temp.equals("3")) {
					temp = "审核未通过";
				}
			}
			String no = info.getString(NUMBER);
			if (no.equals("1") || no.equals("2") || no.equals("3")
					|| no.equals("4")) {
				if (no.equals("1")) {
					holder.TV7.setText("第一次" + "(" + temp + ")");
					holder.btn_in.setBackground(paramContext.getResources()
							.getDrawable(R.drawable.vin_ed));
				} else if (no.equals("2")) {
					holder.TV7.setText("第二次" + "(" + temp + ")");
					holder.btn_in.setBackground(paramContext.getResources()
							.getDrawable(R.drawable.vin_ed));
				} else if (no.equals("3")) {
					holder.TV7.setText("第三次" + "(" + temp + ")");
					holder.btn_in.setBackground(paramContext.getResources()
							.getDrawable(R.drawable.vin_ed));
				} else {
					holder.TV7.setText("第四次" + "(" + temp + ")");
					holder.btn_in.setBackground(paramContext.getResources()
							.getDrawable(R.drawable.vin_ed));
				}
				holder.TV1.setText(info.getString("rname").equals("") ? "无"
						: info.getString("rname"));
			} else if (no.equals("5")) {
				holder.TV7.setText("中止" + "(" + temp + ")");
				holder.TV1.setText(info.getString("rname").equals("") ? "无"
						: info.getString("rname"));
				holder.btn_in.setBackground(paramContext.getResources()
						.getDrawable(R.drawable.vin_ed));
			} else if (no.equals("6")) {
				holder.TV7.setText("实验完成" + "(" + temp + ")");
				holder.TV1.setText(info.getString("rname").equals("") ? "无"
						: info.getString("rname"));
				holder.btn_in.setBackground(paramContext.getResources()
						.getDrawable(R.drawable.vin_ed));
			} else if (no.equals("7")) {
				holder.TV7.setText("第五次" + "(" + temp + ")");
				holder.TV1.setText(info.getString("rname").equals("") ? "无"
						: info.getString("rname"));
				holder.btn_out.setBackground(paramContext.getResources()
						.getDrawable(R.drawable.vout_ed));
				holder.male_bg.setBackground(paramContext.getResources()
						.getDrawable(R.drawable.hout));
			} else if (no.equals("8")) {
				holder.TV7.setText("第六次" + "(" + temp + ")");
				holder.TV1.setText(info.getString("rname").equals("") ? "无"
						: info.getString("rname"));
				holder.btn_out.setBackground(paramContext.getResources()
						.getDrawable(R.drawable.vout_ed));
				holder.male_bg.setBackground(paramContext.getResources()
						.getDrawable(R.drawable.hout));
			}

			if (info.getString("sex").equals("1")) {
				holder.iconiView.setImageResource(R.drawable.male);
			} else {
				holder.iconiView.setImageResource(R.drawable.female);
			}
			convertView.setOnClickListener(new ItemOnClicked2(position));
			holder.creater.setText(info.has("creater") ? info
					.getString("creater") : "暂无");
			// 监听点击事件
			// holder.btn_in.setOnClickListener(new ItemOnClicked(position));
			// holder.btn_out.setOnClickListener(new ItemOnClicked1(position));
			// convertView.setOnLongClickListener(new
			// ItemOnClicked_long(position));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertView;
	}

	class ItemOnClicked2 implements View.OnClickListener {

		private int pos;

		public ItemOnClicked2(int position) {
			this.pos = position;
		}

		public void onClick(View v) {
			dialog(pos);
		}
	}

	// 计划内访视
	class ItemOnClicked implements View.OnClickListener {

		private int pos;
		Dialog dialog;

		public ItemOnClicked(int position, Dialog dialog) {
			this.pos = position;
			this.dialog = dialog;
		}

		public void onClick(View v) {
			// setSelected(v, pos);
			try {
				JSONObject objcet = gridInfo.getJSONObject(pos);
				application.pid = objcet.getString("id").toString();
				Intent i = new Intent(paramContext, MainActivity.class);
				i.putExtra("number",
						objcet.has("number") ? objcet.getString("number") : "");
				i.putExtra("ischecked",
						objcet.has("ischecked") ? objcet.getString("ischecked")
								: "");
				i.putExtra("isbreak",
						objcet.has("isbreak") ? objcet.getString("isbreak")
								: "");
				i.putExtra("isout", "0");
				paramContext.startActivity(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dialog.dismiss();
		}
	}

	// 计划外访视
	class ItemOnClicked1 implements View.OnClickListener {

		private int pos;
		Dialog dialog;

		public ItemOnClicked1(int position, Dialog dialog) {
			this.pos = position;
			this.dialog = dialog;
		}

		public void onClick(View v) {
			try {
				JSONObject objcet = gridInfo.getJSONObject(pos);
				// 是否是计划外访视 1有计划外访视 2没有计划外访视
				String no = objcet.getString("plan");
				if (no.equals("2")) {
					Toast.makeText(paramContext, "未完成计划内访视，不能计划外访视",
							Toast.LENGTH_SHORT).show();
				} else {
					application.pid = objcet.getString("id").toString();
					Intent i = new Intent(paramContext, MainActivity.class);
					i.putExtra("number",
							objcet.has("number") ? objcet.getString("number")
									: "");
					i.putExtra(
							"ischecked",
							objcet.has("ischecked") ? objcet
									.getString("ischecked") : "");
					i.putExtra("isbreak",
							objcet.has("isbreak") ? objcet.getString("isbreak")
									: "");
					i.putExtra("isout", "1");
					paramContext.startActivity(i);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dialog.dismiss();
		}
	}

	// 长点击-进行编辑数据
	class ItemOnClicked_patient implements View.OnClickListener {

		private int pos;
		Dialog dialog;

		public ItemOnClicked_patient(int position, Dialog dialog) {
			this.pos = position;
			this.dialog = dialog;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String id;
			try {
				id = gridInfo.getJSONObject(pos).get("id").toString();
				Bundle b = new Bundle();
				b.putString("id", id);
				((MWDApplication) paramContext.getApplicationContext()).is_add_patient = "2";
				((MWDApplication) paramContext.getApplicationContext()).pid = id;
				Intent i = new Intent(paramContext, AddCaseActivity.class);
				paramContext.startActivity(i.putExtras(b));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dialog.dismiss();
		}
	}

	/**
	 * 点击选择框
	 * 
	 * @param position
	 */
	private void dialog(int position) {

		Dialog dia = new Dialog(paramContext, R.style.selectorDialog);

		LayoutInflater inflater = ((Activity) paramContext).getLayoutInflater();
		View view = inflater.inflate(R.layout.card_dialog, null);

		TextView btn_in = (TextView) view.findViewById(R.id.btn_in);
		TextView btn_out = (TextView) view.findViewById(R.id.btn_out);
		TextView tv_patient = (TextView) view.findViewById(R.id.tv_patient);

		btn_in.setOnClickListener(new ItemOnClicked(position, dia));
		btn_out.setOnClickListener(new ItemOnClicked1(position, dia));
		tv_patient.setOnClickListener(new ItemOnClicked_patient(position, dia));

		dia.setContentView(view);
		dia.show();
	}

	private class ViewHolder {
		ImageView iconiView;
		TextView TV1;
		TextView TV2;
		TextView TV3;
		TextView TV4;
		TextView TV5;
		TextView TV6;
		TextView TV7;
		TextView creater;
		Button btn_in, btn_out;
		LinearLayout male_bg;
	}
}