package com.ukang.clinicaltrial.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ukang.clinic.R;

import java.util.Calendar;

public class MobileTime extends LinearLayout {
	final Calendar c = Calendar.getInstance();
	private int mHour = c.get(Calendar.HOUR_OF_DAY);
	private int mMinute = c.get(Calendar.MINUTE);
	private static final int Time_DIALOG_ID = 0;
	private TextView time;
	private LinearLayout timep;
	private Context mContext;

	public MobileTime(final Context context) {
		super(context);
		time.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dateti(context);
			}

		});
		// TODO Auto-generated constructor stub
	}

	public MobileTime(final Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.mobile_time, this);
		time = (TextView) findViewById(R.id.textView1);
		if (!isInEditMode()) {
			timep = (LinearLayout) findViewById(R.id.time_layout);
		}
		mContext = context;
		setTime();
		if (!isInEditMode()) {
			time.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dateti(context);
				}

			});
		}
	}

	public TimePickerDialog dateti(Context context) {
		mContext = context;
		TimePickerDialog dateDi = (TimePickerDialog) this
				.onCreateDialog(Time_DIALOG_ID);
		dateDi.show();
		return dateDi;
	}

	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			mHour = hourOfDay;
			mMinute = minute;
			updateTime();
		}
	};

	/**
	 * 设置日期
	 */
	private void setTime() {

		updateTime();
	}

	public String updateTime() {
		if (!isInEditMode()) {
			time.setText(new StringBuilder()
					.append((mHour) < 10 ? "0" + (mHour) : (mHour)).append(":")
					.append((mMinute) < 10 ? "0" + (mMinute) : (mMinute)));
		}
		return (new StringBuilder().append(
				(mHour) < 10 ? "0" + (mHour) : (mHour)).append(":")
				.append((mMinute) < 10 ? "0" + (mMinute) : (mMinute)))
				.toString();
	}

	protected Dialog onCreateDialog(int id) {
		return new TimePickerDialog(mContext, timeSetListener, mHour, mMinute,
				true);
	}

	protected void onPrepareDialog(int id, Dialog dialog) {
		((TimePickerDialog) dialog).updateTime(mHour, mMinute);
	}

	public String getText() {
		return time.getText().toString();
	}

	public void setText(String value) {
		time.setText(value);
	}
}
