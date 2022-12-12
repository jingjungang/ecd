package com.ukang.clinic.activity.add;

/**
 * 新增合并用药
 * jjg
 * 2016年6月13日 15:10:17
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.thread.RequestThread;
import com.ukang.clinic.utils.DateUtilities;
import com.ukang.clinicaltrial.view.Mdate;
import com.ukang.clinicaltrial.view.MobileTime;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCombinationDrug extends Activity implements OnClickListener {

	private RequestThread rThread;
	public ProgressDialog dia;

	@ViewInject(R.id.tv1)
	private EditText tv1;

	@ViewInject(R.id.tv2)
	private EditText tv2;

	@ViewInject(R.id.tv3)
	private EditText tv3;

	@ViewInject(R.id.date1)
	private Mdate date1;

	@ViewInject(R.id.date2)
	private Mdate date2;

	@ViewInject(R.id.submit)
	private Button submit;

	@ViewInject(R.id.date11)
	private MobileTime date11;

	@ViewInject(R.id.date22)
	private MobileTime date22;

	EditText tv_3_1;
	CheckBox tv_4;
	MWDApplication ma;
	String Visit = "1";
	String record_id = "0";

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		ma = (MWDApplication) getApplication();
		Visit = ma.nums;
		if (Visit.equals("2") || Visit.equals("3") || Visit.equals("4")) {
			setContentView(R.layout.activity_drug_combination234);
			setView(0);
		} else {
			setContentView(R.layout.activity_drug_combination);
			setView(1);
		}
		ViewUtils.inject(this);
		submit.setOnClickListener(this);
		// 编辑
		Intent i = getIntent();
		if (null != i && i.getExtras() != null) {
			if (Visit.equals("2") || Visit.equals("3") || Visit.equals("4")) {
				if (Visit.equals("4")
						&& i.getExtras().getString("stillinuse").equals("on")) {
					tv_4.setChecked(true);
					date2.setVisibility(View.GONE);
					date22.setVisibility(View.GONE);
				}
				tv_3_1.setText(i.getExtras().getString("usereason"));
			} else if (i.getExtras().getString("stillinuse").equals("on")) {
				tv_4.setChecked(true);
				date2.setVisibility(View.GONE);
				date22.setVisibility(View.GONE);
			}
			tv1.setText(i.getExtras().getString("diseasename"));
			tv2.setText(i.getExtras().getString("drugtherapy"));
			tv3.setText(i.getExtras().getString("dosage"));
			date1.setText(i.getExtras().getString("startdate"));
			record_id = i.getExtras().getString("id");
			date11.setText(i.getExtras().getString("date11"));
			if (!TextUtils.isEmpty(i.getExtras().getString("date22"))) {
				date22.setText(i.getExtras().getString("date22"));
				date2.setText(i.getExtras().getString("enddate"));
			}
		}
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

	/**
	 * 页面绑定
	 * 
	 * @param num
	 */
	private void setView(int num) {
		if (num == 0) {
			if (Visit.equals("4")) {
				((TextView) findViewById(R.id.tv_still))
						.setVisibility(View.VISIBLE);
				tv_4 = (CheckBox) findViewById(R.id.tv4);
				tv_4.setVisibility(View.VISIBLE);
			}
			tv_3_1 = (EditText) findViewById(R.id.tv_3_1);
		} else {
			tv_4 = (CheckBox) findViewById(R.id.tv4);
		}
		((TextView) findViewById(R.id.tv_title)).setText("新增");
		ImageButton iv_back = (ImageButton) findViewById(R.id.iv_back);
		((ImageButton) findViewById(R.id.iv_back)).setVisibility(View.VISIBLE);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		if (tv_4 != null) {
			tv_4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (tv_4.isChecked()) {
						date2.setVisibility(View.GONE);
						date22.setVisibility(View.GONE);
					} else {
						date2.setVisibility(View.VISIBLE);
						date22.setVisibility(View.VISIBLE);
						if (date2.getText().equals("")) {
							date2.setText(DateUtilities.getSystemDate());
							date22.setText(DateUtilities.getSystemTime());
						}
					}
				}
			});
		}
	}

	/**
	 * 数据请求 1fetch 2edit
	 */
	private void setDataByPost() {
		dia = new ProgressDialog(AddCombinationDrug.this);
		dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		dia.show();
		ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
		localArrayList.add(new BasicNameValuePair("token", Constant.token));
		localArrayList.add(new BasicNameValuePair("nums", ma.nums));
		localArrayList.add(new BasicNameValuePair("pid", ma.pid));
		localArrayList.add(new BasicNameValuePair("id", record_id));

		localArrayList.add(new BasicNameValuePair("diseasename", tv1.getText()
				.toString()));
		localArrayList.add(new BasicNameValuePair("drugtherapy", tv2.getText()
				.toString()));
		localArrayList.add(new BasicNameValuePair("dosage", tv3.getText()
				.toString()));
		if (!Visit.equals("1")) {
			localArrayList.add(new BasicNameValuePair("usereason", tv_3_1
					.getText().toString()));
		}
		localArrayList.add(new BasicNameValuePair("startdate", date1.getText()
				.toString() + " " + date11.getText().toString()));
		localArrayList.add(new BasicNameValuePair("enddate", date2.getText()
				.toString() + " " + date22.getText().toString()));
		if (Visit.equals("1") || Visit.equals("4")) {
			if (tv_4.isChecked()) {
				localArrayList.add(new BasicNameValuePair("enddate", ""));
				localArrayList.add(new BasicNameValuePair("stillinuse", "on"));
			}
		}
		this.rThread = new RequestThread(localArrayList, "http", "post",
				Constant.COMBINATION_DRUG_EDIT_URL, mEidHandler);
		this.rThread.start();
	}

	Handler mEidHandler = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();
			if (null != msg.obj) {
				String result = msg.obj.toString();
				if (null != result) {
					JSONObject json;
					try {
						json = new JSONObject(result);
						int status = json.getInt("status");
						switch (status) {
						case 0:
							Toast.makeText(AddCombinationDrug.this, "保存失败",
									Toast.LENGTH_SHORT).show();
							break;
						case 1:
							Toast.makeText(AddCombinationDrug.this, "保存成功",
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		setDataByPost();
	}
}
