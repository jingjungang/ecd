package com.ukang.clinic.fragments;

/**
 * 访视首页
 * jjg 2016年4月20日 14:14:32
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.ukang.clinic.utils.DateUtilities;
import com.ukang.clinic.utils.DialogUtil;
import com.ukang.clinicaltrial.view.Mdate;

import org.json.JSONException;
import org.json.JSONObject;

public class FirstPageFragment extends Fragment implements OnClickListener {

	View root;
	private XThread XThread;
	public ProgressDialog dia;
	MWDApplication ma;

	private Mdate vdate;
	private Button submit;

	@ViewInject(R.id.ll_commit)
	private LinearLayout ll_commit;
	@ViewInject(R.id.ll_status)
	private LinearLayout ll_status;

	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;
	@ViewInject(R.id.Y)
	private RadioButton btn_Y;
	@ViewInject(R.id.N)
	private RadioButton btn_N;

	@ViewInject(R.id.status)
	private TextView status;

	@ViewInject(R.id.tv_tips)
	TextView tv_tips;

	@ViewInject(R.id.reson)
	TextView tv_reson;

	MainActivity mainactivity;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_first_visit,
				paramViewGroup, false);
		ViewUtils.inject(this, root);
		ma = (MWDApplication) getActivity().getApplication();
		init();
		updateDataByPost(1);
		return this.root;
	}

	private void init() {
		submit = (Button) root.findViewById(R.id.submit);
		submit.setOnClickListener(this);
		vdate = (Mdate) root.findViewById(R.id.vdate);
		vdate.setText("");
		mainactivity = (MainActivity) getActivity();
		mainactivity.setSubmitVisibily(submit);
		status.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (arg0.toString().equals("待提交")
						|| arg0.toString().equals("审核未通过")) {
					btn_Y.setChecked(true);
					btn_N.setChecked(false);
				} else {
					btn_Y.setChecked(false);
					btn_N.setChecked(true);
				}
			}
		});
		tv_reson.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogUtil.Dialog(getActivity(), tv_reson.getText().toString());
			}
		});
	}

	/**
	 * 数据请求 1请求，2编辑
	 */
	private void updateDataByPost(int i) {
		dia = new ProgressDialog(getActivity());
		dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		RequestParams params = new RequestParams();
		params.addHeader("Cookie", Constant.sessionId);
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("nums", ma.nums);
		params.addBodyParameter("id", ma.pid);
		if (i == 2) {
			if (TextUtils.isEmpty(vdate.getText().toString().trim())) {
				Toast.makeText(getActivity(), "请输入访视时间", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			try {
				params.addBodyParameter("ischecked", btn_Y.isChecked() ? "1"
						: "0");
				params.addBodyParameter("js", (new JSONObject().put("btime",
						vdate.getText())).toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.XThread = new XThread(getActivity(), 1, params,
					Constant.VISIT_FIRSTPAGE_URL_EDIT, mHandler_edit);
		} else {
			this.XThread = new XThread(getActivity(), 1, params,
					Constant.VISIT_FIRSTPAGE_URL, mHandler);
		}
		dia.show();
		this.XThread.start();
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();
			String reuslt = msg.obj.toString();
			try {
				JSONObject localJSONObject = new JSONObject(reuslt);
				// status -3无数据
				String status = localJSONObject.getString("status").toString();
				if (status.equals("1")) {
					JSONObject js = localJSONObject.getJSONObject("info");
					setLocalData(js);
				} else if (status.equals("-2")) {
					setLocalData(null);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	Handler mHandler_edit = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();
			String reuslt = msg.obj.toString();
			try {
				JSONObject localJSONObject = new JSONObject(reuslt);
				// status -3无数据
				String status = localJSONObject.getString("status").toString();
				if (status.equals("1")) {
					Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT)
							.show();
					String number = localJSONObject.getString("number");
					String ischecked = localJSONObject.getString("ischecked");
					String isbreak = localJSONObject.getString("isbreak");
					ma.visit_count_finish = Integer.valueOf(number);
					ma.visit_count_ischecked = Integer.valueOf(ischecked);
					ma.visit_count_isbreak = Integer.valueOf(isbreak);
					((MainActivity) getActivity()).jugdeVisitCheck(number,
							ischecked, isbreak);
					ll_commit.setVisibility(View.VISIBLE);
					ll_status.setVisibility(View.VISIBLE);
					updateDataByPost(1);
				} else if (status.equals("-3")) {
					Toast.makeText(getActivity(), "请选择提交或修改访视时间",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 显示历史记录
	 * 
	 * @param localJSONObject
	 */
	protected void setLocalData(JSONObject jo) {
		try {
			if (jo != null && !jo.getString("btime").equals("")) {
				submit.setText("提交");
				mainactivity.setVisibilyMenv(false);
				vdate.setText(jo.getString("btime").toString());
				// 若是计划内，需要审核
				if (ma.visit_count_finish < 7) {
					mainactivity.setSubmitVisibily(submit);
					// 设置显示提交
					ll_status.setVisibility(View.VISIBLE);
					// 状态 0未提交 1待审核 2审核通过 3审核未通过
					int temp = Integer.valueOf(jo.getString("ischecked"));
					if (temp == 0) {
						status.setText("待提交");
						ll_commit.setVisibility(View.VISIBLE);
					} else if (temp == 1) {
						status.setText("待审核");
						ll_commit.setVisibility(View.GONE);
					} else if (temp == 2) {
						status.setText("审核通过");
						ll_commit.setVisibility(View.GONE);
					} else if (temp == 3) {
						status.setText("审核未通过");
						ll_commit.setVisibility(View.GONE);
						tv_reson.setText("("
								+ jo.getString("resaon").toString() + ")");
						// 未通过建议不中止时允许提交
						if (jo.getString("isbreak").toString().equals("2")) {
							submit.setVisibility(View.VISIBLE);
						}
					}
					String review = jo.has("review") ? jo.getString("review")
							: "2";
					// 入组审核 : 1无数据 2有数据
					if (ma.visit_count_finish == 1 && review.equals("1")) {
						submit.setVisibility(View.GONE);
						tv_tips.setVisibility(View.VISIBLE);
					} else {
						tv_tips.setVisibility(View.GONE);
					}
				} else {
					ll_commit.setVisibility(View.GONE);
					ll_status.setVisibility(View.GONE);
					submit.setVisibility(View.GONE);
				}
				mainactivity.clickable_8 = true;
			} else {
				submit.setText("保存");
				mainactivity.setVisibilyMenv(true);
				mainactivity.clickable_8 = false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit:
			String time_temp = vdate.getText();
			int n = DateUtilities.nDaysBetweenTwoDate(time_temp,
					DateUtilities.getSystemDate());
			if (n > 0) {
				Toast.makeText(getActivity(), "访视时间不能在今天之前", Toast.LENGTH_SHORT)
						.show();
			} else {
				updateDataByPost(2);
			}
			break;
		}
	}
}
