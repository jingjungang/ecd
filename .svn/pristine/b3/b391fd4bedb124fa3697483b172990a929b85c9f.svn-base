package com.ukang.clinic.fragments;

/**
 * 入组审核
 * jjg
 * 2016年4月23日 10:55:43
 */

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;

public class JoinGroupCheckFragment extends Fragment implements OnClickListener {

	View root;
	// 中心号
	// @ViewInject(R.id.center_no)
	// private EditText center_no;
	// 姓名拼音缩写
	// @ViewInject(R.id.rname)
	// private EditText rname;
	/**
	 * 符合缺血性脑卒中诊断标准
	 */
	@ViewInject(R.id.radioGroup_1)
	private RadioGroup radioGroup_1;
	@ViewInject(R.id.c0)
	private RadioButton c0;
	@ViewInject(R.id.c1)
	private RadioButton c1;
	/**
	 * 3分＜ 神经功能缺损评分（NIHSS）≤25分；
	 */
	@ViewInject(R.id.radioGroup_2)
	private RadioGroup radioGroup_2;
	@ViewInject(R.id.c2)
	private RadioButton c2;
	@ViewInject(R.id.c3)
	private RadioButton c3;
	/**
	 * MRA显示动脉粥样硬化所致颈动脉、大脑前动脉、大脑中动脉、大脑后动脉、椎-基底动脉狭窄
	 */
	@ViewInject(R.id.radioGroup_3)
	private RadioGroup radioGroup_3;
	@ViewInject(R.id.c4)
	private RadioButton c4;
	@ViewInject(R.id.c5)
	private RadioButton c5;
	/**
	 * 发病72h内
	 */
	@ViewInject(R.id.radioGroup_4)
	private RadioGroup radioGroup_4;
	@ViewInject(R.id.c6)
	private RadioButton c6;
	@ViewInject(R.id.c7)
	private RadioButton c7;
	/**
	 * 首次发病，或既往发病但未留相关严重后遗症
	 */
	@ViewInject(R.id.radioGroup_5)
	private RadioGroup radioGroup_5;
	@ViewInject(R.id.c8)
	private RadioButton c8;
	@ViewInject(R.id.c9)
	private RadioButton c9;
	/**
	 * 自愿参加本次研究，并签署知情同意书者
	 */
	@ViewInject(R.id.radioGroup_6)
	private RadioGroup radioGroup_6;
	@ViewInject(R.id.c10)
	private RadioButton c10;
	@ViewInject(R.id.c11)
	private RadioButton c11;
	/**
	 * 短暂性脑缺血发作
	 */
	@ViewInject(R.id.radioGroup_7)
	private RadioGroup radioGroup_7;
	@ViewInject(R.id.c12)
	private RadioButton c12;
	@ViewInject(R.id.c13)
	private RadioButton c13;
	/**
	 * 已进行或计划进行溶栓治疗患者
	 */
	@ViewInject(R.id.radioGroup_8)
	private RadioGroup radioGroup_8;
	@ViewInject(R.id.c14)
	private RadioButton c14;
	@ViewInject(R.id.c15)
	private RadioButton c15;
	/**
	 * 影像学检查有脑出血患者
	 */
	@ViewInject(R.id.radioGroup_9)
	private RadioGroup radioGroup_9;
	@ViewInject(R.id.c16)
	private RadioButton c16;
	@ViewInject(R.id.c17)
	private RadioButton c17;
	/**
	 * 脑梗死后脑出血以及脑动脉炎患者
	 */
	@ViewInject(R.id.radioGroup_10)
	private RadioGroup radioGroup_10;
	@ViewInject(R.id.c18)
	private RadioButton c18;
	@ViewInject(R.id.c19)
	private RadioButton c19;
	/**
	 * 由脑肿瘤、脑外伤、脑寄生虫病、风湿性心脏病、冠心病及其他心脏病合并房颤而引起的脑栓塞者
	 */
	@ViewInject(R.id.radioGroup_11)
	private RadioGroup radioGroup_11;
	@ViewInject(R.id.c20)
	private RadioButton c20;
	@ViewInject(R.id.c21)
	private RadioButton c21;
	/**
	 * ALT、AST≥正常值上限的2.5倍，Cr≥正常值上限1.5倍
	 */
	@ViewInject(R.id.radioGroup_12)
	private RadioGroup radioGroup_12;
	@ViewInject(R.id.c22)
	private RadioButton c22;
	@ViewInject(R.id.c23)
	private RadioButton c23;
	/**
	 * 已使用双抗治疗
	 */
	@ViewInject(R.id.radioGroup_13)
	private RadioGroup radioGroup_13;
	@ViewInject(R.id.c24)
	private RadioButton c24;
	@ViewInject(R.id.c25)
	private RadioButton c25;
	/**
	 * 有出血倾向者，3个月内发生过严重出血者，PLT小于正常值范围下限或APTT大于对照值3秒以上者
	 */
	@ViewInject(R.id.radioGroup_14)
	private RadioGroup radioGroup_14;
	@ViewInject(R.id.c26)
	private RadioButton c26;
	@ViewInject(R.id.c27)
	private RadioButton c27;
	/**
	 * 已知对银杏类药物、乙醇、甘油过敏或过敏体质者
	 */
	@ViewInject(R.id.radioGroup_15)
	private RadioGroup radioGroup_15;
	@ViewInject(R.id.c28)
	private RadioButton c28;
	@ViewInject(R.id.c29)
	private RadioButton c29;
	/**
	 * 妊娠、哺乳者和有妊娠计划者
	 */
	@ViewInject(R.id.radioGroup_16)
	private RadioGroup radioGroup_16;
	@ViewInject(R.id.c30)
	private RadioButton c30;
	@ViewInject(R.id.c31)
	private RadioButton c31;
	/**
	 * 近1个月参加其他临床研究者
	 */
	@ViewInject(R.id.radioGroup_17)
	private RadioGroup radioGroup_17;
	@ViewInject(R.id.c32)
	private RadioButton c32;
	@ViewInject(R.id.c33)
	private RadioButton c33;
	/**
	 * 研究者认为不适宜参加该临床研究者（精神神志异常等）
	 */
	@ViewInject(R.id.radioGroup_18)
	private RadioGroup radioGroup_18;
	@ViewInject(R.id.c34)
	private RadioButton c34;
	@ViewInject(R.id.c35)
	private RadioButton c35;
	/**
	 * 患者是否通过筛选
	 */
	@ViewInject(R.id.radioGroup_19)
	private RadioGroup radioGroup_19;
	@ViewInject(R.id.c36)
	private RadioButton c36;
	@ViewInject(R.id.c37)
	private RadioButton c37;
	/**
	 * 药物编号
	 */
	@ViewInject(R.id.ll_no)
	private LinearLayout ll_no;

