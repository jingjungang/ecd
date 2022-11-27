package com.ukang.clinic.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.SecurityEncode;
import com.ukang.clinic.db.DBAdapater;
import com.ukang.clinic.entity.Users;

import dalvik.system.DexClassLoader;

public class MWDApplication extends Application {

	public static final String LLKCKIO = "KCKLKKCKCMM,C101203132KDMMO1233.DIDSIKLDZZZ'DKSKDLKL123982983,dksdljfkj.2kjkdk";
	private final String TAG = "MWDApplication";
	private String userName, userPwd;
	private boolean firstLoad;
	Boolean pushFlag;
	private static Context sContext;

	public DBAdapater db;
	public int PageIndex;
	public PagerObservered PageNotificationer;
	public String pid = ""; // 选择的病人ID
	public String is_add_patient = "1"; // 新增还是编辑病人简历 1 新增 2编辑
	public String nums = "1";// 第几次访视
	public int current_frg_index = -1;
	public int visit_count_finish = 1; // 当前访视次数 默认1
	public int visit_count_ischecked = 0; // 审核状态 0未提交 1待审核 2审核通过 3审核未通过
	public int visit_count_isbreak = 1; // 中止状态 1建议中止 2建议不中止 3中止

	public static Context getContext() {
		return sContext;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Boolean getPushState() {
		return pushFlag;
	}

	public void setPushState(Boolean pushFlag) {
		this.pushFlag = pushFlag;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		ReadUser();
		ReadPushState();
		ReadSetting();
		db = new DBAdapater(getApplicationContext());
		db.open();
		Cursor cursor = null;
		try {
			cursor = db
					.rawQuery(
							"select count(area_name) from by_area WHERE level =1",
							null);
			cursor.close();
		} catch (Exception e) {
			db.initTable();
		}
	}

	@SuppressLint("NewApi")
	private void dexTool() {

		File dexDir = new File(getFilesDir(), "dlibs");
		dexDir.mkdir();
		File dexFile = new File(dexDir, "libs.apk");
		File dexOpt = new File(dexDir, "opt");
		dexOpt.mkdir();
		try {
			InputStream ins = getAssets().open("libs.apk");
			if (dexFile.length() != ins.available()) {
				FileOutputStream fos = new FileOutputStream(dexFile);
				byte[] buf = new byte[4096];
				int l;
				while ((l = ins.read(buf)) != -1) {
					fos.write(buf, 0, l);
				}
				fos.close();
			}
			ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		ClassLoader cl = getClassLoader();
		ApplicationInfo ai = getApplicationInfo();
		String nativeLibraryDir = null;
		if (Build.VERSION.SDK_INT > 8) {
			nativeLibraryDir = ai.nativeLibraryDir;
		} else {
			nativeLibraryDir = "/data/data/" + ai.packageName + "/lib/";
		}
		DexClassLoader dcl = new DexClassLoader(dexFile.getAbsolutePath(),
				dexOpt.getAbsolutePath(), nativeLibraryDir, cl.getParent());

		try {
			Field f = ClassLoader.class.getDeclaredField("parent");
			f.setAccessible(true);
			f.set(cl, dcl);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	void ReadUser() {
		SharedPreferences user = getSharedPreferences("user_info", MODE_PRIVATE);
		userName = user.getString("username", null);
		userPwd = user.getString("password", null);
		if (Constant.ISDEBUG) {
			// Log.d("------", userName + " " + userPwd);
		}
		if (userName != null) {
			userName = SecurityEncode.decoderByDES(userName, LLKCKIO);
		}
		if (userPwd != null && !userPwd.equals(""))
			userPwd = SecurityEncode.decoderByDES(userPwd, LLKCKIO);
		if (userName != null) {
			Users users = new Users();
			users.setUsername(userName);
			users.setPassword(userPwd);
			Constant.users = users;
		}
	}

	public void WriteUser(String strName, String strPassword, boolean isSavePwd) {
		SharedPreferences user = getSharedPreferences("user_info", MODE_PRIVATE);
		SharedPreferences.Editor sharedata = user.edit();
		sharedata.putString("username",
				SecurityEncode.encoderByDES(strName, LLKCKIO));
		if (isSavePwd)
			sharedata.putString("password",
					SecurityEncode.encoderByDES(strPassword, LLKCKIO));
		else
			sharedata.putString("password", "");
		sharedata.commit();
	}

	void ReadPushState() {
		SharedPreferences push = getSharedPreferences("push_state",
				MODE_PRIVATE);
		pushFlag = push.getBoolean("push_flag", true);
		if (Constant.ISDEBUG)
			Log.d(TAG, "ReadPushState. pushFlag: " + pushFlag);
		Constant.pushFlag = pushFlag;
	}

	public void WritePush(boolean flag) {
		if (Constant.ISDEBUG)
			Log.d(TAG, "WritePush. pushFlag: " + flag);
		SharedPreferences user = getSharedPreferences("push_state",
				MODE_PRIVATE);
		SharedPreferences.Editor sharedata = user.edit();
		sharedata.putBoolean("push_flag", flag);
		sharedata.commit();
	}

	public void ReadSetting() {
		SharedPreferences pref = getSharedPreferences("setting_info",
				MODE_PRIVATE);
		firstLoad = pref.getBoolean("first_load", true);
		boolean newVersion = pref.getBoolean("new_version", true);
		Constant.firstLoad = firstLoad;
		String versionCode = pref.getString("version_code", "");
		if (Constant.VERSION_CODE.compareTo(versionCode) > 0) {
			newVersion = true;
		}
		Constant.isNewVersion = newVersion;
	}

	public void WriteSetting(boolean flag, boolean isNewVersion) {
		SharedPreferences pref = getSharedPreferences("setting_info",
				MODE_PRIVATE);
		SharedPreferences.Editor sharedata = pref.edit();
		sharedata.putBoolean("first_load", flag);
		sharedata.putBoolean("new_version", isNewVersion);
		sharedata.putString("version_code", Constant.VERSION_CODE);
		sharedata.commit();
	}


}
