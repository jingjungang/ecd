package com.ukang.clinic.fragments;

/**
 * NIHSS
 * ZZD
 * 2016年4月21日 16:59:42
 */
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;
import com.ukang.clinic.utils.DialogUtils;
import com.ukang.clinic.utils.EditTextUtil;

public class NihssFrgment extends Fragment implements OnClickListener {

	private Spinner mySpinner;
	private RequestThread rThread;
	public ProgressDialog dia;
	/** 中心号 **/
	// @ViewInject(R.id.centernum)
	// private EditText centernum;
	/** 姓名拼音缩写 **/
	// @ViewInject(R.id.abbreviation)
	// private EditText abbreviation;
	/** 意识水平 **/
	@ViewInject(R.id.frg_16_1a)
	private EditText frg_16_1a;
	/** 意识水平提问 **/
	@ViewInject(R.id.frg_16_1b)
	private EditText frg_16_1b;
	/** 意识水平指令 **/
	@ViewInject(R.id.frg_16_1c)
	private EditText frg_16_1c;
	/** 凝视 **/
	@ViewInject(R.id.frg_16_2)
	private EditText frg_16_2;
	/** 视野 **/
	@ViewInject(R.id.frg_16_3)
	private EditText frg_16_3;
	/** 面瘫 **/
	@ViewInject(R.id.frg_16_4)
	private EditText frg_16_4;
	/** 上下肢运动功能（小写R） **/
	@ViewInject(R.id.frg_16_1r)
	private EditText frg_16_1r;
	/** 上下肢运动功能(小写L) **/
	@ViewInject(R.id.frg_16_1l)
	private EditText frg_16_1l;
	/** 左腿 **/
	@ViewInject(R.id.frg_16_2l)
	private EditText frg_16_2l;
	/** 右腿 **/
	@ViewInject(R.id.frg_16_2r)
	private EditText frg_16_2r;
	/** 共济失调 **/
	@ViewInject(R.id.frg_16_7)
	private EditText frg_16_7;
	/** 感觉 **/
	@ViewInject(R.id.frg_16_8)
	private EditText frg_16_8;
	/** 语言表达能力 **/
	@ViewInject(R.id.frg_16_9)
	private EditText frg_16_9;
	/** 构音障碍 **/
	@ViewInject(R.id.frg_16_10)
	private EditText frg_16_10;
	/** 消退和不注意 **/
	@ViewInject(R.id.frg_16_11)
	private EditText frg_16_11;
	@ViewInject(R.id.submit)
	private Button submit;
	@ViewInject(R.id.total)
	private TextView total;
	View root;
	MWDApplication ma;
	String eid = "0";
	EditTextUtil etUtil;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_nihss,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		ma = (MWDApplication) getActivity().getApplication();
		// initSpinner();
		submit.setOnClickListener(this);
		ArrayList<EditText> list = new ArrayList<EditText>();
		list.add(frg_16_1a);
		list.add(frg_16_1b);
		list.add(frg_16_1c);
		list.add(frg_16_2);
		list.add(frg_16_3);
		list.add(frg_16_4);
		list.add(frg_16_1l);
		list.add(frg_16_1r);
		list.add(frg_16_2l);
		list.add(frg_16_2r);
		list.add(frg_16_7);
		list.add(frg_16_8);
		list.add(frg_16_9);
		list.add(frg_16_10);
		list.add(frg_16_11);
		etUtil = new EditTextUtil(getActivity(), list);
		((MainActivity) getActivity()).setSubmitVisibily(submit);
		init();
		setDataByPost(1);
		return this.root;
	}

	/**
	 * 设置范围和总分
	 */
	private void init() {
		etUtil.setRegion(total, frg_16_1a, 3, 0);
		etUtil.setRegion(total, frg_16_1b, 2, 0);
		etUtil.setRegion(total, frg_16_1c, 2, 0);
		etUtil.setRegion(total, frg_16_2, 2, 0);
		etUtil.setRegion(total, frg_16_3, 3, 0);
		etUtil.setRegion(total, frg_16_4, 3, 0);

		ArrayList<Integer> list0 = new ArrayList<Integer>();
		list0.add(0);
		list0.add(1);
		list0.add(2);
		list0.add(3);
		list0.add(4);
		list0.add(9);
		etUtil.setRegion_Barthel(total, frg_16_1l, list0);
		etUtil.setRegion_Barthel(total, frg_16_1r, list0);
		etUtil.setRegion_Barthel(total, frg_16_2l, list0);
		etUtil.setRegion_Barthel(total, frg_16_2r, list0);
		etUtil.setRegion(total, frg_16_7, 2, 0);
		etUtil.setRegion(total, frg_16_8, 2, 0);
		etUtil.setRegion(total, frg_16_9, 3, 0);
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(0);
		list1.add(1);
		list1.add(2);
		list1.add(9);
		etUtil.setRegion_Barthel(total, frg_16_10, list1);
		etUtil.setRegion(total, frg_16_11, 2, 0);
	}

	/**
	 * 数据请求1fetch2edit
	 */
	private void setDataByPost(int i) {
		dia = new ProgressDialog(getActivity());
		dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		dia.show();
		ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
		localArrayList.add(new BasicNameValuePair("token", Constant.token));
		localArrayList.add(new BasicNameValuePair("nums", ma.nums));
		if (i == 1) {
			localArrayList.add(new BasicNameValuePair("id", ma.pid));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.NIHSS_URL, mHandler);
		} else if (i == 2) {
			String temp = total.getText().toString();
			if (temp.equals("")) {
				DialogUtils.NewDialog("评分总分不符合纳入标准，请核查！", getActivity());
				dia.dismiss();
				return;
			}
			int number = Integer.valueOf(temp);
			if (number > 25 || number <= 3) {
				DialogUtils.NewDialog("评分总分不符合纳入标准，请核查！", getActivity());
				dia.dismiss();
				return;
			}
			localArrayList.add(new BasicNameValuePair("eid", ma.pid));
			localArrayList.add(new BasicNameValuePair("score", getJS()));
			localArrayList.add(new BasicNameValuePair("total", total.getText()
					.toString()));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.NIHSS_ADD_EDIT, mEidHandler);
		}
		this.rThread.start();
	}

	Handler mEidHandler = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();
			String result = msg.obj.toString();
			if (null != result) {
				JSONObject json;
				try {
					json = new JSONObject(result);
					int status = json.getInt("status");
					if (status == 1) {
						Toast.makeText(getActivity(), "保存成功",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "保存失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	};

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();
			String reuslt = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(reuslt);
				if (json.getString("status").equals("-3")) {
				} else {
					eid = ma.pid;
					JSONObject js = json.getJSONObject("info");
					String str_centernum = js.has("centernum") ? js
							.getString("centernum") : "";
					// centernum.setText(str_centernum);
					// centernum.setEnabled(false);
					String str_abbreviation = js.has("abbreviation") ? js
							.getString("abbreviation") : "";
					// abbreviation.setText(str_abbreviation);
					// abbreviation.setEnabled(false);
					JSONObject j = js.getJSONObject("score");
					// if(){
					//
					// }
					String str_frg_16_1a = j.has("1a") ? j.getString("1a") : "";
					frg_16_1a.setText(str_frg_16_1a);
					// frg_16_1a.setEnabled(false);
					String str_frg_16_1b = j.has("1b") ? j.getString("1b") : "";
					frg_16_1b.setText(str_frg_16_1b);
					// frg_16_1b.setEnabled(false);
					String str_frg_16_1c = j.has("1c") ? j.getString("1c") : "";
					frg_16_1c.setText(str_frg_16_1c);
					// frg_16_1c.setEnabled(false);
					String str_frg_16_2 = j.has("2") ? j.getString("2") : "";
					frg_16_2.setText(str_frg_16_2);
					// frg_16_2.setEnabled(false);
					String str_frg_16_3 = j.has("3") ? j.getString("3") : "";
					frg_16_3.setText(str_frg_16_3);
					// frg_16_3.setEnabled(false);
					String str_frg_16_4 = j.has("4") ? j.getString("4") : "";
					frg_16_4.setText(str_frg_16_4);
					// frg_16_4.setEnabled(false);
					String str_frg_16_1r = j.has("1r") ? j.getString("1r") : "";
					frg_16_1r.setText(str_frg_16_1r);
					// frg_16_1r.setEnabled(false);
					String str_frg_16_1l = j.has("1l") ? j.getString("1l") : "";
					frg_16_1l.setText(str_frg_16_1l);
					// frg_16_1l.setEnabled(false);
					String str_frg_16_2l = j.has("2l") ? j.getString("2l") : "";
					frg_16_2l.setText(str_frg_16_2l);
					// frg_16_2l.setEnabled(false);
					String str_frg_16_2r = j.has("2r") ? j.getString("2r") : "";
					frg_16_2r.setText(str_frg_16_2r);
					// frg_16_2r.setEnabled(false);
					String str_frg_16_7 = j.has("7") ? j.getString("7") : "";
					frg_16_7.setText(str_frg_16_7);
					// frg_16_7.setEnabled(false);
					String str_frg_16_8 = j.has("8") ? j.getString("8") : "";
					frg_16_8.setText(str_frg_16_8);
					// frg_16_8.setEnabled(false);
					String str_frg_16_9 = j.has("9") ? j.getString("9") : "";
					frg_16_9.setText(str_frg_16_9);
					// frg_16_9.setEnabled(false);
					String str_frg_16_10 = j.has("10") ? j.getString("10") : "";
					frg_16_10.setText(str_frg_16_10);
					// frg_16_10.setEnabled(false);
					String str_frg_16_11 = j.has("11") ? j.getString("11") : "";
					frg_16_11.setText(str_frg_16_11);
					// frg_16_11.setEnabled(false);
					String s = js.has("total") ? js.getString("total") : "";
					total.setText(s);
				}

			} catch (JSONException e) {
			}
		};
	};

	/** 将参数转换为JSON */
	private String getJS() {
		JSONObject jo1 = new JSONObject();
		// map.put("centernum", centernum.getText().toString());
		// map.put("abbreviation", abbreviation.getText().toString());
		try {
			jo1.put("1a", frg_16_1a.getText().toString());
			jo1.put("1b", frg_16_1b.getText().toString());
			jo1.put("1c", frg_16_1c.getText().toString());
			jo1.put("2", frg_16_2.getText().toString());
			jo1.put("3", frg_16_3.getText().toString());
			jo1.put("4", frg_16_4.getText().toString());
			jo1.put("1l", frg_16_1l.getText().toString());
			jo1.put("1r", frg_16_1r.getText().toString());
			jo1.put("2l", frg_16_2l.getText().toString());
			jo1.put("2r", frg_16_2r.getText().toString());
			jo1.put("7", frg_16_7.getText().toString());
			jo1.put("8", frg_16_8.getText().toString());
			jo1.put("9", frg_16_9.getText().toString());
			jo1.put("10", frg_16_10.getText().toString());
			jo1.put("11", frg_16_11.getText().toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo1.toString();

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		setDataByPost(2);
	}
}
