package com.ukang.clinic.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Toast;

public class DialogUtil {

	public static void toast(RadioButton rb, final Context con) {
		rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(con, "请填写异常说明!", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public static void Dialog(Context con, String text) {
		new AlertDialog.Builder(con).setTitle("提示").setMessage(text)
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						arg0.dismiss();
					}
				}).show();
	}
}
