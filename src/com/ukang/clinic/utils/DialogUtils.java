package com.ukang.clinic.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author ZZD
 * @version 创建时间：2016年5月21日 下午5:23:14 加载Dialog工具类
 * 
 */
public class DialogUtils {

	public static ProgressDialog dia;

	public static void startDialog(Context context) {
		dia = new ProgressDialog(context);
		// dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		dia.show();
	}

	public static void stopDialog() {
		dia.dismiss();
	}

	/**
	 * 简易确定提醒框
	 * 
	 * @param str
	 */
	public static void NewDialog(String str, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(str).setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
