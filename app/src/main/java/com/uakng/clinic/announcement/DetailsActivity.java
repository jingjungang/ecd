package com.uakng.clinic.announcement;

/**
 * 项目公告详情
 * 景俊钢
 * 2016年7月20日 15:12:23
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ukang.clinic.R;
import com.ukang.clinic.adapter.NotificationDetails_Adapter;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.systembartint.SystemBarTintManager;
import com.ukang.clinic.thread.XThread;
import com.ukang.clinic.utils.AsyncLoadingImg;
import com.ukang.clinic.utils.DialogUtils;
import com.ukang.clinic.utils.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends Activity {

	private SystemBarTintManager tintManager;

	@ViewInject(R.id.title)
	private TextView tv_title;
	@ViewInject(R.id.name)
	private TextView tv_name;
	@ViewInject(R.id.picture)
	private ImageView img_picture;
	@ViewInject(R.id.content)
	private TextView tv_content;

	@ViewInject(R.id.lv)
	private ListView lv;

	@ViewInject(R.id.text)
	private EditText et_text;

	String rid = ""; // 记录ID

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		ViewUtils.inject(this);
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.action_bar);

		Intent i = getIntent();
		if (i != null) {
			rid = i.getExtras().getString("id");
			LoadData();
		}

	}

	/**
	 * 查询数据 thread
	 * 
	 * @param id
	 */
	private void LoadData() {
		DialogUtils.startDialog(DetailsActivity.this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("id", rid);
		XThread thread = new XThread(DetailsActivity.this, 0, params,
				Constant.ANNOUNCEMENT_Details_URL, mhandler);
		thread.setShowDia(true);
		thread.start();
	}

	/**
	 * 保存数据 thread
	 * 
	 * @param id
	 */
	private void SaveData(String str) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("token", Constant.token);
		params.addBodyParameter("id", rid);
		params.addBodyParameter("content", str);
		XThread thread = new XThread(DetailsActivity.this, 0, params,
				Constant.ANNOUNCEMENT_ADD_URL, xHandler);
		thread.setShowDia(true);
		thread.start();
	}

	/**
	 * 保存记录Handler
	 */
	Handler xHandler = new Handler() {
		public void handleMessage(Message msg) {
			DialogUtils.stopDialog();
			switch (msg.what) {
			case 1:
				Toast.makeText(DetailsActivity.this, "发送失败", Toast.LENGTH_SHORT)
						.show();
				break;
			case 0:
				String reuslt = msg.obj.toString();
				JSONObject localJSONObject;
				try {
					localJSONObject = new JSONObject(reuslt);
					if (localJSONObject.getString("status").toString()
							.equals("1")) {
						LoadData();
						et_text.setText("");
					} else {
						Toast.makeText(DetailsActivity.this, "发送失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	};
	/**
	 * 查询记录Handler
	 */
	Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			DialogUtils.stopDialog();
			String result = msg.obj.toString();
			try {
				JSONObject json = new JSONObject(result).getJSONObject("info");
				// *************************************************************************************左
				String title = json.has("title") ? json.getString("title") : "";
				String add_time = json.has("add_time") ? json
						.getString("add_time") : "";
				String name = json.has("username") ? json.getString("username")
						: "";
				String thumb = json.has("thumb") ? json.getString("thumb") : "";
				String content = json.has("content") ? json
						.getString("content") : "";
				tv_title.setText(title);
				tv_name.setText(add_time + " " + name + " (项目经理)");
				tv_content.setText("  "
						+ TextUtils.stringFilter(TextUtils.ToDBC(content)));
				ImageLoader imageLoader = AsyncLoadingImg
						.getImageLoader(DetailsActivity.this);
				imageLoader.displayImage(thumb, img_picture,
						AsyncLoadingImg.getDefaultOptions());
				// new BitmapUtils(DetailsActivity.this).display(img_picture,
				// thumb);
				// *************************************************************************************右
				String commentlist = json.getString("commentlist");
				JSONArray joo = new JSONArray(commentlist);
				if (joo != null && joo.length() > 0) {
					NotificationDetails_Adapter adapter = new NotificationDetails_Adapter(
							getApplication(), joo);
					lv.setAdapter(adapter);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	};

	/**
	 * 返回
	 * 
	 * @param view
	 */
	@OnClick(R.id.img_back)
	public void btn_messages(View view) {
		finish();
	}

	/**
	 * 发送
	 * 
	 * @param view
	 */
	@OnClick(R.id.send)
	public void btn_send(View view) {
		SaveData(et_text.getText().toString());
	}
}
