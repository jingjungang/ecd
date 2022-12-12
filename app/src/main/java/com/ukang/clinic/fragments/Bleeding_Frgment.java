package com.ukang.clinic.fragments;

/**
 * 出血事件评价
 * jjg
 * 2016年6月8日 10:10:26
 */

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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Bleeding_Frgment extends Fragment implements OnClickListener {

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
	
	@ViewInject(R.id.submit)
	private Button submit;
	View root;
	MWDApplication ma;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_dead,
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
		setDataByPost(1);
		((MainActivity)getActivity()).setSubmitVisibily(submit);
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
		if (i == 1) {
			localArrayList.add(new BasicNameValuePair("id", ma.pid));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.FEE_OUTSIDE_URL, mHandler);
		} else if (i == 2) {
			localArrayList.add(new BasicNameValuePair("eid", ma.pid));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.FEE_OUTSIDE_EDIT_URL, mEidHandler);
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

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();
			String reuslt = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(reuslt);
				JSONObject js = json.getJSONObject("info");
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
