package com.ukang.clinic.fragments;

/**
 * 大便常规
 * jjg 2016年4月20日 15:27:02
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.adapter.TuPianAdapter;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.entity.ImgInfo;
import com.ukang.clinic.main.MainActivity;
import com.ukang.clinic.service.PicBroadcastRecevicer;
import com.ukang.clinic.thread.XThread;
import com.ukang.clinic.utils.DialogUtil;
import com.ukang.clinic.utils.FileUtils;
import com.ukang.clinic.utils.PhotoActivity;
import com.ukang.clinicaltrial.view.Mdate;

public class ShitBaseStandardFragment extends Fragment implements
		AdapterView.OnItemClickListener {

	View root;
	private XThread rThread;
	public ProgressDialog dia;
	MWDApplication ma;

	/**
	 * 结果
	 */
	@ViewInject(R.id.frg_6_1a)
	private EditText frg_6_1a;
	@ViewInject(R.id.frg_6_1b)
	private EditText frg_6_1b;
	@ViewInject(R.id.frg_6_1c)
	private EditText frg_6_1c;
	@ViewInject(R.id.frg_6_1d)
	private EditText frg_6_1d;
	/**
	 * 其他单位
	 */
	@ViewInject(R.id.frg_6_2a)
	private EditText frg_6_2a;
	@ViewInject(R.id.frg_6_2b)
	private EditText frg_6_2b;
	@ViewInject(R.id.frg_6_2c)
	private EditText frg_6_2c;
	@ViewInject(R.id.frg_6_2d)
	private EditText frg_6_2d;
	/**
	 * 异常值
	 */
	/**
	 * 常规检查
	 */
	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;
	@ViewInject(R.id.frg_6_3a)
	private RadioButton frg_6_3a;
	@ViewInject(R.id.frg_6_3b)
	private RadioButton frg_6_3b;
	@ViewInject(R.id.frg_6_3c)
	private RadioButton frg_6_3c;
	@ViewInject(R.id.frg_6_3d)
	private RadioButton frg_6_3d;
	/**
	 * 红细胞
	 */
	@ViewInject(R.id.radioGroup_1)
	private RadioGroup radioGroup_1;
	@ViewInject(R.id.frg_6_4a)
	private RadioButton frg_6_4a;
	@ViewInject(R.id.frg_6_4b)
	private RadioButton frg_6_4b;
	@ViewInject(R.id.frg_6_4c)
	private RadioButton frg_6_4c;
	@ViewInject(R.id.frg_6_4d)
	private RadioButton frg_6_4d;
	/**
	 * 白细胞
	 */
	@ViewInject(R.id.radioGroup_2)
	private RadioGroup radioGroup_2;
	@ViewInject(R.id.frg_6_5a)
	private RadioButton frg_6_5a;
	@ViewInject(R.id.frg_6_5b)
	private RadioButton frg_6_5b;
	@ViewInject(R.id.frg_6_5c)
	private RadioButton frg_6_5c;
	@ViewInject(R.id.frg_6_5d)
	private RadioButton frg_6_5d;
	/**
	 * 潜血实验
	 */
	@ViewInject(R.id.radioGroup_3)
	private RadioGroup radioGroup_3;
	@ViewInject(R.id.frg_6_6a)
	private RadioButton frg_6_6a;
	@ViewInject(R.id.frg_6_6b)
	private RadioButton frg_6_6b;
	@ViewInject(R.id.frg_6_6c)
	private RadioButton frg_6_6c;
	@ViewInject(R.id.frg_6_6d)
	private RadioButton frg_6_6d;
	/**
	 * 异常说明
	 */
	@ViewInject(R.id.frg_6_7a)
	private EditText frg_6_7a;
	@ViewInject(R.id.frg_6_7b)
	private EditText frg_6_7b;
	@ViewInject(R.id.frg_6_7c)
	private EditText frg_6_7c;
	@ViewInject(R.id.frg_6_7d)
	private EditText frg_6_7d;

	@ViewInject(R.id.ctime)
	private Mdate mtime;

	@ViewInject(R.id.submit)
	private Button submit;

	@ViewInject(R.id.add_img_btn)
	private Button add_img_btn;

	@ViewInject(R.id.camera_gridview)
	private GridView camera_gridview;

	private List<ImgInfo> list;
	private List<Bitmap> phoChangeList;
	private Bitmap crop_bitmap;
	/**
	 * 点击的图片位置
	 */
	int k = 0;
	int j;
	private List<String> photoList = new ArrayList<String>();
	private TuPianAdapter adapter;
	Intent intent;
	PicBroadcastRecevicer PicbCast; // 广播接受-用于接受图片
	List<String> del_picid_list = new ArrayList<String>();
	BitmapUtils bitmapUtils;
	String photo_path = "";

	@Override
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		this.root = paramLayoutInflater.inflate(R.layout.layout_shit_routine,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		ma = (MWDApplication) getActivity().getApplication();
		initview();
		addClickListener();
		setBroadcastRecevicer();
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				updateDataByPost(2);
			}
		});
		((MainActivity) getActivity()).setSubmitVisibily(submit);
		updateDataByPost(1);
		bitmapUtils = new BitmapUtils(getActivity());
		return this.root;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(PicbCast);
	}

	/**
	 * 数据请求 1请求，2编辑
	 */
	private void updateDataByPost(int i) {
		dia = new ProgressDialog(getActivity());
		dia.setMessage("请稍候...");
		dia.setCanceledOnTouchOutside(false);
		dia.show();
		RequestParams params = new RequestParams();
		params.addHeader("Cookie", Constant.sessionId);
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("nums", ma.nums);
		if (i == 1) {
			params.addBodyParameter("id", ma.pid);
			this.rThread = new XThread(getActivity(), 0, params,
					Constant.SHIT_URL, mHandler);
		} else {
			if (TextUtils.isEmpty(mtime.getText().toString().trim())) {
				Toast.makeText(getActivity(), "请点击输入时间", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			params.addBodyParameter("eid", ma.pid);
			params.addBodyParameter("js", getJSON().toString());
			params.addBodyParameter("ctime", mtime.getText().toString());
			if (photoList.size() > 0) {
				File file = null;
				for (int t = 0; t < photoList.size(); t++) {
					file = new File(photoList.get(t));
					params.addBodyParameter("imgs" + (t + 1), file);
				}
				params.addBodyParameter("filenum", photoList.size() + "");
			} else {
				params.addBodyParameter("filenum", "0");
			}
			if (del_picid_list.size() > 0) {
				String str_ids = del_picid_list.toString().replace("[", "")
						.replace("]", "");
				params.addBodyParameter("delimgs", str_ids);
			}
			this.rThread = new XThread(getActivity(), 0, params,
					Constant.SHIT_URL, mHandler_edit);
		}
		this.rThread.start();
	}

	// /**
	// * 数据请求 1请求，2编辑
	// */
	// private void updateDataByPost(int i) {
	// dia = new ProgressDialog(getActivity());
	// dia.setMessage("请稍候...");
	// dia.setCanceledOnTouchOutside(false);
	// dia.show();
	// ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
	// localArrayList.add(new BasicNameValuePair("nums", ma.nums));
	// localArrayList.add(new BasicNameValuePair("token", Constant.token));
	// if (i == 1) {
	// localArrayList.add(new BasicNameValuePair("id", ma.pid));
	// this.rThread = new RequestThread(localArrayList, "http", "post",
	// Constant.SHIT_URL, mHandler);
	// } else {
	// localArrayList.add(new BasicNameValuePair("eid", ma.pid));
	// localArrayList.add(new BasicNameValuePair("js", getJSON().toString()));
	// localArrayList.add(new BasicNameValuePair("ctime",
	// mtime.getText().toString()));
	// this.rThread = new RequestThread(localArrayList, "http", "post",
	// Constant.SHIT_URL, mHandler_edit);
	// }
	// this.rThread.start();
	// }

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();
			try {
				String reuslt = msg.obj.toString();
				JSONObject localJSONObject = new JSONObject(reuslt);
				if (localJSONObject.getString("status").toString().equals("1")) {
					JSONObject js = localJSONObject.getJSONObject("info");
					setLocalData(js);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	Handler mHandler_edit = new Handler() {
		public void handleMessage(Message msg) {
			dia.dismiss();

			if (null != msg.obj) {
				String reuslt = msg.obj.toString();
				try {
					JSONObject localJSONObject = new JSONObject(reuslt);
					// status -3无数据
					if (localJSONObject.getString("status").toString()
							.equals("1")) {
						Toast.makeText(getActivity(), "保存成功",
								Toast.LENGTH_SHORT).show();
						photoList.clear();
						list.clear();
						del_picid_list.clear();
						updateDataByPost(1);
					} else {
						Toast.makeText(getActivity(), "保存失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private void initview() {
		camera_gridview.setOnItemClickListener(this);
		list = new ArrayList<ImgInfo>();
		adapter = new TuPianAdapter(getActivity(), list);
		camera_gridview.setAdapter(adapter);
		phoChangeList = new ArrayList<Bitmap>();
		mtime.setText("");
		DialogUtil.toast(frg_6_3c, getActivity());
		DialogUtil.toast(frg_6_4c, getActivity());
		DialogUtil.toast(frg_6_5c, getActivity());
		DialogUtil.toast(frg_6_6c, getActivity());
	}

	/**
	 * 设置广播-接受选择或拍照图片
	 */
	private void setBroadcastRecevicer() {
		// TODO Auto-generated method stub
		PicbCast = new PicBroadcastRecevicer(pcHandler);
		IntentFilter filter = new IntentFilter();
		filter.addAction("pc");
		getActivity().registerReceiver(PicbCast, filter);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		k = arg2;

		ImageView img = new ImageView(getActivity());
		ImgInfo imginfo = list.get(k);
		if (imginfo.isNetImg()) {
			bitmapUtils.display(img, imginfo.getImgPath());
		} else {
			img.setImageBitmap(imginfo.getBmp());
		}
		new AlertDialog.Builder(getActivity()).setView(img)
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}

				})
				.setNegativeButton("删除", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						ImgInfo imginfo = list.get(k);
						if (imginfo.isNetImg()) {
							del_picid_list.add(imginfo.getId());
						}
						list.remove(k);
						adapter.notifyDataSetChanged();
						dialog.dismiss();
					}

				}).show();
	}

	private void addClickListener() {
		add_img_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list.size() >= 9) {
					Toast.makeText(getActivity(), "最多可以添加9张照片",
							Toast.LENGTH_SHORT).show();
				} else {
					showChoosePicDia();
				}
			}
		});
	}

	void showChoosePicDia() {
		CharSequence[] items = { "相册", "相机" };
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("选择");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					intent = new Intent(getActivity(), PhotoActivity.class);// 调用android的图库
					// intent.putExtra("size", (Serializable) list);
					getActivity().startActivityForResult(intent,
							Activity.RESULT_FIRST_USER);
					dialog.dismiss();
					break;
				case 1:
					File file = FileUtils.NewFile();
					photo_path = file.getPath();
					intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					getActivity().startActivityForResult(intent, 2);
					dialog.dismiss();
					break;
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	/**
	 * 相册选择后，图片获取Handler
	 */
	Handler pcHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// list.clear(); // 初始化先清空从相册中选择的所有图片
				phoChangeList.clear();
				if (crop_bitmap == null) {
					photoList = msg.getData().getStringArrayList("data");
					ImgInfo imginfo;
					for (String path : photoList) {
						crop_bitmap = showLitBitmap(path);// PathChangeBitMap.convertToBitmap(path,200,
						// 200);
						if (list.size() < 9) {
							imginfo = new ImgInfo();
							imginfo.setNetImg(false);
							imginfo.setBmp(crop_bitmap);
							if (!list.contains(imginfo)) {
								list.add(imginfo);
							}
							phoChangeList.add(crop_bitmap);
						}
					}
					camera_gridview.setVisibility(View.VISIBLE);
					adapter.notifyDataSetChanged();
					crop_bitmap = null;
				}
				break;
			case 2:
				ImgInfo imginfo = new ImgInfo();
				imginfo.setNetImg(false);
				imginfo.setImgPath(photo_path);
				imginfo.setBmp(showLitBitmap(photo_path));
				list.add(imginfo);
				photoList.add(photo_path);
				camera_gridview.setVisibility(View.VISIBLE);
				adapter.notifyDataSetChanged();
				break;
			}
		}
	};

	/**
	 * 显示小图片
	 * 
	 * @param imgUrl
	 * @return
	 */
	private Bitmap showLitBitmap(String imgUrl) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			// 先设置为TRUE不加载到内存中，但可以得到宽和高
			options.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeFile(imgUrl, options); // 此时返回bm为空
			options.inJustDecodeBounds = false;
			// 计算缩放比
			int outW = options.outWidth > options.outHeight ? options.outWidth
					: options.outHeight;
			int be = (int) (outW / (float) (512));
			if (be <= 0)
				be = 1;
			options.inSampleSize = be;
			// 这样就不会内存溢出了
			bitmap = BitmapFactory.decodeFile(imgUrl, options);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
		return bitmap;
	}

	/**
	 * 显示历史记录
	 * 
	 * @param localJSONObject
	 */
	protected void setLocalData(JSONObject localJSONObject) {
		JSONObject js = null;
		String ctime = "";
		try {
			js = localJSONObject.getJSONObject("lab");
			frg_6_1a.setText(js.getString("1"));
			frg_6_1b.setText(js.getString("2"));
			frg_6_1c.setText(js.getString("3"));
			frg_6_1d.setText(js.getString("4"));

			js = localJSONObject.getJSONObject("oname");
			frg_6_2a.setText(js.getString("1"));
			frg_6_2b.setText(js.getString("2"));
			frg_6_2c.setText(js.getString("3"));
			frg_6_2d.setText(js.getString("4"));

			js = localJSONObject.getJSONObject("abn");
			String temp = "";
			temp = js.getString("1");
			if (!TextUtils.isEmpty(temp)) {
				if (temp.equals("1")) {
					radioGroup.check(frg_6_3a.getId());
				} else if (temp.equals("2")) {
					radioGroup.check(frg_6_3b.getId());
				} else if (temp.equals("3")) {
					radioGroup.check(frg_6_3c.getId());
				} else {
					radioGroup.check(frg_6_3d.getId());
				}
			}
			temp = js.getString("2");
			if (!TextUtils.isEmpty(temp)) {
				if (temp.equals("1")) {
					radioGroup_1.check(frg_6_4a.getId());
				} else if (temp.equals("2")) {
					radioGroup_1.check(frg_6_4b.getId());
				} else if (temp.equals("3")) {
					radioGroup_1.check(frg_6_4c.getId());
				} else {
					radioGroup_1.check(frg_6_4d.getId());
				}
			}
			temp = js.getString("3");
			if (!TextUtils.isEmpty(temp)) {
				if (temp.equals("1")) {
					radioGroup_2.check(frg_6_5a.getId());
				} else if (temp.equals("2")) {
					radioGroup_2.check(frg_6_5b.getId());
				} else if (temp.equals("3")) {
					radioGroup_2.check(frg_6_5c.getId());
				} else {
					radioGroup_2.check(frg_6_5d.getId());
				}
			}
			temp = js.getString("4");
			if (!TextUtils.isEmpty(temp)) {
				if (temp.equals("1")) {
					radioGroup_3.check(frg_6_6a.getId());
				} else if (temp.equals("2")) {
					radioGroup_3.check(frg_6_6b.getId());
				} else if (temp.equals("3")) {
					radioGroup_3.check(frg_6_6c.getId());
				} else {
					radioGroup_3.check(frg_6_6d.getId());
				}
			}
			js = localJSONObject.getJSONObject("abns");
			frg_6_7a.setText(js.getString("1"));
			frg_6_7b.setText(js.getString("2"));
			frg_6_7c.setText(js.getString("3"));
			frg_6_7d.setText(js.getString("4"));

			ctime = localJSONObject.getString("ctime");
			if (!ctime.equals("")) {
				mtime.setText(ctime);
			}
			String str_photo = localJSONObject.getString("photo");
			String[] photo_num = str_photo.split(",");
			if (photo_num != null && photo_num.length > 0) {
				String str_photos = localJSONObject.getString("imgs");
				String[] photos_list = str_photos.split(",");
				list.clear();
				ImgInfo imginfo;
				for (int p = 0; p < photos_list.length; p++) {
					imginfo = new ImgInfo();
					imginfo.setImgPath(photos_list[p].replace(
							"192.168.88.122/baiyu", "yd.baiyu.cn"));
					imginfo.setNetImg(true);
					imginfo.setId(photo_num[p]);
					if (imginfo != null) {
						list.add(imginfo);
					}
				}
				if (list.size() > 0) {
					camera_gridview.setVisibility(View.VISIBLE);
					adapter.notifyDataSetChanged();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取json数据
	 */
	private JSONObject getJSON() {
		JSONObject Json = null;
		try {
			Json = new JSONObject();
			JSONObject json1 = new JSONObject();
			json1.put("1", frg_6_1a.getText().toString());
			json1.put("2", frg_6_1b.getText().toString());
			json1.put("3", frg_6_1c.getText().toString());
			json1.put("4", frg_6_1d.getText().toString());
			Json.put("lab", json1);
			JSONObject json2 = new JSONObject();
			json2.put("1", frg_6_2a.getText().toString());
			json2.put("2", frg_6_2b.getText().toString());
			json2.put("3", frg_6_2c.getText().toString());
			json2.put("4", frg_6_2d.getText().toString());
			Json.put("oname", json2);
			JSONObject json3 = new JSONObject();
			json3.put("1", getRadio(frg_6_3a, frg_6_3b, frg_6_3c, frg_6_3d)
					+ "");
			json3.put("2", getRadio(frg_6_4a, frg_6_4b, frg_6_4c, frg_6_4d)
					+ "");
			json3.put("3", getRadio(frg_6_5a, frg_6_5b, frg_6_5c, frg_6_5d)
					+ "");
			json3.put("4", getRadio(frg_6_6a, frg_6_6b, frg_6_6c, frg_6_6d)
					+ "");
			Json.put("abn", json3);
			JSONObject json4 = new JSONObject();
			json4.put("1", frg_6_7a.getText().toString());
			json4.put("2", frg_6_7b.getText().toString());
			json4.put("3", frg_6_7c.getText().toString());
			json4.put("4", frg_6_7d.getText().toString());
			Json.put("abns", json4);
			return Json;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Json;
	}

	/**
	 * 获取RadioButton选项
	 */
	private int getRadio(RadioButton r1, RadioButton r2, RadioButton r3,
			RadioButton r4) {
		if (r1.isChecked()) {
			return 1;
		} else if (r2.isChecked()) {
			return 2;
		} else if (r3.isChecked()) {
			return 3;
		} else if (r4.isChecked()) {
			return 4;
		}
		return 1;
	}
}
