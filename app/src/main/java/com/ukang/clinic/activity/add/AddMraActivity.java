package com.ukang.clinic.activity.add;

/**
 * 新增头颅MRA
 * jjg
 * 2016年4月21日 16:24:29
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.adapter.TuPianAdapter;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.entity.ImgInfo;
import com.ukang.clinic.service.PicBroadcastRecevicer;
import com.ukang.clinic.systembartint.SystemBarTintManager;
import com.ukang.clinic.thread.XThread;
import com.ukang.clinic.utils.DialogUtils;
import com.ukang.clinic.utils.FileUtils;
import com.ukang.clinic.utils.PhotoActivity;
import com.ukang.clinicaltrial.view.Mdate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddMraActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	private static SystemBarTintManager tintManager;
	public static String RECORD_ID = "id";
	public static String INSPECTION_DATE = "inspectiondate";
	public static String MSTATUS = "status";
	public static String MEXPLAIN = "explain";
	public static String C_PHOTO = "photo";
	public static String C_IMAGS = "imgs";
	@ViewInject(R.id.iv_back)
	private ImageButton back;

	@ViewInject(R.id.tv_title)
	private TextView tv_title;

	@ViewInject(R.id.submit)
	private Button btn_submit;

	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;
	@ViewInject(R.id.normal)
	private RadioButton r1;
	@ViewInject(R.id.exception)
	private RadioButton r2;

	@ViewInject(R.id.content)
	private EditText edt_content;

	@ViewInject(R.id.ctime)
	private Mdate ctime;

	@ViewInject(R.id.add_pic)
	private Button add_img_btn;

	@ViewInject(R.id.camera_gridview)
	private GridView camera_gridview;

	// ------------------------------
	Context mContent;
	private XThread rThread;
	MWDApplication application;

	String m_id = "0"; // 传入的记录ID
	/**
	 * 点击的图片位置
	 */
	private List<ImgInfo> list;
	private List<Bitmap> phoChangeList;
	private Bitmap crop_bitmap;
	int k = 0;
	int j;
	private List<String> photoList = new ArrayList<String>();
	private TuPianAdapter adapter;
	Intent intent;
	PicBroadcastRecevicer PicbCast; // 广播接受-用于接受图片
	List<String> del_picid_list = new ArrayList<String>();
	BitmapUtils bitmapUtils;
	public ProgressDialog dia;
	MWDApplication ma;
	String photo_path = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_mra);
		ViewUtils.inject(this);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.action_bar);
		mContent = AddMraActivity.this;
		application = ((MWDApplication) mContent.getApplicationContext());
		init();
		setSubmitVisibily(btn_submit);
		addClickListener();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		k = arg2;
		ImageView img = new ImageView(AddMraActivity.this);
		ImgInfo imginfo = list.get(k);
		if (null != imginfo) {
			if (imginfo.isNetImg()) {
				if (!TextUtils.isEmpty(imginfo.getImgPath())) {
					bitmapUtils.display(img, imginfo.getImgPath());
				}
			} else {
				img.setImageBitmap(imginfo.getBmp());
			}
			new AlertDialog.Builder(AddMraActivity.this)
					.setView(img)
					.setPositiveButton("退出",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}

							})
					.setNegativeButton("删除",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
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
		} else {
			Toast.makeText(AddMraActivity.this, "请稍后再试", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void setSubmitVisibily(Button submit) {
		MWDApplication ma = ((MWDApplication) getApplicationContext());
		// 1.选择访视阶段比当前阶段小，隐藏submit
		if (Integer.valueOf(ma.nums) < ma.visit_count_finish) {
			submit.setVisibility(View.GONE);
		}
		// 2.选择访视阶段等于当前阶段
		else if (Integer.valueOf(ma.nums) == ma.visit_count_finish) {
			if (ma.visit_count_ischecked == 1 // 待审核
					|| ma.visit_count_ischecked == 2 // 审核通过
					// 审核未通过1建议中止 3中止
					|| (ma.visit_count_ischecked == 3 && (ma.visit_count_isbreak == 1 || ma.visit_count_isbreak == 3))) {
				submit.setVisibility(View.GONE);
			} else {
				submit.setVisibility(View.VISIBLE);
			}
		}
		// 3.选择访视阶段大于当前阶段
		else if (Integer.valueOf(ma.nums) > ma.visit_count_finish) {

		}
	}

	private void init() {
		bitmapUtils = new BitmapUtils(AddMraActivity.this);
		camera_gridview.setOnItemClickListener(this);
		list = new ArrayList<ImgInfo>();
		adapter = new TuPianAdapter(AddMraActivity.this, list);
		camera_gridview.setAdapter(adapter);
		phoChangeList = new ArrayList<Bitmap>();

		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);

		tv_title.setVisibility(View.VISIBLE);
		tv_title.setText("新增头颅MRA");

		btn_submit.setOnClickListener(this);
		Intent i = getIntent();
		if (i.getExtras() != null) {
			String b, c, d;
			m_id = i.getExtras().getString(RECORD_ID) == null ? "" : i
					.getExtras().getString(RECORD_ID);

			b = i.getExtras().getString(INSPECTION_DATE) == null ? "" : i
					.getExtras().getString(INSPECTION_DATE);

			c = i.getExtras().getString(MSTATUS) == null ? "" : i.getExtras()
					.getString(MSTATUS);
			ctime.setText(b);
			if (c.equals("1")) {
				r1.setChecked(true);
				r2.setChecked(false);
				edt_content.setVisibility(View.INVISIBLE);
			} else {
				r1.setChecked(false);
				r2.setChecked(true);
				d = i.getExtras().getString(MEXPLAIN) == null ? "" : i
						.getExtras().getString(MEXPLAIN);
				edt_content.setVisibility(View.VISIBLE);
				edt_content.setText(d);
			}
		}
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if (arg1 == R.id.normal) {
					edt_content.setVisibility(View.INVISIBLE);
				} else if (arg1 == R.id.exception) {
					edt_content.setVisibility(View.VISIBLE);
				}
			}
		});
		// 若是编辑
		if (i.getExtras() != null) {
			String str_photo = i.getExtras().getString(C_PHOTO);
			if (!TextUtils.isEmpty(str_photo)) {
				String[] photo_num = str_photo.split(",");
				if (photo_num != null && photo_num.length > 0) {
					String str_photos = i.getExtras().getString(C_IMAGS);
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
			}
		}
		r2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "请输入异常说明",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			AddMraActivity.this.finish();
			break;
		case R.id.submit:
			String status = "";
			if (r1.isChecked()) {
				status = "1";
			} else {
				status = "2";
			}
			SaveDataByPost(status, edt_content.getText().toString(), ctime
					.getText().toString());
			break;
		}
	}

	private void addClickListener() {
		add_img_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list.size() >= 9) {
					Toast.makeText(AddMraActivity.this, "最多可以添加9张照片",
							Toast.LENGTH_SHORT).show();
				} else {
					showChoosePicDia();
				}
			}
		});
	}

	void showChoosePicDia() {
		CharSequence[] items = { "相册", "相机" };
		AlertDialog.Builder builder = new AlertDialog.Builder(
				AddMraActivity.this);
		builder.setTitle("选择");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent intent = new Intent(AddMraActivity.this,
							PhotoActivity.class);// 调用android的图库
					// intent.putExtra("size", (Serializable) list);
					AddMraActivity.this.startActivityForResult(intent,
							Activity.RESULT_FIRST_USER);
					dialog.dismiss();
					break;
				case 1:
					File file = FileUtils.NewFile();
					photo_path = file.getPath();
					intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					AddMraActivity.this.startActivityForResult(intent, 2);
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
	 * 提交
	 * 
	 * @param status
	 * @param explain
	 * @param inspectiondate
	 */
	private void SaveDataByPost(String status, String explain,
			String inspectiondate) {
		if (MWDUtils.isNetworkConnected(mContent)) {
			DialogUtils.startDialog(AddMraActivity.this);
			RequestParams params = new RequestParams();
			params.addHeader("Cookie", Constant.sessionId);
			params.addBodyParameter("id", m_id);
			params.addBodyParameter("pid", application.pid);
			params.addBodyParameter("nums", application.nums);
			params.addBodyParameter("token", Constant.token);
			params.addBodyParameter("status", status);
			if (status.equals("2")) {
				params.addBodyParameter("explain", explain);
			} else {
				params.addBodyParameter("explain", "");
			}
			params.addBodyParameter("inspectiondate", inspectiondate);
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
			this.rThread = new XThread(AddMraActivity.this, 0, params,
					Constant.MRA_ADD_URL, mHandler);

			this.rThread.start();
		}
	}

	Handler mHandler = new Handler() {

		public void handleMessage(Message paramAnonymousMessage) {
			try {
				DialogUtils.stopDialog();
				if (paramAnonymousMessage.what != -1) {
					String str = paramAnonymousMessage.obj.toString();
					JSONObject localJSONObject = new JSONObject(str);
					str = localJSONObject.getString("status");
					if (str.equals("1")) {
						AddMraActivity.this.finish();
						Toast.makeText(mContent, "保存成功", Toast.LENGTH_SHORT)
								.show();
						finish();
					} else if (str.equals("-2")) {
						Toast.makeText(mContent, "数据未修改", Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(mContent, "保存失败", Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Toast.makeText(mContent, "保存失败", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
				Toast.makeText(mContent, "保存失败", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(AddMraActivity.this, e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
		return bitmap;
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case 1:
			// list.clear(); // 初始化先清空从相册中选择的所有图片
			phoChangeList.clear();
			if (crop_bitmap == null) {
				photoList = data.getStringArrayListExtra("name");
				ImgInfo imginfo;
				for (String path : photoList) {
					crop_bitmap = showLitBitmap(path);
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
}