	@ViewInject(R.id.no)
	private EditText edt_no;
	// **********************分割线**************************
	Context mContext;
	private RequestThread rThread;
	MWDApplication application;
	public ProgressDialog dia;
	/**
	 * 提交按钮
	 */
	@ViewInject(R.id.submit)
	private Button submit;
	/**
	 * 文字描述 设置红色
	 */
	// -------------------第一部分-------------------
	@ViewInject(R.id.t1)
	private TextView t1;
	@ViewInject(R.id.t2)
	private TextView t2;
	@ViewInject(R.id.t3)
	private TextView t3;
	@ViewInject(R.id.t4)
	private TextView t4;
	@ViewInject(R.id.t5)
	private TextView t5;
	@ViewInject(R.id.t6)
	private TextView t6;
	// ---------------------第二部分------------------
	@ViewInject(R.id.tt1)
	private TextView tt1;
	@ViewInject(R.id.tt2)
	private TextView tt2;
	@ViewInject(R.id.tt3)
	private TextView tt3;
	@ViewInject(R.id.tt4)
	private TextView tt4;
	@ViewInject(R.id.tt5)
	private TextView tt5;
	@ViewInject(R.id.tt6)
	private TextView tt6;
	@ViewInject(R.id.tt7)
	private TextView tt7;
	@ViewInject(R.id.tt8)
	private TextView tt8;
	@ViewInject(R.id.tt9)
	private TextView tt9;
	@ViewInject(R.id.tt10)
	private TextView tt10;
	@ViewInject(R.id.tt11)
	private TextView tt11;
	@ViewInject(R.id.tt12)
	private TextView tt12;

	boolean isShowDialog = true;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(
				R.layout.layout_into_group_auditing, paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		mContext = getActivity();
		application = ((MWDApplication) mContext.getApplicationContext());
		((MainActivity) getActivity()).setSubmitVisibily(submit);
		dia = new ProgressDialog(getActivity());
		dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		submit.setOnClickListener(this);
		fetchDataFromWebservice(0);
		init();
		return this.root;

	}

