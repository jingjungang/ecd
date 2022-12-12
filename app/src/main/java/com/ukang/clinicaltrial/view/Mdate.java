package com.ukang.clinicaltrial.view;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ukang.clinic.R;

import java.util.Calendar;

public class Mdate extends LinearLayout {

	public TextView dateTime;
	private Context mContext;
	public String newdate;
	private int textSize;

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public void setnewdate(String newdate) {
		this.newdate = newdate;
	}

	public Mdate(final Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.mobile_date_nobackground, this);
		dateTime = (TextView) findViewById(R.id.textView1);
		// dateTime.setTextSize(textSize);
		if (!isInEditMode()) {
			dateTime.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CreateDialog();
				}
			});
		}
		Calendar dateAndTime = Calendar.getInstance();// --获取一个日历对象；
		int mYear = dateAndTime.get(Calendar.YEAR);
		int mMonth = dateAndTime.get(Calendar.MONTH);
		int mDay = dateAndTime.get(Calendar.DAY_OF_MONTH);
		if (!isInEditMode()) {
			dateTime.setText(new StringBuilder()
					.append(mYear)
					.append("-")
					.append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
							: (mMonth + 1)).append("-")
					.append((mDay < 10) ? "0" + mDay : mDay));
		}
	}

	protected void CreateDialog() {
		final DatePicker mDatePicker = new DatePicker(mContext);
		int mYear, mMonth, mDay;
		if (!TextUtils.isEmpty(newdate) && newdate.contains("-")) {
			mYear = Integer.valueOf(newdate.split("-")[0]);
			mMonth = Integer.valueOf(newdate.split("-")[1]) - 1;
			mDay = Integer.valueOf(newdate.split("-")[2]);
			mDatePicker.updateDate(mYear, mMonth, mDay);
		} else {
			Calendar dateAndTime = Calendar.getInstance();// --获取一个日历对象；
			mYear = dateAndTime.get(Calendar.YEAR);
			mMonth = dateAndTime.get(Calendar.MONTH);
			mDay = dateAndTime.get(Calendar.DAY_OF_MONTH);
			mDatePicker.updateDate(mYear, mMonth, mDay);
		}
		CustomerDialog_1.Builder timeDialog = new CustomerDialog_1.Builder(
				mContext);
		timeDialog.setTitle("时间选择");
		timeDialog.setContentView(mDatePicker); // 只需将您时间控件的view引入
		timeDialog
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 取出TimePicker设定的时间就行了
						int year = mDatePicker.getYear();
						int month = mDatePicker.getMonth();
						int dayOfMonth = mDatePicker.getDayOfMonth();
						StringBuilder sb = new StringBuilder()
								.append(year)
								.append("-")
								.append((month + 1) < 10 ? "0" + (month + 1)
										: (month + 1))
								.append("-")
								.append((dayOfMonth < 10) ? "0" + dayOfMonth
										: dayOfMonth);
						setText(sb.toString());
						dialog.dismiss();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create().show();
	}

	/**
	 * 设置位置
	 * 
	 * @param set
	 */
	public void setGarity(int set) {
		dateTime.setGravity(set);
	}

	public String getText() {
		return dateTime.getText().toString();
	}

	public void setText(String date) {
		dateTime.setText(date);
		newdate = date.toString();
	}
}
