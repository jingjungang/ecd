package com.ukang.clinic.activity.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.thread.XThread;
import com.ukang.clinic.utils.DialogUtils;
import com.ukang.clinicaltrial.view.Mdate;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 不良事件 jjg 2016年4月23日 14:56:25
 */
public class AdverseEventAddActivity extends Activity implements
		OnClickListener {

	// 选择
	@ViewInject(R.id.radioGroup1)
	private RadioGroup rg;

	@ViewInject(R.id.r1)
	private RadioButton r1;

	@ViewInject(R.id.r3)
	private RadioButton r3;

	@ViewInject(R.id.Scorll_common)
	ScrollView Scorll_common;
	/** 不良事件名称 */
	@ViewInject(R.id.frg_17_1)
	private EditText frg_17_1;
	/** 不良事件描述 */
	@ViewInject(R.id.frg_17_2)
	private EditText frg_17_2;
	/** 发生时间 */
	@ViewInject(R.id.frg_17_3)
	private Mdate vdate_start;
	/** 结束时间 */
	@ViewInject(R.id.frg_17_4)
	private Mdate vdate_end;

	/** 严重程度-轻 */
	@ViewInject(R.id.frg_17_xa)
	private RadioButton frg_17_xa;
	/** 严重程度-中 */
	@ViewInject(R.id.frg_17_xb)
	private RadioButton frg_17_xb;
	/** 严重程度-重 */
	@ViewInject(R.id.frg_17_xc)
	private RadioButton frg_17_xc;

	/** 是否采取措施-否 */
	@ViewInject(R.id.frg_17_5a)
	private RadioButton frg_17_5a;
	/** 是否采取措施-是 */
	@ViewInject(R.id.frg_17_5b)
	private RadioButton frg_17_5b;
	/** 对研究药物的影响-剂量不变 */
	@ViewInject(R.id.frg_17_6a)
	private RadioButton frg_17_6a;
	/** 对研究药物的影响-增加剂量 */
	@ViewInject(R.id.frg_17_6b)
	private RadioButton frg_17_6b;
	/** 对研究药物的影响-减少剂量 */
	@ViewInject(R.id.frg_17_6c)
	private RadioButton frg_17_6c;
	/** 对研究药物的影响-暂停用药 */
	@ViewInject(R.id.frg_17_6d)
	private RadioButton frg_17_6d;
	/** 对研究药物的影响-永久停药 */
	@ViewInject(R.id.frg_17_6e)
	private RadioButton frg_17_6e;
	/** 对研究药物的影响-正常停药 */
	@ViewInject(R.id.frg_17_6f)
	private RadioButton frg_17_6f;
	/** 与研究药物的关系-肯定有关 */
	@ViewInject(R.id.frg_17_7a)
	private RadioButton frg_17_7a;
	/** 与研究药物的关系-很可能有关 */
	@ViewInject(R.id.frg_17_7b)
	private RadioButton frg_17_7b;
	/** 与研究药物的关系-可能有关 */
	@ViewInject(R.id.frg_17_7c)
	private RadioButton frg_17_7c;
	/** 与研究药物的关系-可能无关 */
	@ViewInject(R.id.frg_17_7d)
	private RadioButton frg_17_7d;
	/** 与研究药物的关系-无关 */
	@ViewInject(R.id.frg_17_7e)
	private RadioButton frg_17_7e;
	/** 是否符合GCP严重不良事件定义-否 */
	@ViewInject(R.id.frg_17_8a)
	private RadioButton frg_17_8a;
	/** 是否符合GCP严重不良事件定义-是 */
	@ViewInject(R.id.frg_17_8b)
	private RadioButton frg_17_8b;
	/** 所发生不良事件的结局-消失，无后遗症 */
	@ViewInject(R.id.frg_17_9a)
	private RadioButton frg_17_9a;
	/** 所发生不良事件的结局-消失，有后遗症 */
	@ViewInject(R.id.frg_17_9b)
	private RadioButton frg_17_9b;
	/** 所发生不良事件的结局-缓解 */
	@ViewInject(R.id.frg_17_9c)
	private RadioButton frg_17_9c;
	/** 所发生不良事件的结局-持续 */
	@ViewInject(R.id.frg_17_9d)
	private RadioButton frg_17_9d;
	/** 所发生不良事件的结局-死亡 */
	@ViewInject(R.id.frg_17_9e)
	private RadioButton frg_17_9e;
	/** 所发生不良事件的结局-不详 */
	@ViewInject(R.id.frg_17_9f)
	private RadioButton frg_17_9f;
	/** 受试者是否因此事件而退出研究？-否 */
	@ViewInject(R.id.frg_17_10a)
	private RadioButton frg_17_10a;
	/** 受试者是否因此事件而退出研究？-是 */
	@ViewInject(R.id.frg_17_10b)
	private RadioButton frg_17_10b;

	@ViewInject(R.id.submit)
	private Button submit;

	MWDApplication content;
	XThread thread;
	String id = "0";

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_adverse_event);
		ViewUtils.inject(this);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		content = (MWDApplication) getApplication();
		init();
		setSubmitVisibily(submit);
	}

	public void setSubmitVisibily(Button submit) {
		MWDApplication ma = ((MWDApplication) getApplicationContext());
		// 1.选择访视阶段比当前阶段小，隐藏submit
		if (Integer.valueOf(ma.nums) < ma.visit_count_finish) {
			submit.setVisibility(View.GONE);
		}
		// 2.选择访视阶段等于当前阶段
		else if (Integer.valueOf(ma.nums) == ma.visit_count_finish) {
			if (ma.visit_count_ischecked == 1 // 待审核
					|| ma.visit_count_ischecked == 2 // 审核通过
					// 审核未通过1建议中止 3中止
					|| (ma.visit_count_ischecked == 3 && (ma.visit_count_isbreak == 1 || ma.visit_count_isbreak == 3))) {
				submit.setVisibility(View.GONE);
			} else {
				submit.setVisibility(View.VISIBLE);
			}
		}
		// 3.选择访视阶段大于当前阶段
		else if (Integer.valueOf(ma.nums) > ma.visit_count_finish) {

		}
	}

	private void init() {
		// TODO Auto-generated method stub
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if (r1.isChecked()) {
					Scorll_common.setVisibility(View.VISIBLE);
				} else {
					Scorll_common.setVisibility(View.GONE);
				}
			}
		});
		submit.setOnClickListener(this);
		Intent i = getIntent();
		if (null != i && i.getExtras() != null) {
			try {
				id = i.getExtras().getString("id");
				frg_17_1.setText(i.getExtras().getString("name"));
				frg_17_2.setText(i.getExtras().getString("miaoshu"));
				vdate_start.setText(i.getExtras().getString("startdate"));
				vdate_end.setText(i.getExtras().getString("enddate"));
				setServious(Integer.valueOf(i.getExtras().getString("yanz")));
				setCuoShi(Integer.valueOf(i.getExtras().getString("cuoshi")));
				setYingXiang(Integer.valueOf(i.getExtras().getString(
						"yingxiang")));
				setGuanXi(Integer.valueOf(i.getExtras().getString("guanxi")));
				setGCP(Integer.valueOf(i.getExtras().getString("gcp")));
				setJieJu(Integer.valueOf(i.getExtras().getString("jieju")));
				setTuiChu(Integer.valueOf(i.getExtras().getString("tuichu")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// token 用户token值
	// id 记录唯一ID 新增为0
	// pid 患者ID
	// nums 访视次数
	// name 不良事件名称
	// miaoshu 不良事件描述
	// startdate 发生时间
	// enddate 结束时间
	// yanz 严重程度
	// cuoshi 是否采取措施
	// yingxiang 对研究药物的影响
	// guanxi 与研究药物的关系
	// gcp 是否符合GCP
	// jieju 所发生不良事件的结局
	// tuichu 受试者是否因此事件而退出研究？
	private void onLoad() {
		DialogUtils.startDialog(AdverseEventAddActivity.this);
		RequestParams params = new RequestParams();

		params.addHeader("Cookie", Constant.sessionId);
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("nums",
				((MWDApplication) getApplication()).nums);
		params.addBodyParameter("pid", ((MWDApplication) getApplication()).pid);
		if (r1.isChecked()) { // 不良事件
			params.addBodyParameter("id", id);

			params.addBodyParameter("name", frg_17_1.getText().toString());
			params.addBodyParameter("miaoshu", frg_17_2.getText().toString());
			params.addBodyParameter("startdate", vdate_start.getText()
					.toString());
			params.addBodyParameter("enddate", vdate_end.getText().toString());
			// 严重程度
			if (frg_17_xa.isChecked()) {
				params.addBodyParameter("yanz", "0");
			} else if (frg_17_xb.isChecked()) {
				params.addBodyParameter("yanz", "1");
			} else {
				params.addBodyParameter("yanz", "2");
			}
			// 有无措施
			if (frg_17_5a.isChecked()) {
				params.addBodyParameter("cuoshi", "0");
			} else if (frg_17_5b.isChecked()) {
				params.addBodyParameter("cuoshi", "1");
			}
			// 有无措施
			if (frg_17_6a.isChecked()) {
				params.addBodyParameter("yingxiang", "0");
			} else if (frg_17_6b.isChecked()) {
				params.addBodyParameter("yingxiang", "1");
			} else if (frg_17_6c.isChecked()) {
				params.addBodyParameter("yingxiang", "2");
			} else if (frg_17_6d.isChecked()) {
				params.addBodyParameter("yingxiang", "3");
			} else if (frg_17_6e.isChecked()) {
				params.addBodyParameter("yingxiang", "4");
			} else if (frg_17_6f.isChecked()) {
				params.addBodyParameter("yingxiang", "5");
			}
			// 与研究药物的关系
			if (frg_17_7a.isChecked()) {
				params.addBodyParameter("guanxi", "0");
			} else if (frg_17_7b.isChecked()) {
				params.addBodyParameter("guanxi", "1");
			} else if (frg_17_7c.isChecked()) {
				params.addBodyParameter("guanxi", "2");
			} else if (frg_17_7d.isChecked()) {
				params.addBodyParameter("guanxi", "3");
			} else if (frg_17_7e.isChecked()) {
				params.addBodyParameter("guanxi", "4");
			}
			// 符合GCP
			if (frg_17_8a.isChecked()) {
				params.addBodyParameter("gcp", "0");
			} else if (frg_17_8b.isChecked()) {
				params.addBodyParameter("gcp", "1");
			}
			// 所发生不良事件的结局
			if (frg_17_9a.isChecked()) {
				params.addBodyParameter("jieju", "0");
			} else if (frg_17_9b.isChecked()) {
				params.addBodyParameter("jieju", "1");
			} else if (frg_17_9c.isChecked()) {
				params.addBodyParameter("jieju", "2");
			} else if (frg_17_9d.isChecked()) {
				params.addBodyParameter("jieju", "3");
			} else if (frg_17_9e.isChecked()) {
				params.addBodyParameter("jieju", "4");
			} else if (frg_17_9f.isChecked()) {
				params.addBodyParameter("jieju", "5");
			}
			// 受试者是否因此事件而退出研究？
			if (frg_17_10a.isChecked()) {
				params.addBodyParameter("tuichu", "0");
			} else if (frg_17_10b.isChecked()) {
				params.addBodyParameter("tuichu", "1");
			}
			thread = new XThread(AdverseEventAddActivity.this, 0, params,
					Constant.ADVERSE_EVENT_EDIT_URL, mEidHandler);
		} else if (r3.isChecked()) {// 无
			params.addBodyParameter("status", "1");
			thread = new XThread(AdverseEventAddActivity.this, 0, params,
					Constant.ADVERSE_EVENT_NO_URL, mEidHandler);
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
							Toast.makeText(getApplicationContext(), "保存失败",
									Toast.LENGTH_SHORT).show();
							break;
						case 1:
							Toast.makeText(getApplicationContext(), "保存成功",
									Toast.LENGTH_SHORT).show();
							finish();
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
			// DialogUtils.stopDialog();
			String reuslt = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(reuslt);
				JSONObject js = json.getJSONObject("info");
				int status = js.has("status") ? js.getInt("status") : 0;
				switch (status) {
				case 0:
					r3.setChecked(true);
					break;
				case 1:
					r1.setChecked(true);
					JSONObject jname = js.has("name") ? js
							.getJSONObject("name") : null;
					String name = jname.has("1") ? jname.getString("1") : "";
					frg_17_1.setText(name);
					JSONObject jmiaoshu = js.has("miaoshu") ? js
							.getJSONObject("miaoshu") : null;
					String miaoshu = jmiaoshu.has("1") ? jmiaoshu
							.getString("1") : "";
					frg_17_2.setText(miaoshu);
					JSONObject jstartdate = js.has("startdate") ? js
							.getJSONObject("startdate") : null;
					String startdate = jstartdate.has("1") ? jstartdate
							.getString("1") : "";
					vdate_start.setText(startdate);
					JSONObject jenddate = js.has("enddate") ? js
							.getJSONObject("enddate") : null;
					String enddate = jenddate.has("1") ? jenddate
							.getString("1") : "";
					vdate_end.setText(enddate);
					JSONObject jcuoshi = js.has("cuoshi") ? js
							.getJSONObject("cuoshi") : null;
					int cuoshi = jcuoshi.has("1") ? jcuoshi.getInt("1") : 0;
					setCuoShi(cuoshi);
					JSONObject jyingxiang = js.has("yingxiang") ? js
							.getJSONObject("yingxiang") : null;
					int yingxiang = jyingxiang.has("1") ? jyingxiang
							.getInt("1") : 0;
					setYingXiang(yingxiang);
					JSONObject jguanxi = js.has("guanxi") ? js
							.getJSONObject("guanxi") : null;
					int guanxi = jguanxi.has("1") ? jguanxi.getInt("1") : 0;
					setGuanXi(guanxi);
					JSONObject jgcp = js.has("gcp") ? js.getJSONObject("gcp")
							: null;
					int gcp = jgcp.has("1") ? jgcp.getInt("1") : 0;
					setGCP(gcp);
					JSONObject jjieju = js.has("jieju") ? js
							.getJSONObject("jieju") : null;
					int jieju = jjieju.has("1") ? jjieju.getInt("1") : 0;
					setJieJu(jieju);
					JSONObject jtuichu = js.has("tuichu") ? js
							.getJSONObject("tuichu") : null;
					int tuichu = jtuichu.has("1") ? jtuichu.getInt("1") : 0;
					setTuiChu(tuichu);
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	/** 设置严重成都 */
	private void setServious(int i) {
		switch (i) {
		case 0:
			frg_17_xa.setChecked(true);
			break;
		case 1:
			frg_17_xb.setChecked(true);
		case 2:
			frg_17_xc.setChecked(true);
			break;
		}
	}

	/** 设置是否采取措施选项 */
	private void setCuoShi(int i) {
		switch (i) {
		case 0:
			frg_17_5a.setChecked(true);
			break;
		case 1:
			frg_17_5b.setChecked(true);
			break;
		}
	}

	/** 设置对研究药物的影响选项 */
	private void setYingXiang(int i) {
		switch (i) {
		case 0:
			frg_17_6a.setChecked(true);
			break;
		case 1:
			frg_17_6b.setChecked(true);
			break;
		case 2:
			frg_17_6c.setChecked(true);
			break;
		case 3:
			frg_17_6d.setChecked(true);
			break;
		case 4:
			frg_17_6e.setChecked(true);
			break;
		case 5:
			frg_17_6f.setChecked(true);
			break;
		}
	}

	/** 设置与研究药物的关系选项 */
	private void setGuanXi(int i) {
		switch (i) {
		case 0:
			frg_17_7a.setChecked(true);
			break;
		case 1:
			frg_17_7b.setChecked(true);
			break;
		case 2:
			frg_17_7c.setChecked(true);
			break;
		case 3:
			frg_17_7d.setChecked(true);
			break;
		case 4:
			frg_17_7e.setChecked(true);
			break;
		}
	}

	/** 设置是否符合GCP选项 */
	private void setGCP(int i) {
		switch (i) {
		case 0:
			frg_17_8a.setChecked(true);
			break;
		case 1:
			frg_17_8b.setChecked(true);
			break;
		}
	}

	/** 设置所发生不良事件的结局选项 */
	private void setJieJu(int i) {
		switch (i) {
		case 0:
			frg_17_9a.setChecked(true);
			break;
		case 1:
			frg_17_9b.setChecked(true);
			break;
		case 2:
			frg_17_9c.setChecked(true);
			break;
		case 3:
			frg_17_9d.setChecked(true);
			break;
		case 4:
			frg_17_9e.setChecked(true);
			break;
		case 5:
			frg_17_9f.setChecked(true);
			break;
		}
	}

	/** 设置退出研究选项 */
	private void setTuiChu(int i) {
		switch (i) {
		case 0:
			frg_17_10a.setChecked(true);
			break;
		case 1:
			frg_17_10b.setChecked(true);
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		onLoad();
	}
}
