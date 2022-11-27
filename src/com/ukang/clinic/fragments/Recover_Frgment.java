package com.ukang.clinic.fragments;

/**
 * 复发
 * jjg
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;
import com.ukang.clinic.utils.DateUtilities;
import com.ukang.clinicaltrial.view.Mdate;

public class Recover_Frgment extends Fragment implements OnClickListener {

	private RequestThread rThread;
	public ProgressDialog dia;

	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;
	// 时间
	@ViewInject(R.id.l1)
	LinearLayout l1;
	// 原因
	@ViewInject(R.id.l2)
	LinearLayout l2;

	// 未发生
	@ViewInject(R.id.n)
	RadioButton rb_no;

	@ViewInject(R.id.y)
	RadioButton rb_y;

	@ViewInject(R.id.mdate)
	Mdate mdate;
	@ViewInject(R.id.reson)
	EditText reson;
	@ViewInject(R.id.submit)
	private Button submit;
	View root;
	MWDApplication ma;

	String fetch_id = "";

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_recover,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		ma = (MWDApplication) getActivity().getApplication();
		submit.setOnClickListener(this);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup Group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.y:
					l1.setVisibility(View.VISIBLE);
					l2.setVisibility(View.VISIBLE);
					break;
				case R.id.n:
					l1.setVisibility(View.GONE);
					l2.setVisibility(View.GONE);
					break;
				}
			}
		});
		((MainActivity)getActivity()).setSubmitVisibily(submit);
		setDataByPost(1);
		return this.root;
	}

	/**
	 * 数据请求 1fetch 2edit
	 */
	private void setDataByPost(int i) {
		dia = new ProgressDialog(getActivity());
		dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		dia.show();
		ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
		localArrayList.add(new BasicNameValuePair("token", Constant.token));
		localArrayList.add(new BasicNameValuePair("nums", ma.nums));
		localArrayList.add(new BasicNameValuePair("pid", ma.pid));
		if (i == 1) {
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.RECOVER_URL, fetchHandler);
		} else if (i == 2) {
			localArrayList.add(new BasicNameValuePair("id", fetch_id));
			localArrayList.add(new BasicNameValuePair("no",
					rb_y.isChecked() ? "1" : "0"));
			localArrayList.add(new BasicNameValuePair("time", mdate.getText()
					.toString()));
			localArrayList.add(new BasicNameValuePair("text", reson.getText()
					.toString()));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.RECOVER_EDIT_URL, mEidHandler);
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

	Handler fetchHandler = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();
			String reuslt = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(reuslt);
				String status = json.getString("status");
				if (status.equals("-1")) {
					fetch_id = "0";
				} else if (status.equals("1")) {
					String temp = json.getString("info");
					json = new JSONObject(temp);
					fetch_id = json.getString("id");
					String no = json.getString("no");
					if (no.equals("1")) {
						rb_y.setChecked(true);
					} else {
						rb_no.setChecked(true);
					}
					reson.setText(json.getString("text"));
					mdate.setText(!json.getString("time").equals("") ? json
							.getString("time") : DateUtilities.getSystemDate());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		};
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		setDataByPost(2);
	}
}
