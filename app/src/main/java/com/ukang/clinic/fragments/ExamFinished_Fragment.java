package com.ukang.clinic.fragments;

/**
 * 试验完成情况
 * zzd
 * 2016年6月2日 14:37:25
 */

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
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.XThread;
import com.ukang.clinic.utils.DialogUtils;
import com.ukang.clinicaltrial.view.Mdate;

import org.json.JSONException;
import org.json.JSONObject;

public class ExamFinished_Fragment extends Fragment {

	/** 中心号 */
	@ViewInject(R.id.frg_25_1)
	private EditText frg_25_1;
	/** 药物编号 */
	@ViewInject(R.id.frg_25_2)
	private EditText frg_25_2;
	/** 姓名拼音缩写 */
	@ViewInject(R.id.frg_25_3)
	private EditText frg_25_3;
	/** 首次用银杏内酯注射液日期 */
	@ViewInject(R.id.frg_25_4)
	private Mdate frg_25_4;
	/** 末次用银杏内酯注射液日期 */
	@ViewInject(R.id.frg_25_5)
	private Mdate frg_25_5;
	/** 入组开始使用阿司匹林肠溶片日期 */
	@ViewInject(R.id.frg_25_6)
	private Mdate frg_25_6;
	/** 是否停止使用阿司匹林肠溶片-是 */
	@ViewInject(R.id.frg_25_7a)
	private RadioButton frg_25_7a;
	/** 是否停止使用阿司匹林肠溶片-否 */
	@ViewInject(R.id.frg_25_7b)
	private RadioButton frg_25_7b;
	/** 停止使用阿司匹林肠溶片时间 */
	@ViewInject(R.id.frg_25_7c)
	private Mdate frg_25_7c;
	/** 患者是否完成了临床研究？-是 */
	@ViewInject(R.id.frg_25_8a)
	private RadioButton frg_25_8a;
	/** 患者是否完成了临床研究？-否 */
	@ViewInject(R.id.frg_25_8b)
	private RadioButton frg_25_8b;
	/** 患者中止研究日期 */
	@ViewInject(R.id.frg_25_9)
	private Mdate frg_25_9;
	/** 首先提出中止研究的是-患者 */
	@ViewInject(R.id.frg_25_10a)
	private RadioButton frg_25_10a;
	/** 首先提出中止研究的是-研究者 */
	@ViewInject(R.id.frg_25_10b)
	private RadioButton frg_25_10b;
	/** 首先提出中止研究的是-申办者 */
	@ViewInject(R.id.frg_25_10c)
	private RadioButton frg_25_10c;
	/** 首先提出中止研究的是-其他 */
	@ViewInject(R.id.frg_25_10d)
	private RadioButton frg_25_10d;
	/** 首先提出中止研究-说明 */
	@ViewInject(R.id.frg_25_10e)
	private EditText frg_25_10e;
	/** 中止研究的主要原因是-不良事件 */
	@ViewInject(R.id.frg_25_11a)
	private RadioButton frg_25_11a;
	/** 中止研究的主要原因是-患者自行退出 */
	@ViewInject(R.id.frg_25_11b)
	private RadioButton frg_25_11b;
	/** 中止研究的主要原因是-违背研究方案 */
	@ViewInject(R.id.frg_25_11c)
	private RadioButton frg_25_11c;
	/** 中止研究的主要原因是-失访 */
	@ViewInject(R.id.frg_25_11d)
	private RadioButton frg_25_11d;
	/** 中止研究的主要原因是-其他 */
	@ViewInject(R.id.frg_25_11e)
	private RadioButton frg_25_11e;
	/** 中止研究的主要原因是-说明 */
	@ViewInject(R.id.frg_25_11f)
	private EditText frg_25_11f;

	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;
	@ViewInject(R.id.radioGroup_1)
	private RadioGroup radioGroup_1;
	@ViewInject(R.id.radioGroup_2)
	private RadioGroup radioGroup_2;
	@ViewInject(R.id.radioGroup_3)
	private RadioGroup radioGroup_3;
	@ViewInject(R.id.unfinished)
	private LinearLayout unfinished;
	@ViewInject(R.id.ll_reson)
	private LinearLayout ll_reson;

	@ViewInject(R.id.submit)
	private Button submit;

