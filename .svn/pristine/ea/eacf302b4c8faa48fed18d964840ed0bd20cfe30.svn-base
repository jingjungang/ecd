package com.ukang.clinic.service;
/**
 * 广播-用于图片接受
 * 景俊钢
 * 2016年7月2日 10:55:07
 */
import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class PicBroadcastRecevicer extends BroadcastReceiver {
	Handler mHandler;

	public PicBroadcastRecevicer(Handler hd) {
		this.mHandler = hd;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String num = intent.getStringExtra("num");
		Bundle b = new Bundle();
		Message msg;
		if (num.equals("1")) {
			List<String> names = intent.getStringArrayListExtra("names");
			b.putStringArrayList("data", (ArrayList<String>) names);
			msg = mHandler.obtainMessage(1);
		} else {
			msg = mHandler.obtainMessage(2);
		}
		msg.setData(b);
		mHandler.sendMessage(msg);
	}
}
