package com.ukang.clinic.login;

/**
 * 加载页
 * jjg 2016年4月22日 08:48:34
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.GLES10;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.ukang.clinic.R;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.DataParser;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.entity.Response;
import com.ukang.clinic.entity.Users;
import com.ukang.clinic.patient.MenuActivity;
import com.ukang.clinic.systembartint.SystemBarTintManager;
import com.ukang.clinic.thread.MultiRequestThread;
import com.ukang.clinic.thread.RequestThread;
import com.ukang.clinic.thread.XThread;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

public class StartActivity extends Activity {
	private Context mContext = null;
	private RequestThread rThread;
	private MultiRequestThread mThread;
	private boolean animFinish = false;// 动画完成标识
	private boolean dataFinish = false;// 获取数据完成标识
	private boolean loginSuccess = false;// 登陆成功标识
	private Response response;

	private TextView tvVersionCode;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去往引导页 注释 2016年7月6日 14:24:49 没有UI引导图
		// SharedPreferences sp =
		// getSharedPreferences("first",Context.MODE_APPEND);
		// if (!sp.getString("first", "").equals("true")) {
		// Intent intent = new Intent(StartActivity.this, SwitchActivity.class);
		// startActivity(intent);
		// return;
		// }
		setContentView(R.layout.logo);
		mContext = this;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.login_top_color);

		tvVersionCode = (TextView) findViewById(R.id.tv_version_code);
		tvVersionCode.setText(Constant.VERSION_CODE + "");

		ImageView iv = (ImageView) this.findViewById(R.id.logo_bg);
		AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
		aa.setDuration(3000);
		iv.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				animFinish = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				Log.d("onainima", "---------onAnimationStart...");
				if (MWDUtils.isSdPresent()) {// 安装了SD卡
					File file = new File(Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ File.separator + "YuYi");
					if (!file.exists()) {
						try {
							file.mkdirs();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if (Constant.users != null
						&& Constant.users.getPassword() != null
						&& !Constant.users.getPassword().equals("")) {
					Toast.makeText(mContext, "正在加载数据...", Toast.LENGTH_LONG)
							.show();
					Constant.token = null;
					login();
				} else {
					// 默认使用游客账号登录
					// guestLogin();
					dataFinish = true;
				}
				new Thread() {
					public void run() {
						while (true) {
							if (animFinish && dataFinish)
								break;
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						Log.d("FacadeThread", "animFinish: " + animFinish
								+ " dataFinish: " + dataFinish);
						if (loginSuccess) {
							Intent intent = new Intent(mContext,
									MenuActivity.class);
							startActivity(intent);
							finish();
						} else {
							if (response == null) {
								Intent intent = new Intent(mContext,
										LoginActivity.class);
								startActivity(intent);
								finish();
								return;
							}
							if (response.getRet() == 9) {
								updateHandler.sendEmptyMessage(0);
								// Toast.makeText(mContext, "正在后台进行下载，稍后会自动安装",
								// Toast.LENGTH_SHORT).show();
							} else {
								failHandler.sendEmptyMessage(0);
							}
						}
					}
				}.start();
				// }
			}

		});

		// MobclickAgent.updateOnlineConfig(mContext);SDK5.6.1及以上版本中，setOnlineConfigureListener()这个接口已经废弃，如果使用在线参数功能，需下载独立的在线参数SDK
		MobclickAgent.openActivityDurationTrack(false);
		// PushAgent mPushAgent = PushAgent.getInstance(mContext);
		// mPushAgent.enable();
		// PushAgent.getInstance(mContext).onAppStart();
		// String device_token = UmengRegistrar.getRegistrationId(mContext);
		// System.out.println("------------------------------: " +
		// device_token);
		initAuth();
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
		MobclickAgent.onPageStart("StartActivity"); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写)
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("StartActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）
		MobclickAgent.onPause(this);
	}

	private void initAuth() {
		UMShareAPI mShareAPI = UMShareAPI.get(getApplicationContext());
		// mShareAPI.doOauthVerify(this, SHARE_MEDIA.SINA, umAuthListener);
		// mShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, umAuthListener);
		// mShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN_CIRCLE,
		// umAuthListener);
		// mShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
		// mShareAPI.doOauthVerify(this, SHARE_MEDIA.QZONE, umAuthListener);
	}

	private UMAuthListener umAuthListener = new UMAuthListener() {
		@Override
		public void onComplete(SHARE_MEDIA platform, int action,
				Map<String, String> data) {
			Toast.makeText(getApplicationContext(), "Authorize succeed",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t) {
			Toast.makeText(getApplicationContext(), "Authorize fail",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action) {
			Toast.makeText(getApplicationContext(), "Authorize cancel",
					Toast.LENGTH_SHORT).show();
		}
	};

	private void login() {
		if (Constant.ISDEBUG)
			Log.d("", "login...");
		if (MWDUtils.isNetworkConnected(mContext)) {
			Constant.sessionId = null;
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("mobile", Constant.users
					.getUsername()));
			params.add(new BasicNameValuePair("password", Constant.users
					.getPassword()));
			params.add(new BasicNameValuePair("type", "3"));
			rThread = new RequestThread(params, "http", "post",
					Constant.LOGIN_URL, loginHandler);
			rThread.start();
		} else {
			dataFinish = true;
			loginSuccess = false;
		}
	}

	private void guestLogin() {
		if (MWDUtils.isNetworkConnected(mContext)) {
			Constant.sessionId = null;
			RequestParams params = new RequestParams();
			params.addBodyParameter("mobile", "13311036301");
			params.addBodyParameter("password", "baiyuguest");
			XThread thread = new XThread(StartActivity.this, 0, params,
					Constant.LOGIN_URL, guestHandler);
			thread.start();
		} else {
			dataFinish = true;
			loginSuccess = false;
		}
	}

	private Handler guestHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				try {
					response = DataParser.parseUserInfo((String) msg.obj);
					int ret = response.getRet();
					if (ret == 1) {
						Users user = new Users();
						user.setUsername(LoginActivity.GUEST_NAME);
						user.setPassword(LoginActivity.GUEST_PWD);
						Constant.users = user;
						loginSuccess = true;
					}
				} catch (Exception e) {
				}
				break;
			case -1:
				// Toast.makeText(mContext, msg.obj.toString(),
				// Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			dataFinish = true;
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
			if (msg.what == -1) {
				dataFinish = true;
				return;
			} else {
				String str = (String) msg.obj;
				if (str != null && !str.equals("")) {
					try {
						response = DataParser.parseUserInfo(str);
						int ret = response.getRet();
						if (ret == 1) {
							loginSuccess = true;
							System.out.println("loginSuccess...");
							// Constant.userinfo = (UserInfo) response.getObj();
						}
						dataFinish = true;
						return;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						dataFinish = true;
						return;
					}
				} else {
					dataFinish = true;
					return;
				}
			}
			// Intent i = new Intent(mContext, MainPageActivity.class);
			// startActivity(i);
			// finish();
		}
	};

	public Handler failHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			// Toast.makeText(mContext, "", Toast.LENGTH_SHORT)
			// .show();
			Intent it = new Intent(StartActivity.this, LoginActivity.class);
			startActivity(it);
			finish();
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
						// Intent intent = new Intent(StartActivity.this,
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (rThread != null)
			rThread.cannelRequest();
		if (mThread != null)
			mThread.cannelRequest();
	}

	// added by Jack for handle exception
	// "Bitmap too large to be uploaded into a texture".
	public boolean isNeedCloseHardwareAcceleration(int w, int h) {
		int[] maxSize = new int[1];
		GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSize, 0);

		if (maxSize[0] < h || maxSize[0] < w) {
			return true;
		}

		return false;
	}
}