	private void init() {
		// TODO Auto-generated method stub
		radioGroup_19.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case R.id.c36:
					ll_no.setVisibility(View.VISIBLE);
					break;
				case R.id.c37:
					ll_no.setVisibility(View.INVISIBLE);
					break;
				}
			}
		});
		SetRadioGroupListener(t1, radioGroup_1, c0, c1, 2);
		SetRadioGroupListener(t2, radioGroup_2, c2, c3, 2);
		SetRadioGroupListener(t3, radioGroup_3, c4, c5, 2);
		SetRadioGroupListener(t4, radioGroup_4, c6, c7, 2);
		SetRadioGroupListener(t5, radioGroup_5, c8, c9, 2);
		SetRadioGroupListener(t6, radioGroup_6, c10, c11, 2);

		SetRadioGroupListener(tt1, radioGroup_7, c12, c13, 1);
		SetRadioGroupListener(tt2, radioGroup_8, c14, c15, 1);
		SetRadioGroupListener(tt3, radioGroup_9, c16, c17, 1);
		SetRadioGroupListener(tt4, radioGroup_10, c18, c19, 1);
		SetRadioGroupListener(tt5, radioGroup_11, c20, c21, 1);
		SetRadioGroupListener(tt6, radioGroup_12, c22, c23, 1);
		SetRadioGroupListener(tt7, radioGroup_13, c24, c25, 1);
		SetRadioGroupListener(tt8, radioGroup_14, c26, c27, 1);
		SetRadioGroupListener(tt9, radioGroup_15, c28, c29, 1);
		SetRadioGroupListener(tt10, radioGroup_16, c30, c31, 1);
		SetRadioGroupListener(tt11, radioGroup_17, c32, c33, 1);
		SetRadioGroupListener(tt12, radioGroup_18, c34, c35, 1);
	}

	/**
	 * 获取历史记录
	 * 
	 * @param index
	 *            0查看 1编辑
	 */
	private void fetchDataFromWebservice(int index) {
		if (MWDUtils.isNetworkConnected(mContext)) {
			dia.show();
			ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
			localArrayList.add(new BasicNameValuePair("pid", application.pid));
			localArrayList
					.add(new BasicNameValuePair("nums", application.nums));
			localArrayList.add(new BasicNameValuePair("token", Constant.token));
			if (index == 0) {
				this.rThread = new RequestThread(localArrayList, "http",
						"post", Constant.REVIEW_URL, mHandler);
			} else {
				localArrayList.add(new BasicNameValuePair("status", c36
						.isChecked() ? "1" : "0"));
				JSONObject json = new JSONObject();
				JSONObject json1 = new JSONObject();
				String jsonone = "", jsontwo = "";
				try {
					json.put("review1", getStatus(c0, c1));
					json.put("review2", getStatus(c2, c3));
					json.put("review3", getStatus(c4, c5));
					json.put("review4", getStatus(c6, c7));
					json.put("review5", getStatus(c8, c9));
					json.put("review6", getStatus(c10, c11));
					jsonone = json.toString();

					json1.put("reviewt1", getStatus(c12, c13));
					json1.put("reviewt2", getStatus(c14, c15));
					json1.put("reviewt3", getStatus(c16, c17));
					json1.put("reviewt4", getStatus(c18, c19));
					json1.put("reviewt5", getStatus(c20, c21));
					json1.put("reviewt6", getStatus(c22, c23));
					json1.put("reviewt7", getStatus(c24, c25));
					json1.put("reviewt8", getStatus(c26, c27));
					json1.put("reviewt9", getStatus(c28, c29));
					json1.put("reviewt10", getStatus(c30, c31));
					json1.put("reviewt11", getStatus(c32, c33));
					json1.put("reviewt12", getStatus(c34, c35));
					jsontwo = json1.toString();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				localArrayList.add(new BasicNameValuePair("medicationno",
						edt_no.getText().toString()));
				localArrayList.add(new BasicNameValuePair("jsonone", jsonone));
				localArrayList.add(new BasicNameValuePair("jsontwo", jsontwo));
				this.rThread = new RequestThread(localArrayList, "http",
						"post", Constant.REVIEW_ADD_URL, eHandler);

			}

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
						localJSONObject = new JSONObject(str);
						isShowDialog = false; // 绑数据时不弹出对话框
						BindDataToView(localJSONObject);
						isShowDialog = true;
					}
				} else {

				}
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
		}
	};

	Handler eHandler = new Handler() {

		public void handleMessage(Message msg) {
			try {
				dia.dismiss();
				if (msg.obj != null) {
					String str = msg.obj.toString();
					JSONObject localJSONObject = new JSONObject(str);
					if (localJSONObject.getString("status").toString()
							.equals("1")) {
						Toast.makeText(getActivity(), "保存成功",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT)
								.show();
					}
				}
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
		}
	};

	/**
	 * 数据展示
	 */
	protected void BindDataToView(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			// String no = jo.getString("centernum");
			// String name = jo.getString("name");

			String review1 = jo.getString("review1");// 1是0否
			String review2 = jo.getString("review2");
			String review3 = jo.getString("review3");
			String review4 = jo.getString("review4");
			String review5 = jo.getString("review5");
			String review6 = jo.getString("review6");

			String reviewt1 = jo.getString("reviewt1");
			String reviewt2 = jo.getString("reviewt2");
			String reviewt3 = jo.getString("reviewt3");
			String reviewt4 = jo.getString("reviewt4");
			String reviewt5 = jo.getString("reviewt5");
			String reviewt6 = jo.getString("reviewt6");
			String reviewt7 = jo.getString("reviewt7");
			String reviewt8 = jo.getString("reviewt8");
			String reviewt9 = jo.getString("reviewt9");
			String reviewt10 = jo.getString("reviewt10");
			String reviewt11 = jo.getString("reviewt11");
			String reviewt12 = jo.getString("reviewt12");

			// center_no.setText(no);
			// rname.setText(name);
			String status = jo.getString("status");

			SetRadioGroupChecked(review1, radioGroup_1, c0, c1);
			SetRadioGroupChecked(review2, radioGroup_2, c2, c3);
			SetRadioGroupChecked(review3, radioGroup_3, c4, c5);
			SetRadioGroupChecked(review4, radioGroup_4, c6, c7);
			SetRadioGroupChecked(review5, radioGroup_5, c8, c9);
			SetRadioGroupChecked(review6, radioGroup_6, c10, c11);

			SetRadioGroupChecked(reviewt1, radioGroup_7, c12, c13);
			SetRadioGroupChecked(reviewt2, radioGroup_8, c14, c15);
			SetRadioGroupChecked(reviewt3, radioGroup_9, c16, c17);
			SetRadioGroupChecked(reviewt4, radioGroup_10, c18, c19);
			SetRadioGroupChecked(reviewt5, radioGroup_11, c20, c21);
			SetRadioGroupChecked(reviewt6, radioGroup_12, c22, c23);
			SetRadioGroupChecked(reviewt7, radioGroup_13, c24, c25);
			SetRadioGroupChecked(reviewt8, radioGroup_14, c26, c27);
			SetRadioGroupChecked(reviewt9, radioGroup_15, c28, c29);
			SetRadioGroupChecked(reviewt10, radioGroup_16, c30, c31);
			SetRadioGroupChecked(reviewt11, radioGroup_17, c32, c33);
			SetRadioGroupChecked(reviewt12, radioGroup_18, c34, c35);

			SetRadioGroupChecked(status, radioGroup_19, c36, c37);
			edt_no.setText(jo.getString("medicationno"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
	 */
	@SuppressWarnings("unused")
	private void SetRadioGroupChecked(String index, RadioGroup g,
			RadioButton b1, RadioButton b2) {
		if (index.equals("1")) {
			g.check(b1.getId());
		} else if (index.equals("0")) {
			g.check(b2.getId());
		}
	}

	private int getStatus(RadioButton b1,RadioButton b2 ) {
		int temp = 3;
		if (!b1.isChecked() && !b2.isChecked()) {
			temp = 3;
		} else if (b1.isChecked()) {
			temp = 1;
		}else if(b2.isChecked()){
			temp = 0;
		}
		return temp;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit:
			if (!c36.isChecked() && !c37.isChecked()) {
				Toast.makeText(getActivity(), "患者是否通过筛选为必填项!",
						Toast.LENGTH_SHORT).show();
			} else if (c36.isChecked()
					&& TextUtils.isEmpty(edt_no.getText().toString())) {
				Toast.makeText(getActivity(), "药物编号不能为空!", Toast.LENGTH_SHORT)
						.show();
			} else {
				fetchDataFromWebservice(1);
			}
			break;
		}
	}

	/**
	 * 
	 * @param tv设置红色字
	 * @param g
	 *            RadioGroup
	 * @param b1
	 * @param b2
	 * @param index
	 *            1是红色并提醒，2否红色并提醒
	 * @param isset
	 */
	@SuppressWarnings("unused")
	private void SetRadioGroupListener(final TextView tv, RadioGroup g,
			final RadioButton b1, RadioButton b2, final int index) {
		g.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if (arg1 == b1.getId()) {
					if (index == 1) {
						tv.setTextColor(Color.RED);
						setDialog("确定选项为是吗？");
					} else {
						tv.setTextColor(Color.BLACK);
					}
				} else {
					if (index == 2) {
						setDialog("确定选项为否吗？");
						tv.setTextColor(Color.RED);
					} else {
						tv.setTextColor(Color.BLACK);
					}
				}
			}
		});
	}

	/**
	 * 简易提醒框
	 * 
	 * @param str
	 */
	private void setDialog(String str) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(str).setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		if (isShowDialog) {
			alert.show();
		}
	}
}
