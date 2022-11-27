package com.ukang.clinic.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.ukang.clinic.systembartint.SystemBarTintManager;
import com.ukang.clinic.thread.MultiRequestThread;
import com.ukang.clinic.thread.RequestThread;
import com.umeng.analytics.MobclickAgent;

/**
 * 注册
 *
 * @author SAN
 *
 */
public class RegisterActivity extends Activity {
	private final String TAG = "RegisterActivity";

	@ViewInject(R.id.iv_back)
	private ImageButton btnBack;
	@ViewInject(R.id.btn_register)
	private Button btnRegister;// 登陆按钮
	@ViewInject(R.id.et_username)
	private EditText etUsername;
	@ViewInject(R.id.et_password)
	private EditText etPassword;// 用户名、密码
	@ViewInject(R.id.et_repassword)
	private EditText etRePassword;// 密码

	private Context mContext;
	private MultiRequestThread mThread;
	private RequestThread rThread;
	private Response response;

	private int actionType;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		ViewUtils.inject(this);
		mContext = this;
		actionType = getIntent().getIntExtra("actionType", 0);
		initview();
		addClickListener();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.index_title_color);
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
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void initview() {
		etUsername.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub
				// etPassword.setText("");
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
	}

	private void addClickListener() {
		btnRegister.setOnClickListener(loginClick);
		btnBack.setOnClickListener(btnClick);
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
		dialog.setMessage("正在注册");
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

	private OnClickListener loginClick = new OnClickListener() {

		public void onClick(View v) {
			String username = etUsername.getText().toString();
			String password = etPassword.getText().toString();
			String rePassword = etRePassword.getText().toString();
			if ("".equals(username.trim()) || "".equals(password.trim())
					|| "".equals(rePassword)) {
				Toast.makeText(mContext, "请输入用户名和密码", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (username.trim().length() < 3) {
				Toast.makeText(mContext, "用户名长度必须大于等于3位", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (!password.equals(rePassword)) {
				Toast.makeText(mContext, "两次输入密码不一致，请重新输入", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (password.trim().length() < 6) {
				Toast.makeText(mContext, "密码长度不能少于6位", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (MWDUtils.isNetworkConnected(mContext)) {
				showDialog();
				Constant.sessionId = null;
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("mobile", username));
				params.add(new BasicNameValuePair("password", password));
				rThread = new RequestThread(params, "http", "post",
						Constant.REGISTER_URL, loginHandler);
				rThread.start();
			}
			// Intent i = new Intent(mContext, MainPageActivity.class);
			// startActivity(i);
			// finish();
		}
	};

	private OnClickListener btnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	String toJson(String username, String password) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("username", username);
			obj.put("password", password);
			obj.put("versions", Constant.VERSION_CODE);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}

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
							MWDApplication myapp = (MWDApplication) mContext
									.getApplicationContext();
							// Users users = new Users();
							// users.setUsername(etUsername.getText().toString());
							// users.setPassword(etPassword.getText().toString());
							// Constant.users = users;
							// Constant.userinfo = (UserInfo) response.getObj();
							if (Constant.ISDEBUG)
								Log.d(TAG, "write user to pref...");
							myapp.WriteUser(etUsername.getText().toString(),
									"", false);
							Toast.makeText(myapp, "注册成功，请登录", Toast.LENGTH_LONG)
									.show();
							finish();
						} else if (ret == 9) {
							updateHandler.sendEmptyMessage(0);
							return;
						} else if (ret == -1) {
							nameErrorHandler.sendEmptyMessageDelayed(0, 500);
						} else if (ret == -3) {
							alreadyExistlHandler
									.sendEmptyMessageDelayed(0, 500);
						} else {
							failHandler.sendEmptyMessage(0);
							return;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						failHandler.sendEmptyMessage(0);
						return;
					}
				} else {
					loginFailHandler.sendEmptyMessage(0);
					return;
				}
			}
			// goRequest();
		}
	};

	private Handler loginFailHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			cancelDialog();
			Toast.makeText(mContext, "登录失败，请重试", Toast.LENGTH_SHORT).show();
		}
	};

	private Handler alreadyExistlHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			cancelDialog();
			Toast.makeText(mContext, "该账号已被注册", Toast.LENGTH_SHORT).show();
		}
	};

	private Handler nameErrorHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			cancelDialog();
			Toast.makeText(mContext, "手机号格式错误", Toast.LENGTH_SHORT).show();
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

	public Handler updateHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			// showChooseDateDia();
		}
	};

	void showChooseDateDia() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
				.setTitle(R.string.update_notice)
				.setMessage(response.getMsg())
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Intent intent = new Intent(mContext,
						// DownAPKService.class);
						// intent.putExtra("apk_url",
						// response.getObj().toString());
						// startService(intent);
						// Toast.makeText(mContext, "正在下载新版本，请稍后",
						// Toast.LENGTH_SHORT).show();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						finish();
					}
				}).setCancelable(false);
		builder.show();
	}

}