	MWDApplication content;
	View root;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_test_complete,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		return this.root;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		content = (MWDApplication) getActivity().getApplication();
		init();
		onLoad(1);
		((MainActivity)getActivity()).setSubmitVisibily(submit);
		super.onStart();
	}

	private void init() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int n) {
				// TODO Auto-generated method stub
				if(n==R.id.frg_25_7a){
					frg_25_7c.setVisibility(View.VISIBLE);
				}else if(n==R.id.frg_25_7b){
					frg_25_7c.setVisibility(View.GONE);
				}
			}
		});
		radioGroup_1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int n) {
				// TODO Auto-generated method stub
				if(n==R.id.frg_25_8a){
					unfinished.setVisibility(View.GONE);
				}else if(n==R.id.frg_25_8b){
					unfinished.setVisibility(View.VISIBLE);
				}
			}
		});
		radioGroup_2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int n) {
				// TODO Auto-generated method stub
				if(n==R.id.frg_25_10d){
					frg_25_10e.setVisibility(View.VISIBLE);
				}else {
					frg_25_10e.setVisibility(View.GONE);
				}
			}
		});
		radioGroup_3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int n) {
				// TODO Auto-generated method stub
				if(n==R.id.frg_25_11b||n==R.id.frg_25_11c||n==R.id.frg_25_11e){
					frg_25_11f.setVisibility(View.VISIBLE);
					ll_reson.setVisibility(View.VISIBLE);
				}else {
					frg_25_11f.setVisibility(View.GONE);
					ll_reson.setVisibility(View.GONE);
				}
			}
		});
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLoad(2);
			}
		});
	}
	private void onLoad(int no) {
		DialogUtils.startDialog(getActivity());
		RequestParams params = new RequestParams();
		params.addHeader("Cookie", Constant.sessionId);
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("nums", content.nums);
		params.addBodyParameter("pid", ((MWDApplication) getActivity()
				.getApplication()).pid);
		XThread thread = null;
		if (no == 1) {
			thread = new XThread(getActivity(), 0, params,
					Constant.PERFORMANCE_URL, mHandler);
		} else if (no == 2) {
			params.addBodyParameter("stime", frg_25_4.getText());
			params.addBodyParameter("mtime", frg_25_5.getText());
			params.addBodyParameter("rtime", frg_25_6.getText());
			// 是否用了阿司匹林肠溶片
			if (frg_25_7a.isChecked()) {
				params.addBodyParameter("yes", "1");
				params.addBodyParameter("ztime", frg_25_7c.getText());
			} else {
				params.addBodyParameter("yes", "2");
				params.addBodyParameter("ztime", "");
			}
			// 是否完成实验
			if (frg_25_8a.isChecked()) {
				params.addBodyParameter("complete", "1");
				params.addBodyParameter("htime", frg_25_9.getText());
				params.addBodyParameter("research", "1");
				params.addBodyParameter("researchother", "");
				params.addBodyParameter("reason", "1");
				params.addBodyParameter("reasonzother", "");
				params.addBodyParameter("reasonwother", "");
				params.addBodyParameter("reasonsother", "");
			} else {
				params.addBodyParameter("complete", "2");
				params.addBodyParameter("htime", frg_25_9.getText());

				switch (radioGroup_2.getCheckedRadioButtonId()) {
				case R.id.frg_25_10a:
					params.addBodyParameter("research", "1");
					params.addBodyParameter("researchother", "");
					break;
				case R.id.frg_25_10b:
					params.addBodyParameter("research", "2");
					params.addBodyParameter("researchother", "");
					break;
				case R.id.frg_25_10c:
					params.addBodyParameter("research", "3");
					params.addBodyParameter("researchother", "");
					break;
				case R.id.frg_25_10d:
					params.addBodyParameter("research", "4");
					params.addBodyParameter("researchother", frg_25_10e
							.getText().toString());
					break;
				}
				// 中止研究的主要原因
				switch (radioGroup_3.getCheckedRadioButtonId()) {
				case R.id.frg_25_11a:
					params.addBodyParameter("reason", "1");
					params.addBodyParameter("reasonzother", "");
					params.addBodyParameter("reasonwother", "");
					params.addBodyParameter("reasonsother", "");
					break;
				case R.id.frg_25_11b:
					params.addBodyParameter("reason", "2");
					params.addBodyParameter("reasonzother", frg_25_11f
							.getText().toString());
					params.addBodyParameter("reasonwother", "");
					params.addBodyParameter("reasonsother", "");
					break;
				case R.id.frg_25_11c:
					params.addBodyParameter("reason", "3");
					params.addBodyParameter("reasonzother", "");
					params.addBodyParameter("reasonwother", frg_25_11f
							.getText().toString());
					params.addBodyParameter("reasonsother", "");
					break;
				case R.id.frg_25_11d:
					params.addBodyParameter("reason", "4");
					params.addBodyParameter("reasonzother", "");
					params.addBodyParameter("reasonwother", "");
					params.addBodyParameter("reasonsother", "");
					break;
				case R.id.frg_25_11e:
					params.addBodyParameter("reason", "5");
					params.addBodyParameter("reasonzother", "");
					params.addBodyParameter("reasonwother", "");
					params.addBodyParameter("reasonsother", frg_25_11f
							.getText().toString());
					break;
				}
			}
			thread = new XThread(getActivity(), 0, params,
					Constant.PERFORMANCE_EDIT_URL, mEidHandler);
		}
		thread.start();
	}

	Handler mEidHandler = new Handler() {
		public void handleMessage(Message msg) {
			DialogUtils.stopDialog();
			if (null != msg.obj) {
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
				} else {

				}
			} else {

			}
		};
	};
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			DialogUtils.stopDialog();
			String result = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(result);
				int status = json.getInt("status");
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
					Toast.makeText(content, "无数据", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					JSONObject js = json.getJSONObject("info");
					String centernum = js.has("centernum") ? js
							.getString("centernum") : "";
					frg_25_1.setText(centernum);
					String number = js.has("number") ? js.getString("number")
							: "";
					frg_25_2.setText(number);
					String abbreviation = js.has("abbreviation") ? js
							.getString("abbreviation") : "";
					frg_25_3.setText(abbreviation);
					String stime = js.has("stime") ? js.getString("stime") : "";
					frg_25_4.setText(stime);
					String mtime = js.has("mtime") ? js.getString("mtime") : "";
					frg_25_5.setText(mtime);
					String rtime = js.has("rtime") ? js.getString("rtime") : "";
					frg_25_6.setText(rtime);
					int yes = js.has("yes") ? js.getInt("yes") : 2;
					if (yes == 1) {
						frg_25_7a.setChecked(true);
						String ztime = js.has("ztime") ? js.getString("ztime")
								: "";
						frg_25_7c.setText(ztime);
					} else {
						frg_25_7b.setChecked(true);
					}
					int complete = js.has("complete") ? js.getInt("complete")
							: 1;
					if (complete == 1) {
						frg_25_8a.setChecked(true);
						frg_25_8b.setChecked(false);
					} else {
						frg_25_8a.setChecked(false);
						frg_25_8b.setChecked(true);
						String htime = js.has("htime") ? js.getString("htime")
								: "";
						frg_25_9.setText(htime);
						int research = js.has("research") ? js
								.getInt("research") : 1;
						setResearch(research);
						if (research == 4) {
							String researchother = js.has("researchother") ? js
									.getString("researchother") : "";
							frg_25_10e.setText(researchother);
						}
						int reason = js.has("reason") ? js.getInt("reason") : 1;
						setReason(reason);
						switch (reason) {
						case 2:
							String reasonzother = js.has("reasonzother") ? js
									.getString("reasonzother") : "";
							frg_25_11f.setText(reasonzother);
							break;
						case 3:
							String reasonwother = js.has("reasonwother") ? js
									.getString("reasonwother") : "";
							frg_25_11f.setText(reasonwother);
							break;
						case 5:
							String reasonsother = js.has("reasonsother") ? js
									.getString("reasonsother") : "";
							frg_25_11f.setText(reasonsother);
							break;
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

	/** 设置首先提出中止研究选项 */
	private void setResearch(int i) {
		switch (i) {
		case 1:
			frg_25_10a.setChecked(true);
			break;
		case 2:
			frg_25_10b.setChecked(true);
			break;
		case 3:
			frg_25_10c.setChecked(true);
			break;
		case 4:
			frg_25_10d.setChecked(true);
			break;
		}
	}

	/** 设置中止研究的主要原因选项 */
	private void setReason(int i) {
		switch (i) {
		case 1:
			frg_25_11a.setChecked(true);
			break;
		case 2:
			frg_25_11b.setChecked(true);
			break;
		case 3:
			frg_25_11c.setChecked(true);
			break;
		case 4:
			frg_25_11d.setChecked(true);
			break;
		case 5:
			frg_25_11e.setChecked(true);
			break;
		}
	}
}
