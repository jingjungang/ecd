package com.ukang.clinic.patient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ukang.clinic.R;
import com.ukang.clinic.adapter.CardAdapter;
import com.ukang.clinic.adapter.MyPagerAdapter;
import com.ukang.clinic.application.MWDApplication;
import com.ukang.clinic.common.Constant;
import com.ukang.clinic.common.MWDUtils;
import com.ukang.clinic.thread.RequestThread;

public class PatientListFragment extends Fragment {

	protected static final String TAG = "PatientListFragment";
	@ViewInject(R.id.add_bl)
	private Button btn_add_bl;

	@ViewInject(R.id.base_info)
	private ImageView btn_base_info;

	@ViewInject(R.id.person_center)
	private LinearLayout _person_center;

	@ViewInject(R.id.pages)
	private TextView mpages;
	// 系统退出
	@ViewInject(R.id.mianui_system_exit)
	LinearLayout mianui_system_exit;

	@ViewInject(R.id.refresh)
	LinearLayout l_refresh;

	MWDApplication application;
	JSONArray mjson_array = null;
	public ProgressDialog dia;
	private RequestThread rThread;
	// 数据填充*****
	ViewPager viewPager;
	JSONArray listInfo; // 总数据
	int page;// viewpager的頁數
	public static int PER_SIZE = 8; // 每页个数
	public static int PER_Columns = 4; // 设置的列值
	Context context;

	@ViewInject(R.id.tv_p_center)
	TextView tv_p_center;

	// 当前页*****
	public int currentpage = 1;
	View root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.root = inflater.inflate(R.layout.fragment_patient_list, container,
				false);
		ViewUtils.inject(this, this.root);
		init();
		return this.root;
	}

	/**
	 * 初始化
	 */
	private void init() {
		context = getActivity();
		dia = ((MenuActivity) context).dia;
		dia.show();
		application = (MWDApplication) context.getApplicationContext();
		getDataByPost();
	}

	Handler mHandler = new Handler() {

		public void handleMessage(Message paramAnonymousMessage) {
			try {
				dia.dismiss();
				if (null != paramAnonymousMessage.obj) {
					String str = paramAnonymousMessage.obj.toString();
					JSONObject localJSONObject = new JSONObject(str);
					JSONArray localJSONArray = localJSONObject.getJSONObject(
							"data").getJSONArray("info");
					localJSONArray = Filter(localJSONArray,
							((MenuActivity) getActivity()).str_selection
									.toString());
					mjson_array = localJSONArray;
					if (localJSONObject.getString("status").toString()
							.equals("1")
							&& localJSONArray.length() > 0) {
						setData(localJSONArray);
					}
				}
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
		}
	};

	/**
	 * 过滤患者
	 * 
	 * @return
	 */
	private JSONArray Filter(JSONArray oldJsonArray, String filter) {
		JSONArray nowJSONArray = new JSONArray();
		JSONObject jo;
		String name = "", id = "", sex = "";
		int len = oldJsonArray.length();
		if (len > 0 && !TextUtils.isEmpty(filter)) {
			for (int i = 0; i < len; i++) {
				try {
					jo = oldJsonArray.getJSONObject(i);
					name = jo.getString("rname");
					id = jo.getString("medicationno");
					sex = jo.getString("sex").equals("1") ? "男" : "女";
					if (name.contains(filter) || id.contains(filter)
							|| sex.contains(filter)) {
						nowJSONArray.put(jo);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return nowJSONArray;
		} else {
			return oldJsonArray;
		}
	}

	private void getDataByPost() {
		if (MWDUtils.isNetworkConnected(context)) {
			dia.show();
			ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
			localArrayList.add(new BasicNameValuePair("page", "1"));
			localArrayList.add(new BasicNameValuePair("token", Constant.token));
			this.rThread = new RequestThread(localArrayList, "http", "post",
					Constant.GETUSER_URL, this.mHandler);
			this.rThread.start();
		} else {
			Toast.makeText(context, "网络错误,请检查网络后刷新", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 
	 * 裝載gridview的容器
	 */
	private void setData(JSONArray localJSONArray) {

		listInfo = new JSONArray();// 裝載所有顯示數據得容器
		listInfo = localJSONArray;
		int size = listInfo.length();// 获取所有数据的总长
		if (size == 0)
			return;
		int len = size / PER_SIZE;// 確定viewpager的頁數
		if (size % PER_SIZE == 0) {
			page = len;
		} else {
			page = len + 1;
		}
		mpages.setText("Page " + 1 + "/" + page);
		// 為viewpager裝配數據
		viewPager = (ViewPager) root.findViewById(R.id.viewpagerLayout);
		viewPager.setAdapter(new MyPagerAdapter(page, localJSONArray,
				getGridViewList()));
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int index) {
				currentpage = index + 1;
				mpages.setText("Page " + (index + 1) + "/" + page);
			}

			@Override
			public void onPageScrolled(int a, float b, int c) {

			}

			@Override
			public void onPageScrollStateChanged(int page) {
			}
		});
	}

	// viewpage所需要的界面
	private ArrayList<GridView> getGridViewList() {
		List<GridView> gridViewList; // 裝載gridview的容器
		gridViewList = new ArrayList<GridView>(); // 裝載gridview的容器
		for (int i = 0; i < page; i++) {
			LayoutInflater inflater = ((LayoutInflater) context
					.getSystemService("layout_inflater"));
			View v = inflater.inflate(R.layout.gridview, null, false);
			GridView gridView = (GridView) v.findViewById(R.id.grid);
			gridView.setNumColumns(PER_Columns);
			gridView.setAdapter(new CardAdapter(context, i, listInfo));
			gridViewList.add(gridView);
		}
		return (ArrayList<GridView>) gridViewList;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		getDataByPost() ;
		super.onResume();
	}
}
