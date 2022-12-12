package com.ukang.clinic.fragments;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.ukang.clinic.utils.FileUtils;
import com.ukang.clinic.utils.PhotoActivity;
import com.ukang.clinic.utils.Res;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2016/6/13. 出血事件
 */
public class Bleed_Fragment extends Fragment implements OnItemClickListener {

	View root;
	static MWDApplication ma;

	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;
	@ViewInject(R.id.bleed_r1)
	private RadioButton bleed_r1;
	@ViewInject(R.id.bleed_r2)
	private RadioButton bleed_r2;
	@ViewInject(R.id.bleed_et)
	private EditText bleed_et;
	@ViewInject(R.id.submit)
	private Button bleed_commit_btn;

	private String status;

	@ViewInject(R.id.camera_gridview)
	private GridView camera_gridview;

	@ViewInject(R.id.add_img_btn)
	private Button add_img_btn;

	private List<ImgInfo> list;
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
		this.root = paramLayoutInflater.inflate(R.layout.bleed_layout,
				paramViewGroup, false);
		ViewUtils.inject(this, this.root);
		ma = (MWDApplication) getActivity().getApplication();
		Res.init(getActivity());
		radioGroup.setOnCheckedChangeListener(changeListener);
		bleed_commit_btn.setOnClickListener(commit_btn);
		((MainActivity) getActivity()).setSubmitVisibily(bleed_commit_btn);
		setBroadcastRecevicer();

		bitmapUtils = new BitmapUtils(getActivity());
		camera_gridview.setOnItemClickListener(this);
		list = new ArrayList<ImgInfo>();
		adapter = new TuPianAdapter(getActivity(), list);
		camera_gridview.setAdapter(adapter);
		add_img_btn.setOnClickListener(new OnClickListener() {

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

		onLoad();
		return this.root;
	}

	@Override
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
	 * 设置广播-接受选择或拍照图片
	 */
	private void setBroadcastRecevicer() {
		// TODO Auto-generated method stub
		PicbCast = new PicBroadcastRecevicer(pcHandler);
		IntentFilter filter = new IntentFilter();
		filter.addAction("pc");
		getActivity().registerReceiver(PicbCast, filter);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(PicbCast);
	}

	private void onLoad() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("pid", ma.pid);
		params.addBodyParameter("nums", ma.nums);
		XThread thread = new XThread(getActivity(), 0, params,
				Constant.BLEED_ECORDS_URL, mHandler);
		thread.setShowDia(true);
		thread.start();
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String result = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(result);
				int status = json.getInt("status");
				switch (status) {
				case 0:
					Toast.makeText(getActivity(), "token验证错误",
							Toast.LENGTH_SHORT).show();
					break;
				case 1:
					JSONObject item = json.getJSONObject("info");
					int statu = item.has("status") ? item.getInt("status") : 0;
					switch (statu) {
					case 0:
						bleed_r1.setChecked(true);
						break;
					case 1:
						bleed_r2.setChecked(true);
						String desc = item.has("desc") ? item.getString("desc")
								: "";
						bleed_et.setText(desc);
						break;
					}
					/**
					 * 图片显示处理
					 */
					String str_photo = item.getString("photo");
					String[] photo_num = str_photo.split(",");
					if (photo_num != null && photo_num.length > 0) {
						String str_photos = item.getString("imgs");
						String[] photos_list = str_photos.split(",");
						list.clear();
						ImgInfo imginfo;
						for (int p = 0; p < photos_list.length; p++) {
							imginfo = new ImgInfo();
							imginfo.setImgPath(photos_list[p]);
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
					break;
				case -1:
					break;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	private void onEdit() {
		RequestParams params = new RequestParams();
		params.addHeader("Cookie", Constant.sessionId);
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("pid", ma.pid);
		params.addBodyParameter("nums", ma.nums);
		params.addBodyParameter("status", status);
		params.addBodyParameter("desc", bleed_et.getText().toString().trim());

		if (photoList.size() > 0) {
			File file = null;
			for (int t = 0; t < photoList.size(); t++) {
				file = new File(photoList.get(t).toString().trim());
				params.addBodyParameter("imgs" + (t + 1), file, "image/jpg");
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

		XThread thread = new XThread(getActivity(), 0, params,
				Constant.BLEED_ECORDS_EDIT_URL, xHandler);
		thread.setShowDia(true);
		thread.start();
	}

	/**
	 * 相册选择后，图片获取Handler
	 */
	Handler pcHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (crop_bitmap == null) {
					photoList = msg.getData().getStringArrayList("data");
					ImgInfo imginfo;
					for (String path : photoList) {
						crop_bitmap = showLitBitmap(path);
						if (list.size() < 9) {
							imginfo = new ImgInfo();
							imginfo.setNetImg(false);
							imginfo.setBmp(crop_bitmap);
							list.add(imginfo);
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

	private Handler xHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String result = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(result);
				int status = json.getInt("status");
				if (status == 1) {
					Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT)
							.show();
					photoList.clear();
					list.clear();
					del_picid_list.clear();
					onLoad();
				} else {
					Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	private RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup radioGroup, int i) {
			switch (radioGroup.getCheckedRadioButtonId()) {
			case R.id.bleed_r1:
				status = "0";
				break;
			case R.id.bleed_r2:
				status = "1";
				break;

			}
		}
	};
	private OnClickListener commit_btn = new OnClickListener() {
		@Override
		public void onClick(View view) {
			onEdit();
		}
	};

}
