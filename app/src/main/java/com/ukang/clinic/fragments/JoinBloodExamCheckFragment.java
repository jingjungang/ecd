package com.ukang.clinic.fragments;
/**
 * 凝血实验检查
 * jjg 2016-04-21 08:28:22
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;
import com.ukang.clinicaltrial.view.Mdate;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JoinBloodExamCheckFragment extends Fragment {

    View root;
    /**
	 * 结果
	 */
	@ViewInject(R.id.frg_8_1a)
	private EditText frg_8_1a;
	@ViewInject(R.id.frg_8_1b)
	private EditText frg_8_1b;
	@ViewInject(R.id.frg_8_1c)
	private EditText frg_8_1c;
	@ViewInject(R.id.frg_8_1d)
	private EditText frg_8_1d;
	/**
	 * 其他单位
	 */
	@ViewInject(R.id.frg_8_2a)
	private EditText frg_8_2a;
	@ViewInject(R.id.frg_8_2b)
	private EditText frg_8_2b;
	@ViewInject(R.id.frg_8_2c)
	private EditText frg_8_2c;
	@ViewInject(R.id.frg_8_2d)
	private EditText frg_8_2d;
	/*
	 * 异常值
	 */
	/**
	 * 凝血酶原时间（PT）
	 */
	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;
	@ViewInject(R.id.frg_8_3a)
	private RadioButton frg_8_3a;
	@ViewInject(R.id.frg_8_3b)
	private RadioButton frg_8_3b;
	@ViewInject(R.id.frg_8_3c)
	private RadioButton frg_8_3c;
	@ViewInject(R.id.frg_8_3d)
	private RadioButton frg_8_3d;
	/**
	 * 活化部分凝血活酶时间
	 */
	@ViewInject(R.id.radioGroup_1)
	private RadioGroup radioGroup_1;
	@ViewInject(R.id.frg_8_4a)
	private RadioButton frg_8_4a;
	@ViewInject(R.id.frg_8_4b)
	private RadioButton frg_8_4b;
	@ViewInject(R.id.frg_8_4c)
	private RadioButton frg_8_4c;
	@ViewInject(R.id.frg_8_4d)
	private RadioButton frg_8_4d;
	/**
	 * 凝血酶时间（TT）
	 */
	@ViewInject(R.id.radioGroup_2)
	private RadioGroup radioGroup_2;
	@ViewInject(R.id.frg_8_5a)
	private RadioButton frg_8_5a;
	@ViewInject(R.id.frg_8_5b)
	private RadioButton frg_8_5b;
	@ViewInject(R.id.frg_8_5c)
	private RadioButton frg_8_5c;
	@ViewInject(R.id.frg_8_5d)
	private RadioButton frg_8_5d;
	/**
	 * 纤维蛋白原(FIB)
	 */
	@ViewInject(R.id.radioGroup_3)
	private RadioGroup radioGroup_3;
	@ViewInject(R.id.frg_8_6a)
	private RadioButton frg_8_6a;
	@ViewInject(R.id.frg_8_6b)
	private RadioButton frg_8_6b;
	@ViewInject(R.id.frg_8_6c)
	private RadioButton frg_8_6c;
	@ViewInject(R.id.frg_8_6d)
	private RadioButton frg_8_6d;
	/**
	 * 异常说明
	 */
	@ViewInject(R.id.frg_8_7a)
	private EditText frg_8_7a;
	@ViewInject(R.id.frg_8_7b)
	private EditText frg_8_7b;
	@ViewInject(R.id.frg_8_7c)
	private EditText frg_8_7c;
	@ViewInject(R.id.frg_8_7d)
	private EditText frg_8_7d;
	
	@ViewInject(R.id.ctime)
	private Mdate mtime;
	@ViewInject(R.id.submit)
	private Button submit;
	
	private RequestThread rThread;
	public ProgressDialog dia;
	MWDApplication ma;
	
    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.root = paramLayoutInflater.inflate(R.layout.layout_blood_coagulation_check, paramViewGroup, false);
        ViewUtils.inject(this, this.root);
        dia = new ProgressDialog(getActivity());
		dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		ma = (MWDApplication) getActivity().getApplication();
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				updateDataByPost(2);
			}
		});
		((MainActivity)getActivity()).setSubmitVisibily(submit);
		updateDataByPost(1);
        return this.root;
    }
    /**
	 * 数据请求 1请求，2编辑
	 */
	private void updateDataByPost(int i) {
		dia.show();
		ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
		localArrayList.add(new BasicNameValuePair("nums", ma.nums));
		localArrayList.add(new BasicNameValuePair("token", Constant.token));
		if(i == 1){
			localArrayList.add(new BasicNameValuePair("id", ma.pid));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.BLOOD_COAGULATION_URL, mHandler);

		}else {
			localArrayList.add(new BasicNameValuePair("eid", ma.pid));
			localArrayList.add(new BasicNameValuePair("js", getJSON().toString()));
			localArrayList.add(new BasicNameValuePair("ctime", mtime.getText().toString()));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.BLOOD_COAGULATION_URL, mHandler_edit);
		}
		this.rThread.start();
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();
			try {
				if (null != msg.obj) {
					String reuslt = msg.obj.toString();
					JSONObject localJSONObject = new JSONObject(reuslt);
					if (localJSONObject.getString("status").toString()
							.equals("1")) {
						JSONObject js = localJSONObject.getJSONObject("info");
						setLocalData(js);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	Handler mHandler_edit = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();

			if (null != msg.obj) {
				String reuslt = msg.obj.toString();
				try {
					JSONObject localJSONObject = new JSONObject(reuslt);
					// status -3无数据
					if (localJSONObject.getString("status").toString()
							.equals("1")) {
						Toast.makeText(getActivity(), "保存成功",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "保存失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	/**
	 * 显示历史记录
	 * 
	 * @param localJSONObject
	 */
	protected void setLocalData(JSONObject localJSONObject) {
		JSONObject js = null;
		String ctime = "";
		try {
			js = localJSONObject.getJSONObject("lab");
			frg_8_1a.setText(js.getString("1"));
			frg_8_1b.setText(js.getString("2"));
			frg_8_1c.setText(js.getString("3"));
			frg_8_1d.setText(js.getString("4"));

			js = localJSONObject.getJSONObject("oname");
			frg_8_2a.setText(js.getString("1"));
			frg_8_2b.setText(js.getString("2"));
			frg_8_2c.setText(js.getString("3"));
			frg_8_2d.setText(js.getString("4"));
			

			js = localJSONObject.getJSONObject("abn");
			String temp = "";
			temp = js.getString("1");
			if (!TextUtils.isEmpty(temp)) {
				SetRadioGroupChecked(temp, radioGroup, frg_8_3a, frg_8_3b,
						frg_8_3c, frg_8_3d);
			}
			temp = js.getString("2");
			if (!TextUtils.isEmpty(temp)) {
				SetRadioGroupChecked(temp, radioGroup_1, frg_8_4a, frg_8_4b,
						frg_8_4c, frg_8_4d);
			}
			temp = js.getString("3");
			if (!TextUtils.isEmpty(temp)) {
				SetRadioGroupChecked(temp, radioGroup_2, frg_8_5a, frg_8_5b,
						frg_8_5c, frg_8_5d);
			}
			temp = js.getString("4");
			if (!TextUtils.isEmpty(temp)) {
				SetRadioGroupChecked(temp, radioGroup_3, frg_8_6a, frg_8_6b,
						frg_8_6c, frg_8_6d);
			}
			
			js = localJSONObject.getJSONObject("abns");
			frg_8_7a.setText(js.getString("1"));
			frg_8_7b.setText(js.getString("2"));
			frg_8_7c.setText(js.getString("3"));
			frg_8_7d.setText(js.getString("4"));

			ctime = localJSONObject.getString("ctime");
			if (!ctime.equals("")) {
				mtime.setText(ctime);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置Radiogroup
	 * 
	 * @param index
	 * @param g
	 * @param b1
	 * @param b2
	 * @param b3
	 * @param b4
	 */
	private void SetRadioGroupChecked(String index, RadioGroup g,
			RadioButton b1, RadioButton b2, RadioButton b3, RadioButton b4) {
		if (index.equals("1")) {
			g.check(b1.getId());
		} else if (index.equals("2")) {
			g.check(b2.getId());
		} else if (index.equals("3")) {
			g.check(b3.getId());
		} else {
			g.check(b4.getId());
		}
	}

	/**
	 * 获取json数据
	 */
	private JSONObject getJSON() {
		JSONObject Json = null;
		try {
			Json = new JSONObject();
			JSONObject json1 = new JSONObject();
			json1.put("1", frg_8_1a.getText().toString());
			json1.put("2", frg_8_1b.getText().toString());
			json1.put("3", frg_8_1c.getText().toString());
			json1.put("4", frg_8_1d.getText().toString());
			Json.put("oname", json1);
			JSONObject json2 = new JSONObject();
			json2.put("1", frg_8_2a.getText().toString());
			json2.put("2", frg_8_2b.getText().toString());
			json2.put("3", frg_8_2c.getText().toString());
			json2.put("4", frg_8_2d.getText().toString());
			Json.put("abn", json2);
			JSONObject json3 = new JSONObject();
			json3.put("1", getRadio(frg_8_3a, frg_8_3b, frg_8_3c, frg_8_3d) + "");
			json3.put("2", getRadio(frg_8_4a, frg_8_4b, frg_8_4c, frg_8_4d) + "");
			json3.put("3", getRadio(frg_8_5a, frg_8_5b, frg_8_5c, frg_8_5d) + "");
			json3.put("4", getRadio(frg_8_6a, frg_8_6b, frg_8_6c, frg_8_6d) + "");
			Json.put("lab", json3);
			JSONObject json4 = new JSONObject();
			json4.put("1", frg_8_7a.getText().toString());
			json4.put("2", frg_8_7b.getText().toString());
			json4.put("3", frg_8_7c.getText().toString());
			json4.put("4", frg_8_7d.getText().toString());
			Json.put("abns", json4);
			return Json;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json;
	}

	/**
	 * 获取RadioButton选项
	 */
	private int getRadio(RadioButton r1, RadioButton r2, RadioButton r3, RadioButton r4) {
		if (r1.isChecked()) {
			return 1;
		} else if (r2.isChecked()) {
			return 2;
		} else if (r3.isChecked()) {
			return 3;
		} else if (r4.isChecked()) {
			return 4;
		}
		return 1;
	}
}
