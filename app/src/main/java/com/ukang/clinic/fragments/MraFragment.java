package com.ukang.clinic.fragments;

/**
 * 头颅MRA
 * jjg
 * 2016年4月22日 13:56:01
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.activity.add.AddMraActivity;
import com.ukang.clinic.adapter.MraListAdapter;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MraFragment extends Fragment implements OnClickListener {

	View root;
	/**
	 * 新增
	 */
	@ViewInject(R.id.add)
	private Button add;
	@ViewInject(R.id.lv)
	private ListView lv;
	// **********************分割线**************************
	Context mContext;
	private RequestThread rThread;
	MWDApplication application;
	public ProgressDialog dia;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_mra,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		((MainActivity)getActivity()).setSubmitVisibily(add);
		return this.root;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		dia = new ProgressDialog(getActivity());
		dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		mContext = getActivity();
		application = ((MWDApplication) mContext.getApplicationContext());
		fetchDataFromWebservice();
		add.setOnClickListener(this);
	}

	/**
	 * 获取历史记录
	 */
	private void fetchDataFromWebservice() {
		if (MWDUtils.isNetworkConnected(mContext)) {
			dia.show();
			ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
			localArrayList.add(new BasicNameValuePair("pid", application.pid));
			localArrayList
					.add(new BasicNameValuePair("nums", application.nums));
			localArrayList.add(new BasicNameValuePair("token", Constant.token));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.MRA_URL, mHandler);
			this.rThread.start();
		}
	}

	Handler mHandler = new Handler() {

		public void handleMessage(Message paramAnonymousMessage) {
			try {
				dia.dismiss();
				if (paramAnonymousMessage.obj != null) {
					String str = paramAnonymousMessage.obj.toString();
					JSONObject localJSONObject = new JSONObject(str);
					if (localJSONObject.getString("status").toString()
							.equals("1")) {
						str = localJSONObject.getString("info");
						JSONArray ja = new JSONArray(str);
						MraListAdapter adapter = new MraListAdapter(mContext,
								ja);
						lv.setAdapter(adapter);
					}
				} else {

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
		case R.id.add:
			application.current_frg_index = 15;
			mContext.startActivity(new Intent(mContext, AddMraActivity.class));
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
