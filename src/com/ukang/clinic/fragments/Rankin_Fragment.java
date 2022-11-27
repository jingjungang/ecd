package com.ukang.clinic.fragments;

/**
 * RANKIN评分
 * zzd
 * 2016年6月2日 14:36:28
 */
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.EditText;
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

public class Rankin_Fragment extends Fragment implements OnClickListener {

	@ViewInject(R.id.frg_24_1)
	private EditText frg_24_1;
	@ViewInject(R.id.submit)
	private Button btn_submit;
	MWDApplication content;
	View root;
	String eid = ""; // use to add or edit

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_rankin,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		btn_submit.setOnClickListener(this);
		frg_24_1.addTextChangedListener(new TextWatcher() {

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
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(s)) {
					int number = Integer.valueOf(s.toString());
					if (number > 5) {
						frg_24_1.setText("5");
						Toast.makeText(getActivity(), "不能超过" + 5,
								Toast.LENGTH_SHORT).show();
					} else if (number < 0) {
						frg_24_1.setText("0");
					}
				}
			}
		});
		((MainActivity)getActivity()).setSubmitVisibily(btn_submit);
		return this.root;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		content = (MWDApplication) getActivity().getApplication();
		onLoad();
		super.onStart();
	}

	// Thread查询
	private void onLoad() {
		DialogUtils.startDialog(getActivity());
		RequestParams params = new RequestParams();
		params.addHeader("Cookie", Constant.sessionId);
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("nums", content.nums);
		params.addBodyParameter("pid", ((MWDApplication) getActivity()
				.getApplication()).pid); // 60
		XThread thread = new XThread(getActivity(), 0, params,
				Constant.RANKIN_URL, mHandler_fetch);
		thread.start();
	}

	// Thread编辑
	private void Thread_Edit() {
		DialogUtils.startDialog(getActivity());
		RequestParams params = new RequestParams();
		params.addHeader("Cookie", Constant.sessionId);
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("eid", ((MWDApplication) getActivity()
				.getApplication()).pid);
		params.addBodyParameter("nums", content.nums); // 随访次数
		params.addBodyParameter("num", frg_24_1.getText().toString());
		XThread thread = new XThread(getActivity(), 0, params,
				Constant.RANKIN_ADD_EDIT, mHandler_add_edit);
		thread.start();
	}

	// Handler-fetch
	private Handler mHandler_fetch = new Handler() {
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
					break;
				case 1:
					JSONObject js = json.getJSONObject("info");
					String num = js.has("num") ? js.getString("num") : "";
					eid = js.has("id") ? js.getString("id") : "";
					frg_24_1.setText(num);
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
	// Handler-add-edit
	private Handler mHandler_add_edit = new Handler() {
		public void handleMessage(Message msg) {
			DialogUtils.stopDialog();
			String result = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(result);
				int status = json.getInt("status");
				switch (status) {
				case 0:
					Toast.makeText(content, "保存失败", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(content, "保存成功", Toast.LENGTH_SHORT).show();
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit:
			Thread_Edit();
			break;
		}
	}
}
