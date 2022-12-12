package com.ukang.clinic.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ukang.clinic.R;

import java.io.Serializable;
import java.util.List;

public class CameraUtil {

	private static TextView tv_selectpicture;
	private static TextView tv_selectcamera;
	private static Intent intent;
	private static Dialog dialog;
	private static List<Bitmap> list;
	/**
	 * 实例化相机dialog*/
	public static void dialog(Context context){
		View view = LayoutInflater.from(context).inflate(R.layout.camera_dialog, null);
		dialog = new AlertDialog.Builder(context).setView(view).show();
		tv_selectpicture = (TextView) view.findViewById(R.id.tv_selectpicture);
		tv_selectcamera = (TextView) view.findViewById(R.id.tv_selectcamera);
		tv_selectpicture.setOnClickListener(new Listener(context));
		tv_selectcamera.setOnClickListener(new Listener(context));
	}
	public static void disMissDialog(){
		   if(dialog!=null&&dialog.isShowing()){
			   dialog.dismiss();
			   dialog=null;
		   }
		   
		   
	}
	/**
	 * 内部类实现dialog按钮点击事件*/
	static class Listener implements OnClickListener{

		private Context context;
		
		public Listener(Context context) {
			super();
			this.context = context;
		}

		@Override
		public void onClick(View arg0) {
			switch(arg0.getId()){
			case R.id.tv_selectpicture:
				intent = new Intent(context,PhotoActivity.class);//调用android的图库 
				intent.putExtra("size", (Serializable)list);
				((Activity) context).startActivityForResult(intent, 1); 
				disMissDialog();
				break;
			case R.id.tv_selectcamera:
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用android自带的照相机 
				((Activity) context).startActivityForResult(intent, 2); 
				disMissDialog();
				break;
			}
		}
	}
}
