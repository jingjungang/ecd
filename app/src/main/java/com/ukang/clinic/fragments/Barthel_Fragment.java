package com.ukang.clinic.fragments;

/**
 * Barthel指数
 * jjg
 * 2016年6月2日 14:34:55
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.XThread;
import com.ukang.clinic.utils.DialogUtils;
import com.ukang.clinic.utils.EditTextUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Barthel_Fragment extends Fragment implements OnClickListener {

	private Spinner mySpinner;
	/** 中心号 */
	@ViewInject(R.id.zhongxin_et)
	private EditText zhongxin_et;
	/** 药物编号 */
	@ViewInject(R.id.yaowu_num)
	private EditText yaowu_num;
	/** 姓名拼音缩写 */
	@ViewInject(R.id.name)
	private EditText name;
	/** 吃饭 */
	@ViewInject(R.id.frg_20_1)
	private EditText frg_20_1;
	/** 洗浴 */
	@ViewInject(R.id.frg_20_2)
	private EditText frg_20_2;
	/** 梳洗 */
	@ViewInject(R.id.frg_20_3)
	private EditText frg_20_3;
	/** 穿衣 */
	@ViewInject(R.id.frg_20_4)
	private EditText frg_20_4;
	/** 大便 */
	@ViewInject(R.id.frg_20_5)
	private EditText frg_20_5;
	/** 小便 */
	@ViewInject(R.id.frg_20_6)
	private EditText frg_20_6;
	/** 如厕 */
	@ViewInject(R.id.frg_20_7)
	private EditText frg_20_7;
	/** 椅子/床转换 */
	@ViewInject(R.id.frg_20_8)
	private EditText frg_20_8;
	/** 行走 */
	@ViewInject(R.id.frg_20_9)
	private EditText frg_20_9;
	/** 上楼 */
	@ViewInject(R.id.frg_20_10)
	private EditText frg_20_10;
	/** 总分 */
	@ViewInject(R.id.frg_20_11)
	private TextView frg_20_11;
	/** 研究者签字 */
	// @ViewInject(R.id.frg_20_12)
	// private EditText frg_20_12;
	/** 时间 */
	// @ViewInject(R.id.frg_20_13)
	// private Mdate mDate;
	@ViewInject(R.id.submit)
	Button submit;
	MWDApplication content;
	View root;
	String eid = "";
	EditTextUtil etUtil;

	private void initSpinner() {
		this.mySpinner = ((Spinner) this.root.findViewById(R.id.spinner1));
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.spinnerarry,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner.setAdapter(adapter);
	}

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_barthel,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		initSpinner();
		submit.setOnClickListener(this);
		init();
		 ((MainActivity)getActivity()).setSubmitVisibily(submit);
		return this.root;
	}

	private void init() {
		// TODO Auto-generated method stub
		ArrayList<EditText> list = new ArrayList<EditText>();
		list.add(frg_20_1);
		list.add(frg_20_2);
		list.add(frg_20_3);
		list.add(frg_20_4);
		list.add(frg_20_5);
		list.add(frg_20_6);
		list.add(frg_20_7);
		list.add(frg_20_8);
		list.add(frg_20_9);
		list.add(frg_20_10);
		etUtil = new EditTextUtil(getActivity(), list);
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		ArrayList<Integer> list3 = new ArrayList<Integer>();

		list1.add(0);
		list1.add(5);

		list2.add(0);
		list2.add(5);
		list2.add(10);

		list3.add(0);
		list3.add(5);
		list3.add(10);
		list3.add(15);

		etUtil.setRegion_Barthel(frg_20_11, frg_20_1, list2);
		etUtil.setRegion_Barthel(frg_20_11, frg_20_2, list1);
		etUtil.setRegion_Barthel(frg_20_11, frg_20_3, list1);
		etUtil.setRegion_Barthel(frg_20_11, frg_20_4, list2);
		etUtil.setRegion_Barthel(frg_20_11, frg_20_5, list2);
		etUtil.setRegion_Barthel(frg_20_11, frg_20_6, list2);
		etUtil.setRegion_Barthel(frg_20_11, frg_20_7, list2);
		etUtil.setRegion_Barthel(frg_20_11, frg_20_8, list3);
		etUtil.setRegion_Barthel(frg_20_11, frg_20_9, list3);
		etUtil.setRegion_Barthel(frg_20_11, frg_20_10, list2);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		content = (MWDApplication) getActivity().getApplication();
		fetch_data();
		super.onStart();
	}

	// Thread-fetch
	private void fetch_data() {
		DialogUtils.startDialog(getActivity());
		RequestParams params = new RequestParams();
		params.addHeader("Cookie", Constant.sessionId);
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("nums", content.nums);
		params.addBodyParameter("id", content.pid);
		XThread thread = new XThread(getActivity(), 0, params,
				Constant.BARTHEL_URL, fetchmHandler);
		thread.start();
	}

	// Thread-add-edit
	private void onLoad_add_edit() {
		DialogUtils.startDialog(getActivity());
		RequestParams params = new RequestParams();
		params.addHeader("Cookie", Constant.sessionId);
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("nums", content.nums);
		params.addBodyParameter("eid", content.pid);
		// if (!eid.equals("")) { // 编辑新增
		// params.addBodyParameter("eid", eid);
		// } else { // 查看
		// params.addBodyParameter("id", content.pid);
		// }
		params.addBodyParameter("score", getJS());
		params.addBodyParameter("total", frg_20_11.getText().toString());
		XThread thread = new XThread(getActivity(), 0, params,
				Constant.BARTHEL_ADD_EDIT, mEidHandler);
		thread.start();
	}

	private Handler fetchmHandler = new Handler() {
		public void handleMessage(Message msg) {
			DialogUtils.stopDialog();
			String result = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(result);
				int status = json.has("status") ? json.getInt("status") : 0;
				switch (status) {
				case 0:
					Toast.makeText(content, "Token验证失败", Toast.LENGTH_SHORT)
							.show();
					break;
				case -1:
					Toast.makeText(content, "无传入参数或者不完整", Toast.LENGTH_SHORT)
							.show();
					break;
				case -3:
					eid = "";
					// Toast.makeText(content, "无数据",
					// Toast.LENGTH_SHORT).show();
					break;
				case 1:
					JSONObject js = json.getJSONObject("info");
					if (null != js) {
						if (new JSONObject(js.getString("score")) != null) {
							eid = content.pid;
						} else {
							eid = "";
						}
						/*
						 * int interview = js.has("interview") ? js
						 * .getInt("interview") : 1;
						 * mySpinner.setSelection(interview - 1); String
						 * centernum = js.has("centernum") ? js
						 * .getString("centernum") : "";
						 * zhongxin_et.setText(centernum); String number =
						 * js.has("number") ? js .getString("number") : "";
						 * yaowu_num.setText(number); String abbreviation =
						 * js.has("abbreviation") ? js
						 * .getString("abbreviation") : "";
						 * name.setText(abbreviation);
						 */
						JSONObject item = js.has("score") ? js
								.getJSONObject("score") : null;

						String toatal = js.has("total") ? js.getString("total")
								: "";
						frg_20_11.setText(toatal);
						if (null != item) {
							String frg1 = item.has("1a") ? item.getString("1a")
									: "";
							frg_20_1.setText(frg1);
							String frg2 = item.has("2a") ? item.getString("2a")
									: "";
							frg_20_2.setText(frg2);
							String frg3 = item.has("3a") ? item.getString("3a")
									: "";
							frg_20_3.setText(frg3);
							String frg4 = item.has("4a") ? item.getString("4a")
									: "";
							frg_20_4.setText(frg4);
							String frg5 = item.has("5a") ? item.getString("5a")
									: "";
							frg_20_5.setText(frg5);
							String frg6 = item.has("6a") ? item.getString("6a")
									: "";
							frg_20_6.setText(frg6);
							String frg7 = item.has("7a") ? item.getString("7a")
									: "";
							frg_20_7.setText(frg7);
							String frg8 = item.has("8a") ? item.getString("8a")
									: "";
							frg_20_8.setText(frg8);
							String frg9 = item.has("9a") ? item.getString("9a")
									: "";
							frg_20_9.setText(frg9);
							String frg10 = item.has("10a") ? item
									.getString("10a") : "";
							frg_20_10.setText(frg10);
						}
					}
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
	Handler mEidHandler = new Handler() {
		public void handleMessage(Message msg) {
			DialogUtils.stopDialog();
			String result = msg.obj.toString();
			if (null != result) {
				JSONObject json;
				try {
					json = new JSONObject(result);
					int status = json.getInt("status");
					switch (status) {
					case 0:
						Toast.makeText(getActivity(), "保存失败",
								Toast.LENGTH_SHORT).show();
						break;
					case 1:
						Toast.makeText(getActivity(), "保存成功",
								Toast.LENGTH_SHORT).show();
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	};

	/** 将参数转换为JSON */
	private String getJS() {
		JSONObject jo1 = new JSONObject();
		try {
			jo1.put("1a", frg_20_1.getText().toString());
			jo1.put("2a", frg_20_2.getText().toString());
			jo1.put("3a", frg_20_3.getText().toString());
			jo1.put("4a", frg_20_4.getText().toString());
			jo1.put("5a", frg_20_5.getText().toString());
			jo1.put("6a", frg_20_6.getText().toString());
			jo1.put("7a", frg_20_7.getText().toString());
			jo1.put("8a", frg_20_8.getText().toString());
			jo1.put("9a", frg_20_9.getText().toString());
			jo1.put("10a", frg_20_10.getText().toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo1.toString();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit:
			onLoad_add_edit();
			break;
		}
	}
}
