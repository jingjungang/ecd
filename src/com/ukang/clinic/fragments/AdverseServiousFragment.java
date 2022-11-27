package com.ukang.clinic.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;
import com.ukang.clinic.utils.DateUtilities;
import com.ukang.clinic.utils.DialogUtils;
import com.ukang.clinicaltrial.view.Mdate;
import com.ukang.clinicaltrial.view.MobileTime;

/**
 * 不良事件 jjg 2016年4月23日 14:56:25
 */
public class AdverseServiousFragment extends Fragment implements
		OnClickListener {

	// 选择
	@ViewInject(R.id.radioGroup1)
	private RadioGroup rg;

	@ViewInject(R.id.r1)
	private RadioButton r1;

	@ViewInject(R.id.r3)
	private RadioButton r3;

	@ViewInject(R.id.Scorll_special)
	ScrollView Scorll_special;
	/** 严重不良事件的报告人（请书写清晰） */
	@ViewInject(R.id.f_17_1)
	private EditText f_17_1;
	/** 报告时间 */
	@ViewInject(R.id.f_17_2)
	private Mdate vdate_report;
	/** 严重不良事件名称（字迹清晰） */
	@ViewInject(R.id.f_17_3)
	private EditText f_17_3;
	/** SAE发生时间 */
	@ViewInject(R.id.f_17_4)
	private Mdate vdate_happen;
	@ViewInject(R.id.f_17_4_time)
	private MobileTime vdate_happen_time;
	/** SAE特点—阵发性 */
	@ViewInject(R.id.f_17_5a)
	private RadioButton f_17_5a;
	/** SAE特点—阵发性 */
	@ViewInject(R.id.f_17_5a_text)
	private EditText f_17_5a_text;
	/** SAE特点-持续性 */
	@ViewInject(R.id.f_17_5b)
	private RadioButton f_17_5b;
	/** SAE特点-其他 */
	@ViewInject(R.id.f_17_5c)
	private RadioButton f_17_5c;
	@ViewInject(R.id.f_17_5d)
	private EditText f_17_5d;
	/** SAE情况—导致住院 */
	@ViewInject(R.id.f_17_6a)
	private CheckBox f_17_6a;
	/** SAE情况-延迟住院时间 */
	@ViewInject(R.id.f_17_6b)
	private CheckBox f_17_6b;
	/** SAE情况-伤残 */
	@ViewInject(R.id.f_17_6c)
	private CheckBox f_17_6c;
	/** SAE情况-功能障碍 */
	@ViewInject(R.id.f_17_6d)
	private CheckBox f_17_6d;
	/** SAE情况-影响工作能力 */
	@ViewInject(R.id.f_17_6e)
	private CheckBox f_17_6e;
	/** SAE情况-导致先天畸形 */
	@ViewInject(R.id.f_17_6f)
	private CheckBox f_17_6f;
	/** SAE情况-危及生命或死亡 */
	@ViewInject(R.id.f_17_6g)
	private CheckBox f_17_6g;
	/** SAE情况-其他 */
	@ViewInject(R.id.f_17_6h)
	private CheckBox f_17_6h;
	/** SAE情况-其他描述 */
	@ViewInject(R.id.f_17_6i)
	private EditText f_17_6i;
	/** 纠正治疗-无 */
	@ViewInject(R.id.f_17_7a)
	private RadioButton f_17_7a;
	/** 纠正治疗-有 */
	@ViewInject(R.id.f_17_7b)
	private RadioButton f_17_7b;
	/** 对实验用药采取的措施-剂量不变 */
	@ViewInject(R.id.f_17_8a)
	private RadioButton f_17_8a;
	/** 对实验用药采取的措施-减少剂量 */
	@ViewInject(R.id.f_17_8b)
	private RadioButton f_17_8b;
	/** 对实验用药采取的措施-暂停用药 */
	@ViewInject(R.id.f_17_8c)
	private RadioButton f_17_8c;
	/** 对实验用药采取的措施-停止用药 */
	@ViewInject(R.id.f_17_8d)
	private RadioButton f_17_8d;
	/** 对实验用药采取的措施-实验用药已结束 */
	@ViewInject(R.id.f_17_8e)
	private RadioButton f_17_8e;
	/** 对实验用药采取的措施-其他 */
	@ViewInject(R.id.f_17_8f)
	private RadioButton f_17_8f;
	/** 对实验用药采取的措施-其他_说明 */
	@ViewInject(R.id.f_17_8f_text)
	private EditText f_17_8f_text;
	/** 与实验药物的关系-肯定相关 */
	@ViewInject(R.id.f_17_9a)
	private RadioButton f_17_9a;
	/** 与实验药物的关系-很可能相关 */
	@ViewInject(R.id.f_17_9b)
	private RadioButton f_17_9b;
	/** 与实验药物的关系-可能相关 */
	@ViewInject(R.id.f_17_9c)
	private RadioButton f_17_9c;
	/** 与实验药物的关系-可疑 */
	@ViewInject(R.id.f_17_9d)
	private RadioButton f_17_9d;
	/** 与实验药物的关系-不可能相关 */
	@ViewInject(R.id.f_17_9e)
	private RadioButton f_17_9e;
	/** SAE转归-症状消失 */
	@ViewInject(R.id.f_17_10a)
	private RadioButton f_17_10a;
	/** SAE转归-症状消失_表现 */
	@ViewInject(R.id.f_17_10a_text)
	private EditText f_17_10a_text;
	/** SAE转归-症状消失 */
	@ViewInject(R.id.f_17_10b)
	private RadioButton f_17_10b;
	/** SAE转归-症状消失 */
	@ViewInject(R.id.f_17_10c)
	private RadioButton f_17_10c;
	/** SAE转归-症状消失_表现 */
	@ViewInject(R.id.f_17_10c_text)
	private Mdate f_17_10c_text;
	/** 症状消失日期和时间（如果症状仍存在，请不要填写此栏） */
	@ViewInject(R.id.f_17_11)
	private Mdate vdate_disappear;
	@ViewInject(R.id.f_17_11_time)
	private MobileTime vdate_disappear_time;
	/** 该患者是否因为此严重不良事件退出试验？-否 */
	@ViewInject(R.id.f_17_12a)
	private RadioButton f_17_12a;
	/** 该患者是否因为此严重不良事件退出试验？-是 */
	@ViewInject(R.id.f_17_12b)
	private RadioButton f_17_12b;
	/** 破盲情况-未破盲 */
	@ViewInject(R.id.f_17_13a)
	private RadioButton f_17_13a;
	/** 破盲情况-已破盲 */
	@ViewInject(R.id.f_17_13b)
	private RadioButton f_17_13b;
	/** 破盲时间 */
	@ViewInject(R.id.f_17_13c)
	private Mdate vdate_unblinding;
	/** SAE过程描述（包括症状、体征、实验室检查等）及处理的详细情况 */
	@ViewInject(R.id.f_17_14)
	private EditText f_17_14;

	@ViewInject(R.id.submit)
	private Button submit;

	MWDApplication content;
	RequestThread thread;
	String id = "0";
	View root;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.frg_adverseservious,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		((MainActivity) getActivity()).setSubmitVisibily(submit);
		return this.root;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		content = (MWDApplication) getActivity().getApplication();
		init();
		DealData(1);
	}

	private void init() {
		// TODO Auto-generated method stub
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if (r1.isChecked()) {
					Scorll_special.setVisibility(View.VISIBLE);
				} else {
					Scorll_special.setVisibility(View.GONE);
				}
			}
		});
		submit.setOnClickListener(this);
		vdate_disappear.setText("");
	}

	/**
	 * 
	 * @param index
	 *            1查询2编辑
	 */
	private void DealData(int index) {
		DialogUtils.startDialog(getActivity());
		ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
		MWDApplication ma = (MWDApplication) getActivity().getApplication();
		localArrayList.add(new BasicNameValuePair("token", Constant.token));
		localArrayList.add(new BasicNameValuePair("nums", ma.nums));
		localArrayList.add(new BasicNameValuePair("pid", ma.pid));
		if (index == 1) {
			thread = new RequestThread(localArrayList, "http", "post",
					Constant.S_ADVERSE_EVENT_URL, mHandler);
		} else if (index == 2) {
			if (r3.isChecked()) {
				localArrayList.add(new BasicNameValuePair("status", "2"));
				this.thread = new RequestThread(localArrayList, "http",
						"post", Constant.ADVERSE_EVENT_NODATA_URL, mEidHandler);
			} else {
				SetParamer(localArrayList);
				thread = new RequestThread(localArrayList, "http", "post",
						Constant.S_ADVERSE_EVENT_EDIT_URL, mEidHandler);
			}
		}
		thread.start();
	}

	private void SetParamer(ArrayList<NameValuePair> localArrayList) {
		// TODO Auto-generated method stub
		localArrayList.add(new BasicNameValuePair("bgname", f_17_1.getText()
				.toString()));
		localArrayList.add(new BasicNameValuePair("bgtime", vdate_report
				.getText().toString()));
		localArrayList.add(new BasicNameValuePair("bgtitle", f_17_3.getText()
				.toString()));
		String temp = "";
		if (vdate_happen_time.getText().length() == 5) {
			temp = vdate_happen.getText().toString() + " "
					+ vdate_happen_time.getText() + ":00";
		} else {
			temp = vdate_happen.getText().toString() + " "
					+ vdate_happen_time.getText();
		}
		localArrayList.add(new BasicNameValuePair("saetime", temp));

		// SAE特点
		if (f_17_5a.isChecked()) {
			temp = "{\"no\":\"" + "1" + "\"" + "," + "\"num\":\""
					+ f_17_5a_text.getText() + "\"," + "\"text\":\"" + "\""
					+ "}";
		} else if (f_17_5b.isChecked()) {
			temp = "{\"no\":\"" + "2" + "\"" + "," + "\"num\":\"" + "\","
					+ "\"text\":\"" + "\"" + "}";
		} else if (f_17_5c.isChecked()) {
			temp = "{\"no\":\"" + "3" + "\"" + "," + "\"num\":\"" + "\","
					+ "\"text\":" + "\"" + f_17_5d.getText() + "\"}";
		}
		localArrayList.add(new BasicNameValuePair("saepoint", temp));
		// SAE情况
		temp = "{";
		List<String> li = new ArrayList<String>();
		if (f_17_6a.isChecked()) {
			li.add("1");
		}
		if (f_17_6b.isChecked()) {
			li.add("2");
		}
		if (f_17_6c.isChecked()) {
			li.add("3");
		}
		if (f_17_6d.isChecked()) {
			li.add("4");
		}
		if (f_17_6e.isChecked()) {
			li.add("5");
		}
		if (f_17_6f.isChecked()) {
			li.add("6");
		}
		if (f_17_6g.isChecked()) {
			li.add("7");
		}
		if (f_17_6h.isChecked()) {
			li.add("8");
		}
		String tpm = "";
		int size = li.size();
		for (int i = 0; i < size; i++) {
			tpm += li.get(i);
			if (i != size - 1) {
				tpm += ",";
			}
		}
		temp += "\"no\":\"" + tpm + "\",\"" + "text\":\"" + f_17_6i.getText()
				+ "\"";
		temp += "}";
		localArrayList.add(new BasicNameValuePair("saecase", temp));
		// 纠正治疗
		localArrayList.add(new BasicNameValuePair("correcttreat", f_17_7a
				.isChecked() ? "1" : "2"));
		// 对实验用药采取的措施
		temp = "{\"no\":\"";
		if (f_17_8a.isChecked()) {
			temp += "1\",\"text\":\"" + "\"";
		} else if (f_17_8b.isChecked()) {
			temp += "2\",\"text\":\"" + "\"";
		} else if (f_17_8c.isChecked()) {
			temp += "3\",\"text\":\"" + "\"";
		} else if (f_17_8d.isChecked()) {
			temp += "4\",\"text\":\"" + "\"";
		} else if (f_17_8e.isChecked()) {
			temp += "5\",\"text\":\"" + "\"";
		} else if (f_17_8f.isChecked()) {
			temp += "6\",\"text\":\"" + f_17_8f_text.getText() + "\"";
		}
		temp += "}";
		localArrayList.add(new BasicNameValuePair("testdrug", temp));
		// 与实验药物的关系
		if (f_17_8a.isChecked()) {
			temp = "1";
		} else if (f_17_9b.isChecked()) {
			temp = "2";
		} else if (f_17_9c.isChecked()) {
			temp = "3";
		} else if (f_17_9d.isChecked()) {
			temp = "4";
		} else if (f_17_9e.isChecked()) {
			temp = "5";
		}
		localArrayList.add(new BasicNameValuePair("testrelation", temp));
		// SAE转归
		temp = "{\"no\":\"";
		if (f_17_10a.isChecked()) {
			temp += "1\",\"text\":\"" + f_17_10a_text.getText()
					+ "\",\"death\":\"" + "\"";
		} else if (f_17_10b.isChecked()) {
			temp += "2\",\"text\":\"" + "\",\"death\":\"" + "\"";
		} else if (f_17_10c.isChecked()) {
			temp += "3\",\"text\":\"" + f_17_8f_text.getText()
					+ "\",\"death\":\"" + f_17_10c_text.getText() + "\"";
		}
		temp += "}";
		localArrayList.add(new BasicNameValuePair("saeoutcome", temp));
		// 症状消失日期和时间（如果症状仍存在，请不要填写此栏）
		if (vdate_disappear_time.getText().length() == 5) {
			temp = vdate_disappear.getText().toString() + " "
					+ vdate_disappear_time.getText() + ":00";
		} else {
			temp = vdate_happen.getText().toString() + " "
					+ vdate_disappear_time.getText();
		}
		localArrayList.add(new BasicNameValuePair("poinggone", temp));
		// 该患者是否因为此严重不良事件退出试验？
		localArrayList.add(new BasicNameValuePair("exittext", f_17_12a
				.isChecked() ? "1" : "2"));
		// 破盲情况
		String temp1 = "";
		if (f_17_13a.isChecked()) {
			temp1 = "{\"no\":\"" + "1" + "\"" + "," + "\"text\":\"" + "\""
					+ "}";
		} else if (f_17_13b.isChecked()) {
			temp1 = "{\"no\":\"" + "2" + "\"," + "\"text\":\""
					+ vdate_unblinding.getText() + "\"" + "}";
		}
		localArrayList.add(new BasicNameValuePair("unblinding", temp1));
		// SAE过程描述（包括症状、体征、实验室检查等）及处理的详细情况
		localArrayList.add(new BasicNameValuePair("saedesc", f_17_14.getText()
				.toString()));

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
						if (status == 1) {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"保存成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"保存失败", Toast.LENGTH_SHORT).show();
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
			if (msg.obj == null) {
				return;
			}
			String reuslt = msg.obj.toString();
			try {
				JSONObject js = new JSONObject(reuslt);
				int status = js.has("status") ? js.getInt("status") : 0;
				if (status != 1) {
					r3.setChecked(true);
				} else {
					r1.setChecked(true);
					js = js.getJSONObject("info");
					// 报告人
					String bgname = js.has("bgname") ? js.getString("bgname")
							: "";
					f_17_1.setText(bgname);
					// 报告时间
					String bgtime = "";
					try {
						bgtime = js.has("bgtime") ? js.getString("bgtime")
								.substring(0,
										js.getString("bgtime").indexOf(" "))
								: "";
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					vdate_report.setText(bgtime);
					// 严重不良事件名称
					String bgtitle = js.has("bgtitle") ? js
							.getString("bgtitle") : "";
					f_17_3.setText(bgtitle);
					// SAE发生时间
					String temp = DateUtilities.getStrTime(js
							.getString("saetime"));
					try {
						if (temp.indexOf(" ") == -1) {
							vdate_happen.setText(temp);
							vdate_happen_time.setText("00:00");
						} else {
							vdate_happen.setText(temp.substring(0,
									temp.indexOf(" ")));
							vdate_happen_time.setText(temp.substring(temp
									.indexOf(" ")));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// SAE特点
					JSONObject jsaepoint = new JSONObject();
					if (!js.getString("saepoint").equals("null")) {
						jsaepoint = js.getJSONObject("saepoint");
					}
					int saepoint = jsaepoint.has("no") ? jsaepoint.getInt("no")
							: 0;
					setSaepoint(saepoint);
					if (saepoint == 3) {
						String text = jsaepoint.has("text") ? jsaepoint
								.getString("text") : "";
						f_17_5d.setText(text);
					}
					if (saepoint == 1) {
						String text = jsaepoint.has("num") ? jsaepoint
								.getString("num") : "";
						f_17_5a_text.setText(text);
					}
					// SAE情况
					JSONObject jsaecase = js.has("saecase") ? js
							.getJSONObject("saecase") : new JSONObject();
					/*
					 * String t = jsaecase.has("text") ? jsaecase
					 * .getString("text") : ""; f_17_6d.setText(t);
					 */
					String n = jsaecase.has("no") ? jsaecase.getString("no")
							: "";
					if (!TextUtils.isEmpty(n)) {
						setSaecase(n, jsaecase);
					}
					// 纠正治疗
					int correcttreat = js.has("correcttreat") ? js
							.getInt("correcttreat") : 1;
					setCorrecttreat(correcttreat);
					// 对实验用药采取的措施
					JSONObject jtestdrug = js.has("testdrug") ? js
							.getJSONObject("testdrug") : null;
					int testdrug = jtestdrug.has("no") ? jtestdrug.getInt("no")
							: 1;
					setTestdrug(testdrug);
					if (testdrug == 6) {
						f_17_8f_text.setText(jtestdrug.has("text") ? jtestdrug
								.getString("text").toString() : "");
					}
					// 与实验药物的关系
					int testrelation = js.has("testrelation") ? js
							.getInt("testrelation") : 1;
					setTestrelation(testrelation);
					// SAE转归
					JSONObject jsaeoutcome = js.has("saeoutcome") ? js
							.getJSONObject("saeoutcome") : null;
					int saeoutcome = jsaeoutcome.has("no") ? jsaeoutcome
							.getInt("no") : 1;
					if (saeoutcome == 1) {
						setSaeoutcome(saeoutcome,
								jsaeoutcome.has("text") ? jsaeoutcome
										.getString("text").toString() : "");
					} else if (saeoutcome == 2) {
						setSaeoutcome(saeoutcome, "");
					} else {
						setSaeoutcome(saeoutcome,
								jsaeoutcome.has("death") ? jsaeoutcome
										.getString("death").toString() : "");
					}
					// 症状消失日期和时间（如果症状仍存在，请不要填写此栏）
					temp = js.getString("poinggone");
					try {
						if (temp.indexOf(" ") == -1) {
							vdate_disappear.setText(temp);
							vdate_disappear_time.setText("00:00");
						} else {
							vdate_disappear.setText(temp.substring(0,
									temp.indexOf(" ")));
							vdate_disappear_time.setText(temp.substring(temp
									.indexOf(" ")));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// 该患者是否因为此严重不良事件退出试验？
					int exittext = js.has("exittext") ? js.getInt("exittext")
							: 1;
					setExittext(exittext);
					// 破盲情况
					JSONObject junblinding = js.has("unblinding") ? js
							.getJSONObject("unblinding") : null;
					int unblinding = junblinding.has("no") ? junblinding
							.getInt("no") : 1;
					setUnblinding(unblinding);
					if (unblinding == 2) {
						String unblindingText = junblinding.has("text") ? junblinding
								.getString("text") : "";
						vdate_unblinding.setText(unblindingText);
					}
					// SAE过程描述（包括症状、体征、实验室检查等）及处理的详细情况
					String saedesc = js.has("saedesc") ? js
							.getString("saedesc") : "";
					f_17_14.setText(saedesc);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	/** 设置SAE特点选项 */
	private void setSaepoint(int i) {
		switch (i) {
		case 1:
			f_17_5a.setChecked(true);
			break;
		case 2:
			f_17_5b.setChecked(true);
			break;
		case 3:
			f_17_5c.setChecked(true);
			break;
		}
	}

	/** 设置SAE情况选项 -多选 */
	private void setSaecase(String n, JSONObject jsaecase) {
		if (n.contains("1")) {
			f_17_6a.setChecked(true);
		}
		if (n.contains("2")) {
			f_17_6b.setChecked(true);
		}
		if (n.contains("3")) {
			f_17_6c.setChecked(true);
		}
		if (n.contains("4")) {
			f_17_6d.setChecked(true);
		}
		if (n.contains("5")) {
			f_17_6e.setChecked(true);
		}
		if (n.contains("6")) {
			f_17_6f.setChecked(true);
		}
		if (n.contains("7")) {
			f_17_6g.setChecked(true);
		}
		if (n.contains("8")) {
			f_17_6h.setChecked(true);
			try {
				f_17_6i.setText(jsaecase.has("text") ? jsaecase
						.getString("text") : "");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** 设置纠正治疗选项 */
	private void setCorrecttreat(int i) {
		switch (i) {
		case 1:
			f_17_7a.setChecked(true);
			break;
		case 2:
			f_17_7b.setChecked(true);
			break;
		}
	}

	/** 设置对实验用药采取的措施选项 */
	private void setTestdrug(int i) {
		switch (i) {
		case 1:
			f_17_8a.setChecked(true);
			break;
		case 2:
			f_17_8b.setChecked(true);
			break;
		case 3:
			f_17_8c.setChecked(true);
			break;
		case 4:
			f_17_8d.setChecked(true);
			break;
		case 5:
			f_17_8e.setChecked(true);
			break;
		case 6:
			f_17_8f.setChecked(true);
			break;
		}
	}

	/** 设置与实验药物的关系选项 */
	private void setTestrelation(int i) {
		switch (i) {
		case 1:
			f_17_9a.setChecked(true);
			break;
		case 2:
			f_17_9b.setChecked(true);
			break;
		case 3:
			f_17_9c.setChecked(true);
			break;
		case 4:
			f_17_9d.setChecked(true);
			break;
		case 5:
			f_17_9e.setChecked(true);
			break;
		}
	}

	/** 设置SAE转归选项 */
	private void setSaeoutcome(int i, String text) {
		switch (i) {
		case 1:
			f_17_10a.setChecked(true);
			f_17_10a_text.setText(text);
			break;
		case 2:
			f_17_10b.setChecked(true);
			break;
		case 3:
			f_17_10c.setChecked(true);
			f_17_10c_text.setText(text);
			break;
		}
	}

	/** 设置该患者是否因为此严重不良事件退出试验选项 */
	private void setExittext(int i) {
		switch (i) {
		case 1:
			f_17_12a.setChecked(true);
			break;
		case 2:
			f_17_12b.setChecked(true);
			break;
		}
	}

	/** 设置破盲情况选项 */
	private void setUnblinding(int i) {
		switch (i) {
		case 1:
			f_17_13a.setChecked(true);
			break;
		case 2:
			f_17_13b.setChecked(true);
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		DealData(2);
	}
}
