package com.ukang.clinic.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * viewpager的數據
 * 
 * @author Administrator
 * 
 */
public class MyPagerAdapter extends PagerAdapter {

	int page = 0;
	List<GridView> gridViewList = new ArrayList<GridView>();
	JSONArray ja;

	public MyPagerAdapter(int page, JSONArray ja, List<GridView> al) {
		this.page = page;
		this.ja = ja;
		this.gridViewList = al;
	}

	@Override
	public int getCount() {
		return page;// 总页数
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		GridView view = gridViewList.get(position);
		container.addView(view, 0);
		return gridViewList.get(position);
	}
}
