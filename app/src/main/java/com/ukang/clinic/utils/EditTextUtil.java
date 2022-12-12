package com.ukang.clinic.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditTextUtil {
	Context con;
	ArrayList<EditText> list = new ArrayList<EditText>();

	public EditTextUtil(Context con, ArrayList<EditText> list) {
		this.con = con;
		this.list = list;
	}

	/**
	 * EditText设置范围,并计算总分
	 * 
	 * @param list
	 * @param tv_total
	 * @param et
	 * @param maxVal
	 * @param minVal
	 */
	public void setRegion(final TextView tv_total, final EditText et,
			final int maxVal, final int minVal) {
		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int start,
					int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(s)) {
					if (maxVal != -1 && minVal != -1) {
						int number = Integer.valueOf(s.toString());
						if (number > maxVal) {
							et.setText(String.valueOf(maxVal));
							Toast.makeText(con, "不能超过" + maxVal,
									Toast.LENGTH_SHORT).show();
						} else if (number < minVal) {
							et.setText(String.valueOf(minVal));
						}
					}
				}
				int count = 0;
				EditText _et = null;
				String _temp = "";
				for (int m = 0; m < list.size(); m++) {
					_et = list.get(m);
					_temp = _et.getText().toString().trim();
					if (!TextUtils.isEmpty(_temp)) {
						count += Integer.valueOf(_temp);
					}
				}
				tv_total.setText(String.valueOf(count));
			}
		});
	}

	/**
	 * Barthel评分
	 * 
	 * @param tv_total
	 * @param et
	 * @param maxVal
	 * @param minVal
	 */
	public void setRegion_Barthel(final TextView tv_total, final EditText et,
			final ArrayList<Integer> li) {
		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int start,
					int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(s)) {
					int number = Integer.valueOf(s.toString());
					if (!li.contains(number)) {
						if (number == 1 && li.contains(10)) {
						} else {
							et.setText("");
							/*
							 * Toast.makeText(con, "请在范围内填写",
							 * Toast.LENGTH_SHORT) .show();
							 */
						}
					}
				}
				int count = 0;
				EditText _et = null;
				String _temp = "";
				for (int m = 0; m < list.size(); m++) {
					_et = list.get(m);
					_temp = _et.getText().toString().trim();
					if (!TextUtils.isEmpty(_temp)) {
						count += Integer.valueOf(_temp);
					}
				}
				tv_total.setText(String.valueOf(count));
			}
		});
	}
}
