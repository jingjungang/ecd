package com.ukang.clinic.main;

/**
 * 访视页底层
 * 景俊钢
 * 2016年6月8日 14:16:27
 */

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.adapter.MenuIndexAdapter;
import com.ukang.clinic.adapter.MenuIndexAdapter2;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.application.PagerObservered;
import com.ukang.clinic.db.DBAdapater;
import com.ukang.clinic.entity.MenuContents;
import com.ukang.clinic.systembartint.SystemBarTintManager;

public class MainActivity extends FragmentActivity implements
		View.OnClickListener {

	DBAdapater DB;
	ArrayList<MenuContents> DataList = new ArrayList<MenuContents>();
	@ViewInject(R.id._back)
	private ImageView img_back;

	@ViewInject(R.id._back1)
	private ImageView img_back1;
	// *******************************指示圆点
	@ViewInject(R.id.img1)
	private ImageView img_index1;

	@ViewInject(R.id.img2)
	private ImageView img_index2;

	@ViewInject(R.id.img3)
	private ImageView img_index3;

	@ViewInject(R.id.img4)
	private ImageView img_index4;

	@ViewInject(R.id.img5)
	private ImageView img_index5;
	// ********************************listview
	@ViewInject(R.id.lv1)
	private ListView lv1;

	@ViewInject(R.id.lv2)
	private ListView lv2;
	// *********************************line
	@ViewInject(R.id.tv_line1)
	private TextView tv_line1;

	@ViewInject(R.id.tv_line2)
	private TextView tv_line2;

	@ViewInject(R.id.tv_line3)
	private TextView tv_line3;

	@ViewInject(R.id.tv_line4)
	private TextView tv_line4;

	@ViewInject(R.id.tv_line5)
	private TextView tv_line5;

	// ***********************************button

	@ViewInject(R.id.fir_visit)
	private Button tv_fir_visit;

	@ViewInject(R.id.sec_visit)
	private Button tv_sec_visit;

	@ViewInject(R.id.three_visit)
	private Button tv_three_visit;

	@ViewInject(R.id.four_visit)
	private Button tv_four_visit;

	@ViewInject(R.id.end_visit)
	private Button tv_end_visit;

	@ViewInject(R.id.finish_visit)
	private Button tv_finish_visit;

	@ViewInject(R.id.six_visit)
	private Button btn_six;

	@ViewInject(R.id.seven_visit)
	public Button btn_seven;
	// **********************************二级菜单之第X次访视
	@ViewInject(R.id.tv_visit)
	private TextView tv_visit;

	// ******************************title LinearLayout

	@ViewInject(R.id.title1)
	private LinearLayout ll_title1;

	@ViewInject(R.id.title2)
	private LinearLayout ll_title2;

	// **********************************是否可以进入访视录入
	public Boolean clickable_1 = true, clickable_2 = true, clickable_3 = true,
			clickable_4 = true, clickable_5 = true, clickable_6 = true,
			clickable_7 = true, clickable_8 = true;

	int mVisit = 1;
	public MWDApplication ma;
	private static SystemBarTintManager tintManager;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		this.ma = ((MWDApplication) getApplicationContext());
		this.ma.PageNotificationer = new PagerObservered();
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.title_bar);

		setListener();
		Intent i = getIntent();
		if (i != null) {
			String number = i.getStringExtra("number");
			String ischecked = i.getStringExtra("ischecked");
			String isbreak = i.getStringExtra("isbreak");
			String isout = i.getStringExtra("isout");
			setOutVisit(isout, number, ischecked);
			jugdeVisitCheck(number, ischecked, isbreak);
			// 判断实验完成按钮隐藏
			int n = Integer.valueOf(number);
			int c = Integer.valueOf(ischecked);
			int b = Integer.valueOf(isbreak);
			ma.visit_count_finish = n;
			ma.visit_count_ischecked = c;
			ma.visit_count_isbreak = b;
			// 计划内*实验完成：1.访视4完成；2.访视5完成 ;3.访视6
			if (!number.equals("7") && !number.equals("8")) {
				if (((n == 4 || n == 5) && c == 2) || n == 6) {
					tv_finish_visit.setVisibility(View.VISIBLE);
					tv_end_visit.setVisibility(View.GONE);
				} else {
					tv_finish_visit.setVisibility(View.GONE);
					tv_end_visit.setVisibility(View.VISIBLE);
				}
			}
		} else { // 可能网络慢 不做控制
			Toast.makeText(MainActivity.this, "网络不稳定，请刷新再试!",
					Toast.LENGTH_SHORT).show();
		}
		this.DB = ma.db;
		getDataFromLocalDB(0, 0, false, null);
		setMenu();
	}

	/**
	 * 设置访视最高级目录
	 */
	private void setOutVisit(String isout, String vnum, String ischecked) {
		// TODO Auto-generated method stub
		int vid = Integer.valueOf(vnum);
		// 若是计划外访视
		if (!TextUtils.isEmpty(isout) && isout.equals("1")) {
			ll_title1.setVisibility(View.GONE);
			ll_title2.setVisibility(View.VISIBLE);
			switch (vid) {
			case 6:
				// 实验完成 审核通过了
				ma.visit_count_finish = 7;
				ma.visit_count_ischecked = 0;
				vnum = "7";
				if (ischecked.equals("2")) {
					setColor_outvisit(btn_six);
				}
			case 7:
				setColor_outvisit(btn_six);
				break;
			case 8:
				setColor_outvisit(btn_seven);
				break;
			}
			mVisit = Integer.valueOf(vnum);
		}
		// 若是计划内访视
		else {
			switch (vid) {
			case 1:
				setColor(tv_fir_visit);
				break;
			case 2:
				setColor(tv_sec_visit);
				break;
			case 3:
				setColor(tv_three_visit);
				break;
			case 4:
				setColor(tv_four_visit);
				break;
			case 5:
				setColor(tv_end_visit);
				break;
			case 6:
				setColor(tv_finish_visit);
				break;
			// 当前处在计划外访视，当点击进入计划内访视时，默认在试验完成结果
			default:
				tv_end_visit.setVisibility(View.GONE);
				tv_finish_visit.setVisibility(View.VISIBLE);
				setColor(tv_finish_visit);
				mVisit = Integer.valueOf(6);
				break;
			}
		}
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		this.tv_fir_visit.setOnClickListener(this);
		this.tv_sec_visit.setOnClickListener(this);
		this.tv_three_visit.setOnClickListener(this);
		this.tv_four_visit.setOnClickListener(this);
		this.tv_end_visit.setOnClickListener(this);
		btn_six.setOnClickListener(this);
		btn_seven.setOnClickListener(this);
		tv_finish_visit.setOnClickListener(this);
		this.img_back.setOnClickListener(this);
		img_back1.setOnClickListener(this);
	}

	/**
	 * 设置菜单
	 */
	private void setMenu() {
		this.DataList = getDataFromLocalDB(0, 0, false, null);
		this.lv1.setAdapter(new MenuIndexAdapter(this, this.DataList, lv1));
		this.DataList = getDataFromLocalDB(1, 8, false, null);
		this.lv2.setAdapter(new MenuIndexAdapter2(this, this.DataList, lv2));
		if (ma.current_frg_index != -1) {
			refreshPage(ma.current_frg_index);
		} else {
			refreshPage(0);
		}
	}

	/**
	 * firInt获取第几级目录，secInt获取第二级目录索引，clickedBoolean是否是点击，clickedBoolean点击的项值
	 * 
	 * @param firInt
	 * @param secInt
	 * @param clickedBoolean
	 * @param paramMenuContents
	 * @return
	 */
	public ArrayList<MenuContents> getDataFromLocalDB(int firInt, int secInt,
			boolean clickedBoolean, MenuContents paramMenuContents) {
		Cursor localCursor = null;
		DataList = new ArrayList<MenuContents>();
		String str = "";
		switch (firInt) {
		case 0:
			str = "PARENTID = '7' and instr(visit,'" + this.mVisit + "')>0";
			break;
		case 1:
			str = "PARENTID = '" + secInt + "' and instr(visit,'" + this.mVisit // instr(str1,str2)
					+ "')>0";
			break;
		}
		this.DB.open();
		localCursor = this.DB.getEntry("by_index_menu", new String[] { "ID",
				"NAME", "PARENTID", "abbreviation", "newabbreviation",
				"fragmentindex", "fname", "son" }, str, null, null, null, null,
				null);
		int Count = localCursor.getCount();
		MenuContents localMenuContents;
		for (int i = 0; i < Count; i++) {
			localCursor.moveToPosition(i);
			localMenuContents = new MenuContents();
			localMenuContents.id = localCursor.getInt(0);
			localMenuContents.name = localCursor.getString(1);
			localMenuContents.PARENTID = localCursor.getString(2);
			localMenuContents.pic = localCursor.getString(3);
			localMenuContents.picq = localCursor.getString(4);
			localMenuContents.fragmentindex = localCursor.getInt(5);
			localMenuContents.fname = localCursor.getString(6);
			localMenuContents.son = localCursor.getInt(7);
			DataList.add(localMenuContents);
		}
		localCursor.close();
		DB.close();
		if ((clickedBoolean) && (paramMenuContents.son == 0 || firInt == 1)) {
			refreshPage(paramMenuContents.fragmentindex);
		}
		if (clickedBoolean && firInt == 1) {
			lv2.setAdapter(new MenuIndexAdapter2(this, this.DataList, lv2));
		}
		return DataList;
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id._back:
			finish();
			break;
		case R.id._back1:
			finish();
			break;
		case R.id.fir_visit:
			setColor(paramView);
			setMenu();
			break;
		case R.id.sec_visit:
			if (!clickable_2) {
				Toast.makeText(MainActivity.this, "第1次访视未完成",
						Toast.LENGTH_SHORT).show();
				break;
			}
			setColor(paramView);
			setMenu();
			break;
		case R.id.three_visit:
			if (!clickable_3) {
				Toast.makeText(MainActivity.this, "第2次访视未完成",
						Toast.LENGTH_SHORT).show();
				break;
			}
			setColor(paramView);
			setMenu();
			break;
		case R.id.four_visit:
			if (!clickable_4) {
				Toast.makeText(MainActivity.this, "第3次访视未完成",
						Toast.LENGTH_SHORT).show();
				break;
			}
			setColor(paramView);
			setMenu();
			break;
		case R.id.end_visit:
			setColor(paramView);
			setMenu();
			break;
		case R.id.finish_visit:
			if (!clickable_6) {
				Toast.makeText(MainActivity.this, "第4次访视未完成",
						Toast.LENGTH_SHORT).show();
				break;
			}
			setColor(paramView);
			setMenu();
			break;
		case R.id.six_visit:
			setColor_outvisit(paramView);
			setMenu();
			break;
		case R.id.seven_visit:
			if (!clickable_8) {
				Toast.makeText(MainActivity.this, "第5次访视未提交",
						Toast.LENGTH_SHORT).show();
				break;
			}
			setColor_outvisit(paramView);
			setMenu();
			break;
		}
	}

	public void refreshPage(int paramInt) {
		this.ma.PageIndex = paramInt;
		this.ma.PageNotificationer.notifition();
	}

	/**
	 * 设置计划外访视UI
	 * 
	 * @param paramView
	 */
	public void setColor_outvisit(View paramView) {
		this.img_index1.setVisibility(View.INVISIBLE);
		this.img_index2.setVisibility(View.INVISIBLE);
		this.btn_six.setBackground(getResources().getDrawable(
				R.drawable.vmenu_bg));
		this.btn_seven.setBackground(getResources().getDrawable(
				R.drawable.vmenu_bg));
		switch (paramView.getId()) {
		case R.id.six_visit:
			this.mVisit = 7;
			ma.nums = "7";
			this.tv_visit.setText("第5次访视");
			this.img_index1.setVisibility(View.INVISIBLE);
			this.btn_six.setBackground(getResources().getDrawable(
					R.drawable.corner_choose_item_stoken));
			tv_line5.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			break;
		case R.id.seven_visit:
			this.mVisit = 8;
			ma.nums = "8";
			this.tv_visit.setText("第6次访视");
			this.img_index2.setVisibility(View.INVISIBLE);
			this.btn_seven.setBackground(getResources().getDrawable(
					R.drawable.corner_choose_item_stoken));
			tv_line5.setBackgroundColor(getResources().getColor(R.color.title2));
			break;
		}
	}

	/**
	 * 设置计划内访视UI
	 * 
	 * @param paramView
	 */
	public void setColor(View paramView) {
		this.img_index1.setVisibility(View.INVISIBLE);
		this.img_index2.setVisibility(View.INVISIBLE);
		this.img_index3.setVisibility(View.INVISIBLE);
		this.img_index4.setVisibility(View.INVISIBLE);
		this.img_index5.setVisibility(View.INVISIBLE);
		this.tv_fir_visit.setBackground(getResources().getDrawable(
				R.drawable.vmenu_bg));
		this.tv_sec_visit.setBackground(getResources().getDrawable(
				R.drawable.vmenu_bg));
		this.tv_three_visit.setBackground(getResources().getDrawable(
				R.drawable.vmenu_bg));
		this.tv_four_visit.setBackground(getResources().getDrawable(
				R.drawable.vmenu_bg));
		switch (paramView.getId()) {
		case R.id.fir_visit:
			this.mVisit = 1;
			ma.nums = "1";
			this.tv_visit.setText("第1次访视");
			this.tv_line1.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			this.tv_line2.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			this.tv_line3.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			this.tv_line4.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			this.tv_fir_visit.setBackground(getResources().getDrawable(
					R.drawable.corner_choose_item_stoken));
			this.img_index1.setVisibility(View.VISIBLE);

			return;
		case R.id.sec_visit:
			this.mVisit = 2;
			ma.nums = "2";
			this.tv_visit.setText("第2次访视");
			this.tv_line1.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line2.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			this.tv_line3.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			this.tv_line4.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			this.tv_sec_visit.setBackground(getResources().getDrawable(
					R.drawable.corner_choose_item_stoken));
			this.img_index2.setVisibility(View.VISIBLE);
			return;
		case R.id.three_visit:
			this.mVisit = 3;
			ma.nums = "3";
			this.tv_visit.setText("第3次访视");
			this.tv_line1.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line2.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line3.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			this.tv_line4.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			this.img_index3.setVisibility(View.VISIBLE);
			this.tv_three_visit.setBackground(getResources().getDrawable(
					R.drawable.corner_choose_item_stoken));
			return;
		case R.id.four_visit:
			this.mVisit = 4;
			ma.nums = "4";
			this.tv_visit.setText("第4次访视");
			this.tv_line1.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line2.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line3.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line4.setBackgroundColor(getResources().getColor(
					R.color.gray_color));
			this.img_index4.setVisibility(View.VISIBLE);
			this.tv_four_visit.setBackground(getResources().getDrawable(
					R.drawable.corner_choose_item_stoken));
			return;
		case R.id.end_visit:
			ma.nums = "5";
			this.tv_visit.setText("中止访视");
			this.tv_line1.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line2.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line3.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line4.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.mVisit = 5;
			this.img_index5.setVisibility(View.VISIBLE);
			return;
		case R.id.finish_visit:
			ma.nums = "6";
			this.tv_visit.setText("实验完成");
			this.tv_line1.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line2.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line3.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.tv_line4.setBackgroundColor(getResources().getColor(
					R.color.title2));
			this.mVisit = 6;
			this.img_index5.setVisibility(View.VISIBLE);
			;
			return;
		}
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/**
	 * 判断访问次数的可点击性
	 * 
	 * @param number
	 *            当前访视次数 1、 2、 3、 4、5(中止)
	 * @param is_checked
	 *            审核状态 0未提交 1待审核 2审核通过 3审核未通过
	 * @param is_break
	 *            中止状态 1建议中止 2建议不中止 3中止
	 */
	public void jugdeVisitCheck(String number, String is_checked,
			String is_break) {
		int n = Integer.valueOf(number);
		int c = Integer.valueOf(is_checked);
		switch (n) {
		case 1:
			if (c == 2) {
				clickable_1 = true;
				clickable_2 = true;
				clickable_3 = false;
				clickable_4 = false;
				clickable_5 = true;
			} else {
				clickable_1 = true;
				clickable_2 = false;
				clickable_3 = false;
				clickable_4 = false;
				clickable_5 = true;
			}
			break;
		case 2:
			if (c == 2) {
				clickable_1 = true;
				clickable_2 = true;
				clickable_3 = true;
				clickable_4 = false;
				clickable_5 = true;
			} else {
				clickable_1 = true;
				clickable_2 = true;
				clickable_3 = false;
				clickable_4 = false;
				clickable_5 = true;
			}
			break;
		case 3:
			if (c == 2) {
				clickable_1 = true;
				clickable_2 = true;
				clickable_3 = true;
				clickable_4 = true;
				clickable_5 = true;
			} else {
				clickable_1 = true;
				clickable_2 = true;
				clickable_3 = true;
				clickable_4 = false;
				clickable_5 = true;
			}
			break;
		case 4:
			if (c == 2) {
				clickable_1 = true;
				clickable_2 = true;
				clickable_3 = true;
				clickable_4 = true;
				clickable_5 = true;
				clickable_6 = true;
			} else {
				clickable_1 = true;
				clickable_2 = true;
				clickable_3 = true;
				// if (c == 0 || (is_checked.equals("3") &&
				// is_break.equals("2"))) {
				// clickable_4 = true;
				// } else {
				// clickable_4 = false;
				// }
				clickable_4 = true;
				clickable_5 = true;
			}
			break;
		case 5:
			clickable_1 = true;
			clickable_2 = true;
			clickable_3 = true;
			clickable_4 = true;
			clickable_5 = true;
			break;
		case 6:
			clickable_1 = true;
			clickable_2 = true;
			clickable_3 = true;
			clickable_4 = true;
			clickable_6 = true;
			break;
		case 7:
			clickable_6 = true;
			break;
		}
	}

	/**
	 * 公共方法，供所有子fragment页面调用， 设置提交权限（ 选择访视阶段比当前阶段对比）
	 * 
	 * @param submit
	 */
	public void setSubmitVisibily(Button submit) {
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		Intent intent = new Intent();
		intent.setAction("pc");
		switch (requestCode) {
		case Activity.RESULT_FIRST_USER:
			intent.putExtra("num", "1");
			intent.putExtra("names", data.getStringArrayListExtra("name"));
			break;
		case 2:
			intent.putExtra("num", "2");
			break;
		}
		sendBroadcast(intent);
	}

	/**
	 * 设置隐藏菜单(访视首页调用)
	 * 
	 * @param boolean flag 是否隐藏
	 */
	public void setVisibilyMenv(boolean flag) {
		if (flag) {
			DataList = new ArrayList<MenuContents>();
			MenuContents localMenuContents = new MenuContents();
			localMenuContents.id = 8;
			localMenuContents.name = "访视首页";
			localMenuContents.PARENTID = "7";
			localMenuContents.pic = "fssy";
			localMenuContents.picq = "fssyq";
			localMenuContents.fragmentindex = 0;
			localMenuContents.fname = "example";
			localMenuContents.son = 0;
			DataList.add(localMenuContents);
			lv1.setAdapter(new MenuIndexAdapter(this, this.DataList, lv1));
			lv2.setAdapter(new MenuIndexAdapter2(this,
					new ArrayList<MenuContents>(), lv2));
		} else {
			this.DataList = getDataFromLocalDB(0, 0, false, null);
			this.lv1.setAdapter(new MenuIndexAdapter(this, this.DataList, lv1));
			this.DataList = getDataFromLocalDB(1, 8, false, null);
			this.lv2.setAdapter(new MenuIndexAdapter2(this, this.DataList, lv2));
		}
	}
}