package com.ukang.clinic.fragments;

/**
 * 基本体征
 * jjg 2016年4月19日15:38:30
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;
import com.ukang.clinic.thread.XThread;
import com.ukang.clinic.utils.DialogUtils;
import com.ukang.clinicaltrial.view.Mdate;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BaseFeatureFragment extends Fragment {

	View root;
	@ViewInject(R.id.Mdate)
	/**知情同意签署日期*/
	private Mdate Mdate;
	/**
	 * 门诊号
	 */
	@ViewInject(R.id.patient_no)
	private EditText patient_no;

	/**
	 * 男女
	 */
	@ViewInject(R.id.radioGroup)
	private RadioGroup g_sex;
	@ViewInject(R.id.radioMale)
	private RadioButton radioMale;
	@ViewInject(R.id.radioFemale)
	private RadioButton radioFemale;

	// 婚姻
	@ViewInject(R.id.marray)
	private RadioGroup g_marray;
	@ViewInject(R.id.married)
	private RadioButton married;
	@ViewInject(R.id.marraying)
	private RadioButton marraying;

	// 民族
	@ViewInject(R.id.radioGroup1)
	private RadioGroup g_nation;
	@ViewInject(R.id.rb1)
	private RadioButton rb_nation_h;
	@ViewInject(R.id.rb2)
	private RadioButton rb_nation_other;
	@ViewInject(R.id.nation)
	private EditText nation;

	// 职业
	@ViewInject(R.id.job)
	private RadioGroup g_job;
	@ViewInject(R.id.grad1)
	private RadioButton job1;
	@ViewInject(R.id.grad2)
	private RadioButton job2;
	@ViewInject(R.id.grad3)
	private RadioButton job3;

	// 生日
	@ViewInject(R.id.birthday)
	private Mdate birthday;

	// 身高体重
	@ViewInject(R.id.height)
	private EditText height;
	@ViewInject(R.id.weight)
	private EditText weight;

	@ViewInject(R.id.submit)
	private Button submit;

	Context mContent;
	private RequestThread rThread;
	MWDApplication application;
	int nations;
	boolean h = false;
	boolean w = false;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_base_features,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		mContent = getActivity();
		application = ((MWDApplication) mContent.getApplicationContext());
		addBtnLis();
		((MainActivity) getActivity()).setSubmitVisibily(submit);
		getDataByPost();
		return this.root;
	}

	private void addBtnLis() {
		submit.setOnClickListener(commitBtn);
	}

	private View.OnClickListener commitBtn = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			onEdit();
		}
	};

	private void getDataByPost() {
		if (MWDUtils.isNetworkConnected(mContent)) {
			DialogUtils.startDialog(getActivity());
			ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
			localArrayList.add(new BasicNameValuePair("pid", application.pid));
			localArrayList.add(new BasicNameValuePair("token", Constant.token));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.BASE_FEATURE_URL, mHandler);
			this.rThread.start();
		}

	}

	Handler mHandler = new Handler() {

		public void handleMessage(Message paramAnonymousMessage) {
			try {
				DialogUtils.stopDialog();
				String str = paramAnonymousMessage.obj.toString();
				JSONObject localJSONObject = new JSONObject(str);
				str = localJSONObject.getString("info");
				String status = localJSONObject.getString("status").toString();
				if (status.equals("1")) {
					localJSONObject = new JSONObject(str);
					setLocalData(localJSONObject);
				} else if (status.equals("-1")) { // feature表无数据，在patients表查询数据
					localJSONObject = new JSONObject(str);
					setLocalData(localJSONObject);
				}
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
		}
	};

	/**
	 * 提交请求
	 */
	private void onEdit() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("pid", application.pid);
		params.addBodyParameter("informeddate", Mdate.getText());
		params.addBodyParameter("outpatientnum", patient_no.getText()
				.toString().trim());
		params.addBodyParameter("sex", getSex() + "");
		params.addBodyParameter("marriage", getMarray() + "");
		nations = getNation();
		params.addBodyParameter("nation", nations + "");
		if (nations == 2) {
			params.addBodyParameter("nations", nation.getText().toString()
					.trim());
		}
		params.addBodyParameter("birth", birthday.getText().toString().trim());
		params.addBodyParameter("occupation", getJob() + "");
		params.addBodyParameter("weight", weight.getText().toString().trim());
		params.addBodyParameter("height", height.getText().toString().trim());
		XThread thread = new XThread(getActivity(), 0, params,
				Constant.BASE_FEATURE_EDIT_URL, xHandler);
		thread.setShowDia(true);
		thread.start();
	}

	private Handler xHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String result = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(result);
				int status = json.getInt("status");
				if (status == 1) {
					Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 获取性别选项
	 */
	private int getSex() {
		if (radioMale.isChecked()) {
			return 1;
		} else if (radioFemale.isChecked()) {
			return 2;
		}
		return 1;
	}

	/**
	 * 获取婚姻选项
	 */
	private int getMarray() {
		if (married.isChecked()) {
			return 1;
		} else if (marraying.isChecked()) {
			return 2;
		}
		return 1;
	}

	/**
	 * 获取民族选项
	 */
	private int getNation() {
		if (rb_nation_h.isChecked()) {
			return 1;
		} else if (rb_nation_other.isChecked()) {
			return 2;
		}
		return 1;
	}

	/**
	 * 获取职位选项
	 */
	private int getJob() {
		if (job1.isChecked()) {
			return 1;
		} else if (job2.isChecked()) {
			return 2;
		} else if (job3.isChecked()) {
			return 3;
		}
		return 1;
	}

	/**
	 * 查看数据
	 */
	protected void setLocalData(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			Mdate.setText(jo.getString("informeddate"));
			String temp = "";
			temp = jo.getString("sex");
			if (temp.equals("1")) {// 1男2女
				radioMale.setChecked(true);
				radioFemale.setChecked(false);
			} else {
				radioMale.setChecked(false);
				radioFemale.setChecked(true);
			}
			temp = jo.getString("marriage");
			if (temp.equals("1")) { // 1已婚2未婚
				married.setChecked(true);
				marraying.setChecked(false);
			} else {
				married.setChecked(false);
				marraying.setChecked(true);
			}
			temp = jo.getString("nation");
			if (temp.equals("1")) {
				rb_nation_h.setChecked(true);
				rb_nation_other.setChecked(false);
				nation.setText("");
			} else {
				rb_nation_h.setChecked(false);
				rb_nation_other.setChecked(true);
				nation.setText(jo.getString("nations"));
			}
			birthday.setText(jo.getString("birth"));
			patient_no.setText(jo.getString("outpatientnum"));
			temp = jo.getString("occupation");
			if (temp.equals("1")) {
				job1.setChecked(true);
				job2.setChecked(false);
				job3.setChecked(false);
			} else if (temp.equals("2")) {
				job1.setChecked(false);
				job2.setChecked(true);
				job3.setChecked(false);
			} else {
				job1.setChecked(false);
				job2.setChecked(false);
				job3.setChecked(true);
			}
			String w = jo.getString("weight");
			String h = jo.getString("height");
			if (w.equals("0")) {
				weight.setText("");
			} else {
				weight.setText(w);
			}
			if (h.equals("0")) {
				height.setText("");
			} else {
				height.setText(h);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
