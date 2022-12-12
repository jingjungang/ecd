package com.ukang.clinic.fragments;

/**
 * 治疗措施记录
 * 景俊钢
 * 2016年4月23日 17:06:36
 */

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TreatmentRecordsFragment extends Fragment implements
		OnClickListener {

	View root;
	/**
	 * 针灸
	 */
	@ViewInject(R.id.radioGroup_1)
	private RadioGroup radioGroup;
	@ViewInject(R.id.r0)
	private RadioButton r0;
	@ViewInject(R.id.r1)
	private RadioButton r1;
	@ViewInject(R.id.f_22_1)
	private EditText f_22_1;
	@ViewInject(R.id.f_22_2)
	private EditText f_22_2;
	/**
	 * 康复
	 */
	@ViewInject(R.id.radioGroup_2)
	private RadioGroup radioGroup2;
	@ViewInject(R.id.r2)
	private RadioButton r2;
	@ViewInject(R.id.r3)
	private RadioButton r3;
	@ViewInject(R.id.f_22_3)
	private EditText f_22_3;
	@ViewInject(R.id.f_22_4)
	private EditText f_22_4;

	/** 提交 **/
	@ViewInject(R.id.submit)
	private Button submit;

	// **********************分割线**************************
	Context mContent;
	private RequestThread rThread;
	MWDApplication application;
	public ProgressDialog dia;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(
				R.layout.layout_treatment_records, paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		((MainActivity)getActivity()).setSubmitVisibily(submit);
		return this.root;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		dia = new ProgressDialog(getActivity());
		dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		mContent = getActivity();
		application = ((MWDApplication) mContent.getApplicationContext());
		fetchData(0);
		submit.setOnClickListener(this);
	}

	/**
	 * 获取历史记录
	 */
	private void fetchData(int index) {
		if (MWDUtils.isNetworkConnected(mContent)) {
			dia.show();
			ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
			localArrayList.add(new BasicNameValuePair("pid", application.pid));
			localArrayList
					.add(new BasicNameValuePair("nums", application.nums));
			localArrayList.add(new BasicNameValuePair("token", Constant.token));
			if (index == 0) {
				this.rThread = new RequestThread(localArrayList, "http",
						"post", Constant.TREATMENTR_ECORDS_URL, mHandler);
				this.rThread.start();
			} else {
				if (r0.isChecked()) {
					localArrayList.add(new BasicNameValuePair("acupunctures",
							"1"));
				} else {
					localArrayList.add(new BasicNameValuePair("acupunctures",
							"2"));
					localArrayList.add(new BasicNameValuePair("acupuncturep",
							f_22_1.getText().toString().trim()));
					localArrayList.add(new BasicNameValuePair("acupuncturesj",
							f_22_2.getText().toString().trim()));
				}

				if (r2.isChecked()) {
					localArrayList
							.add(new BasicNameValuePair("recoverys", "1"));
				} else {
					localArrayList
							.add(new BasicNameValuePair("recoverys", "2"));
					localArrayList.add(new BasicNameValuePair("recoveryp",
							f_22_3.getText().toString().trim()));
					localArrayList.add(new BasicNameValuePair("recoverysj",
							f_22_4.getText().toString().trim()));
				}

				this.rThread = new RequestThread(localArrayList, "http",
						"post", Constant.TREATMENTR_ECORDS_EDIT_URL, emHandler);
				this.rThread.start();
			}

		}
	}

	Handler mHandler = new Handler() {

		public void handleMessage(Message paramAnonymousMessage) {
			try {
				dia.dismiss();
				if (paramAnonymousMessage.what != -1) {
					String str = paramAnonymousMessage.obj.toString();
					JSONObject localJSONObject = new JSONObject(str);
					if (localJSONObject.getString("status").toString()
							.equals("1")) {
						str = localJSONObject.getString("info");
						localJSONObject = new JSONObject(str);
						String acupunctures = "", acupuncturep = "", acupuncturesj = "", recoverys = "", recoveryp = "", recoverysj = "";

						acupunctures = localJSONObject.has("acupunctures") ? localJSONObject
								.getString("acupunctures") : "";
						acupuncturep = localJSONObject.has("acupunctures") ? localJSONObject
								.getString("acupunctures") : "";
						acupuncturesj = localJSONObject.has("acupunctures") ? localJSONObject
								.getString("acupunctures") : "";

						recoverys = localJSONObject.has("recoverys") ? localJSONObject
								.getString("recoverys") : "";
						recoveryp = localJSONObject.has("recoveryp") ? localJSONObject
								.getString("recoveryp") : "";
						recoverysj = localJSONObject.has("recoverysj") ? localJSONObject
								.getString("recoverysj") : "";

						if (acupunctures.equals("2")) {
							r0.setChecked(false);
							r1.setChecked(true);
							f_22_1.setText(acupuncturep);
							f_22_2.setText(acupuncturesj);
						} else {
							r0.setChecked(true);
							r1.setChecked(false);
						}

						if (recoverys.equals("2")) {
							r2.setChecked(false);
							r3.setChecked(true);
							f_22_3.setText(recoveryp);
							f_22_4.setText(recoverysj);
						} else {
							r2.setChecked(true);
							r3.setChecked(false);
						}

					} else {

					}
				} else {

				}

			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
		}
	};
	Handler emHandler = new Handler() {

		public void handleMessage(Message paramAnonymousMessage) {
			try {
				dia.dismiss();
				if (paramAnonymousMessage.what != -1) {
					String str = paramAnonymousMessage.obj.toString();
					JSONObject localJSONObject = new JSONObject(str);
					if (localJSONObject.getString("status").toString()
							.equals("1")) {
						Toast.makeText(mContent, "保存成功", Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(mContent, "保存失败", Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Toast.makeText(mContent, "保存失败", Toast.LENGTH_SHORT).show();
				}

			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit:
			fetchData(1);
			break;
		}
	}
}
