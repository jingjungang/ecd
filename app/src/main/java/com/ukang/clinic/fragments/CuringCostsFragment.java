package com.ukang.clinic.fragments;

/**
 * 治疗成本
 * jjg
 * 2016年4月21日 11:57:20
 */

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.thread.RequestThread;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CuringCostsFragment extends Fragment implements OnClickListener {

	View root;
	// 治疗花费
	@ViewInject(R.id.frg_21_1)
	private EditText frg_21_1;
	// 生活费、交通费
	@ViewInject(R.id.frg_21_2)
	private EditText frg_21_2;
	// 陪护费
	@ViewInject(R.id.frg_21_3)
	private EditText frg_21_3;
	// 平均月薪
	@ViewInject(R.id.frg_21_4)
	private EditText frg_21_4;
	// 月薪
	@ViewInject(R.id.frg_21_5)
	private EditText frg_21_5;
	// 出院后
	@ViewInject(R.id.frg_21_6)
	private EditText frg_21_6;
	// 是否花钱请陪护
	@ViewInject(R.id.radioGroup_1)
	private RadioGroup radioGroup_1;
	@ViewInject(R.id.r0)
	private RadioButton c0;
	@ViewInject(R.id.r1)
	private RadioButton c1;
	// 是否家人陪护
	@ViewInject(R.id.radioGroup_2)
	private RadioGroup radioGroup_2;
	@ViewInject(R.id.r2)
	private RadioButton c2;
	@ViewInject(R.id.r3)
	private RadioButton c3;
	// 患病前是否有工作
	@ViewInject(R.id.radioGroup_3)
	private RadioGroup radioGroup_3;
	@ViewInject(R.id.r4)
	private RadioButton c4;
	@ViewInject(R.id.r5)
	private RadioButton c5;
	@ViewInject(R.id.submit)
	private Button submit;
	// **********************分割线**************************
	Context mContext;
	private RequestThread rThread;
	MWDApplication application;
	public ProgressDialog dia;

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_cure_costs,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		((MainActivity)getActivity()).setSubmitVisibily(submit);
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
		submit.setOnClickListener(this);
		fetchDataFromWebservice(0);
	}

	/**
	 * 获取历史记录
	 */
	private void fetchDataFromWebservice(int index) {
		if (MWDUtils.isNetworkConnected(mContext)) {
			dia.show();
			ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
			localArrayList.add(new BasicNameValuePair("pid", application.pid));
			localArrayList
					.add(new BasicNameValuePair("nums", application.nums));
			localArrayList.add(new BasicNameValuePair("token", Constant.token));
			if (index == 0) {// 查看
				this.rThread = new RequestThread(localArrayList, "http",
						"post", Constant.COST_URL, mHandler);
			} else {
				// hospitalization 住院所有相关治疗花费 1
				// living 住院期间生活费及交通费 1
				// escort 是否花钱请陪护 1否 2是
				// escorts 陪护总费用 元
				// familys 是否家人陪护 1否 2是
				// family 陪护家人平均月薪
				// works 患病前是否有工作 1否 2是
				// work 月薪
				// extramural 出院后所有相关治疗性药物、预防复发药物及康复理疗花费
				localArrayList.add(new BasicNameValuePair("hospitalization",
						frg_21_1.getText().toString()));
				localArrayList.add(new BasicNameValuePair("living", frg_21_2
						.getText().toString()));
				if (c0.isChecked()) {
					localArrayList.add(new BasicNameValuePair("escort", "1"));
					localArrayList.add(new BasicNameValuePair("escorts", ""));
				} else {
					localArrayList.add(new BasicNameValuePair("escort", "2"));
					localArrayList.add(new BasicNameValuePair("escorts",
							frg_21_3.getText().toString()));
				}
				if (c2.isChecked()) {
					localArrayList.add(new BasicNameValuePair("familys", "1"));
					localArrayList.add(new BasicNameValuePair("family", ""));
				} else {
					localArrayList.add(new BasicNameValuePair("familys", "2"));
					localArrayList.add(new BasicNameValuePair("family",
							frg_21_4.getText().toString()));
				}
				if (c4.isChecked()) {
					localArrayList.add(new BasicNameValuePair("works", "1"));
					localArrayList.add(new BasicNameValuePair("work", ""));
				} else {
					localArrayList.add(new BasicNameValuePair("works", "2"));
					localArrayList.add(new BasicNameValuePair("work",
							frg_21_5.getText().toString()));
				}
				this.rThread = new RequestThread(localArrayList, "http",
						"post", Constant.COST_ADD_URL, add_mHandler);
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
						BindDataToView(localJSONObject);
					}
				} else {

				}
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
		}
	};
	Handler add_mHandler = new Handler() {

		public void handleMessage(Message paramAnonymousMessage) {
			try {
				dia.dismiss();
				if (paramAnonymousMessage.obj != null) {
					String str = paramAnonymousMessage.obj.toString();
					JSONObject localJSONObject = new JSONObject(str);
					if (localJSONObject.getString("status").toString()
							.equals("1")) {
						Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
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
			// hospitalization 住院所有相关治疗花费 1
			// living 住院期间生活费及交通费 1
			// escorts 是否花钱请陪护 1否 2是
			// escort 陪护总费用 元
			// familys 是否家人陪护 1否 2是
			// family 陪护家人平均月薪
			// works 患病前是否有工作 1否 2是
			// work 月薪
			// extramural 出院后所有相关治疗性药物、预防复发药物及康复理疗花费
			String hospitalization = jo.getString("hospitalization");
			String living = jo.getString("living");
			String escorts = jo.getString("escorts");
			String escort = jo.getString("escort");// 1是0否
			String familys = jo.getString("familys");
			String family = jo.getString("family");
			String works = jo.getString("works");
			String work = jo.getString("work");
			String extramural = jo.getString("extramural");

			frg_21_1.setText(hospitalization);
			frg_21_2.setText(living);
			frg_21_3.setText(escorts);
			frg_21_4.setText(family);
			frg_21_5.setText(work);
			frg_21_6.setText(extramural);

			SetRadioGroupChecked(escort, radioGroup_1, c0, c1);
			SetRadioGroupChecked(familys, radioGroup_2, c2, c3);
			SetRadioGroupChecked(works, radioGroup_3, c4, c5);

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
		} else {
			g.check(b2.getId());
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit:
			fetchDataFromWebservice(1);
			break;
		}
	}
}
