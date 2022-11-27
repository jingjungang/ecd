package com.ukang.clinic.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.DataParser;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.entity.Response;
import com.ukang.clinic.entity.Users;
import com.ukang.clinic.patient.MenuActivity;
import com.ukang.clinic.systembartint.SystemBarTintManager;
import com.ukang.clinic.thread.MultiRequestThread;
import com.ukang.clinic.thread.RequestThread;
import com.umeng.analytics.MobclickAgent;

/**
 * 登陆
 * 
 * @author SAN
 * 
 */
@SuppressWarnings("deprecation")
public class LoginActivity extends Activity {
	private final String TAG = "LoginActivity";
	private final static int SCANNIN_GREQUEST_CODE = 1;

	public static final String GUEST_NAME = "13311036301";
	public static final String GUEST_PWD = "baiyuguest";

	public static final int ACTION_TYPE_NORMAL = 0;
	public static final int ACTION_TYPE_NEED_LOGIN = 1;// 需要登录

	@ViewInject(R.id.iv_back)
	private ImageButton btnBack;
	@ViewInject(R.id.btn_login)
	private Button btnLogin;// 登陆按钮
	@ViewInject(R.id.username)
	private EditText etUsername;
	@ViewInject(R.id.password)
	private EditText etPassword;// 用户名、密码
	@ViewInject(R.id.cb_store_pwd)
	private CheckBox cbStorePwd;// 是否记住密码

	private Context mContext;
	private MultiRequestThread mThread;
	private RequestThread rThread;
	private Response response;

	private int actionType;
	MWDApplication myapp;
	SharedPreferences user;
	SharedPreferences.Editor sharedata;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		ViewUtils.inject(this);
		user = getSharedPreferences("user_info", MODE_PRIVATE);
		sharedata = user.edit();
		mContext = this;
		myapp = (MWDApplication) mContext.getApplicationContext();
		actionType = getIntent().getIntExtra("actionType", 0);
		initview();
		addClickListener();
		System.out.println("actionType: " + actionType);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.action_bar);

		// 获取屏幕高度
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width1 = metric.widthPixels; // 屏幕宽度（像素）
		int height1 = metric.heightPixels; // 屏幕高度（像素）
		float density = metric.density;
		Log.i(TAG, width1 + "*" + height1);
		Log.i(TAG, "density" + ":" + density);
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

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void initview() {
		if (actionType != ACTION_TYPE_NEED_LOGIN) {
			btnBack.setVisibility(View.INVISIBLE);
		}
		etUsername.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				etPassword.setText("");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		if (Constant.users != null && actionType != ACTION_TYPE_NEED_LOGIN) {
			etUsername.setText(Constant.users.getUsername());
			etPassword.setText(Constant.users.getPassword());
		}
		if (actionType == ACTION_TYPE_NEED_LOGIN) {
		}
		String t = user.getString("cbStorePwd", null);
		if (t != null && t.equals("1")) {
			cbStorePwd.setChecked(true);
		} else {
			cbStorePwd.setChecked(false);
		}
		cbStorePwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (cbStorePwd.isChecked()) {
					myapp.WriteUser(etUsername.getText().toString(), etPassword
							.getText().toString(), cbStorePwd.isChecked());
					sharedata.putString("cbStorePwd", "1");
					sharedata.commit();
				} else {
					myapp.WriteUser(etUsername.getText().toString(), "",
							cbStorePwd.isChecked());
					sharedata.putString("cbStorePwd", "0");
					sharedata.commit();
				}
			}
		});
	}

	private void addClickListener() {
		btnBack.setOnClickListener(btnClick);
		btnLogin.setOnClickListener(loginClick);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				etUsername.setText(bundle.getString("result"));
				// mImageView.setImageBitmap((Bitmap)
				// data.getParcelableExtra("bitmap"));
			}
			break;
		}
	}

	private ProgressDialog dialog;

	private void showDialog() {
		dialog = new ProgressDialog(mContext);
		dialog.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					System.out.println("onkey ： BACK");
					cancelRequest();
					return true;
				}
				return false;
			}
		});
		dialog.setCancelable(false);
		dialog.show();
	}

	private void cancelDialog() {
		if (dialog != null)
			dialog.dismiss();
	}

	private void cancelRequest() {
		if (dialog != null)
			dialog.dismiss();
		if (rThread != null)
			rThread.cannelRequest();
		if (mThread != null)
			mThread.cannelRequest();
	}

	private OnClickListener btnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btnBack) {
				finish();
			}
		}
	};

	private OnClickListener loginClick = new OnClickListener() {

		public void onClick(View v) {
			if (v == btnLogin) {
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				if ("".equals(username.trim()) || "".equals(password.trim())) {
					Toast.makeText(mContext, "请输入用户名和密码", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (MWDUtils.isNetworkConnected(mContext)) {
					Constant.token = null;
					showDialog();
					Constant.sessionId = null;
					// guest guest baiyuguest
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("mobile", username));
					params.add(new BasicNameValuePair("password", password));
					params.add(new BasicNameValuePair("type", "3")); // 3临床实验2患者1医生
					rThread = new RequestThread(params, "http", "post",
							Constant.LOGIN_URL, loginHandler);
					rThread.start();
				} else {
					Toast.makeText(mContext, "网络出错", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

	private Handler loginHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			cancelDialog();
			if (msg.what == -1) {
				failHandler.sendEmptyMessage(0);
				return;
			} else {
				String str = (String) msg.obj;
				if (Constant.ISDEBUG)
					Log.d(TAG, str);
				if (str != null && !str.equals("")) {
					try {
						response = DataParser.parseUserInfo(str);
						int ret = response.getRet();
						if (ret == 1) {
							Users users = new Users();
							String nickname = new JSONObject(str)
									.getJSONObject("into")
									.getString("nickname").toString().trim();
							Constant.nickname = nickname;
							users.setUsername(etUsername.getText().toString());
							users.setPassword(etPassword.getText().toString());
							Constant.users = users;
							// Constant.userinfo = (UserInfo) response.getObj();
							if (Constant.ISDEBUG)
								Log.d(TAG, "write user to pref...");
							myapp.WriteUser(etUsername.getText().toString(),
									etPassword.getText().toString(),
									cbStorePwd.isChecked());
						} else {
							loginFailHandler.sendEmptyMessage(ret);
							return;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						failHandler.sendEmptyMessage(0);
						return;
					}
				} else {
					loginFailHandler.sendEmptyMessage(-5);
					return;
				}
			}
			// goRequest();
			if (actionType != ACTION_TYPE_NEED_LOGIN) {
				Intent i = new Intent(mContext, MenuActivity.class);
				startActivity(i);
			}
			finish();
		}
	};

	private Handler loginFailHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			cancelDialog();
			String str = "";
			switch (msg.what) {
			case -1:
				str = "手机号格式错误";
				break;
			case -3:
				str = "帐号错误";// 不存在
				break;
			case -4:
				str = "密码错误";
				break;
			case -5:
				str = "登录失败";
				break;
			default:
				str = "用户名或密码错误";
				break;
			}
			Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
		}
	};

	public Handler failHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			cancelDialog();
			if (response != null) {
				Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(mContext, "登录失败，请重试", Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (actionType == ACTION_TYPE_NEED_LOGIN) {
				finish();
				return true;
			}
			// System.exit(0);
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
