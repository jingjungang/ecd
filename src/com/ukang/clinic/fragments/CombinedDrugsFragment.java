package com.ukang.clinic.fragments;

/**
 * 合并用药
 * jjg
 * 2016年4月21日 13:18:33
 */
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.activity.add.AddCombinationDrug;
import com.ukang.clinic.adapter.CombinationDrugListAdapter;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;

public class CombinedDrugsFragment extends Fragment implements OnClickListener {

	View root;
	/**
	 * 新增合并用药
	 */
	@ViewInject(R.id.add)
	private Button add;
	@ViewInject(R.id.lv)
	private ListView lv;
	@ViewInject(R.id.title_1)
	private LinearLayout title_1;
	@ViewInject(R.id.title_2)
	private LinearLayout title_2;
	@ViewInject(R.id.still)
	private TextView tv_still;
	/*
	 * 是否有合并用药
	 */
	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;
	@ViewInject(R.id.r1)
	private RadioButton r1;
	@ViewInject(R.id.r2)
	private RadioButton r2;
	@ViewInject(R.id.save)
	private Button btn_save;
	// **********************分割线**************************
	Context mContent;
	private RequestThread rThread;
	MWDApplication application;
	public ProgressDialog dia;
	String visit_no = "1";

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(
				R.layout.layout_drug_combination, paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		init();
		fetchDataFromWebservice();
		return this.root;
	}

	public void init() {
		// TODO Auto-generated method stub
		super.onStart();
		dia = new ProgressDialog(getActivity());
		dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		mContent = getActivity();
		application = ((MWDApplication) mContent.getApplicationContext());
		visit_no = application.nums;
		if (visit_no.equals("2") || visit_no.equals("3")
				|| visit_no.equals("4")) {
			title_1.setVisibility(View.GONE);
			title_2.setVisibility(View.VISIBLE);
			if (visit_no.equals("4")) {
				title_2.setVisibility(View.VISIBLE);
				tv_still.setVisibility(View.VISIBLE);
			}
		}
		((MainActivity) getActivity()).setSubmitVisibily(add);
		((MainActivity) getActivity()).setSubmitVisibily(btn_save);
		add.setOnClickListener(this);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				// 无
				case R.id.r1:
					setViewVisibly(true);
					break;
				// 有
				case R.id.r2:
					setViewVisibly(false);
					break;
				}
			}
		});
		btn_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setNoData();
			}
		});
	}

	/**
	 * 无合并用药写入
	 */
	public void setNoData() {
		if (MWDUtils.isNetworkConnected(mContent)) {
			dia.show();
			ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
			localArrayList.add(new BasicNameValuePair("pid", application.pid));
			localArrayList.add(new BasicNameValuePair("nums", visit_no));
			localArrayList.add(new BasicNameValuePair("token", Constant.token));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.COMBINATION_DRUG_NODATA_URL, mHandler_del);
			this.rThread.start();
		}
	}

	// Handler处理-保存无数据
	Handler mHandler_del = new Handler() {

		public void handleMessage(Message paramAnonymousMessage) {
			dia.dismiss();
			try {
				String str = paramAnonymousMessage.obj.toString();
				JSONObject localJSONObject = new JSONObject(str);
				String status = localJSONObject.getString("status");
				if (status.equals("1")) {
					Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_SHORT)
							.show();
					fetchDataFromWebservice();
				} else {
					Toast.makeText(getActivity(), "操作失败", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
				Toast.makeText(getActivity(), "操作错误", Toast.LENGTH_SHORT)
						.show();
			}

		}
	};

	/**
	 * 获取历史记录
	 */
	private void fetchDataFromWebservice() {
		if (MWDUtils.isNetworkConnected(mContent)) {
			dia.show();
			ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
			localArrayList.add(new BasicNameValuePair("pid", application.pid));
			localArrayList.add(new BasicNameValuePair("nums", visit_no));
			localArrayList.add(new BasicNameValuePair("token", Constant.token));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.COMBINATION_DRUG_URL, mHandler);
			this.rThread.start();
		}
	}

	/**
	 * 设置有无，视图隐藏
	 * 
	 * @param isvisibily
	 */
	private void setViewVisibly(boolean isvisibily) {
		// 设置为无
		if (isvisibily) {
			btn_save.setVisibility(View.VISIBLE);
			((MainActivity) getActivity()).setSubmitVisibily(btn_save);
			add.setVisibility(View.GONE);
			lv.setVisibility(View.GONE);
			// 设置为有
		} else {
			btn_save.setVisibility(View.GONE);
			add.setVisibility(View.VISIBLE);
			((MainActivity) getActivity()).setSubmitVisibily(add);
			lv.setVisibility(View.VISIBLE);
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
						r2.setChecked(true);
						str = localJSONObject.getString("info");
						JSONArray ja = new JSONArray(str);
						CombinationDrugListAdapter adapter = new CombinationDrugListAdapter(
								mContent, ja, visit_no);
						lv.setAdapter(adapter);
					}
				} else {
					r1.setChecked(true);
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
			mContent.startActivity(new Intent(mContent,
					AddCombinationDrug.class));
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fetchDataFromWebservice();
	}

}
