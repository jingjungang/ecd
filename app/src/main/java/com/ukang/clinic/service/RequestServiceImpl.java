package com.ukang.clinic.service;

import android.util.Log;

import com.ukang.clinic.common.Constant;
import com.ukang.clinic.utils.HttpUtil;
import com.ukang.clinic.utils.HttpsUtil;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

import java.util.List;

public class RequestServiceImpl implements RequestService {

	@Override
	public Object doHttpsPostRequest(HttpClient hc, List<NameValuePair> params,
			String url) {
		// TODO Auto-generated method stub
		String result = null;
		try {
			result = HttpsUtil.doHttpsPost2(hc, url, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Constant.ISDEBUG)
			Log.d("RequestServiceImpl", "result: " + result);
		return result;
	}

	@Override
	public Object doHttpsGetRequest(HttpClient hc, String url) {
		// TODO Auto-generated method stub
		String result = null;
		try {
			result = HttpsUtil.doHttpsGet(hc, url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Constant.ISDEBUG)
			Log.d("RequestServiceImpl", "result: " + result);
		return result;
	}

	@Override
	public Object doHttpPostRequest(HttpClient hc, List<NameValuePair> params,
			String url) {
		// TODO Auto-generated method stub
		String result = null;
		try {
			result = HttpUtil.doPost(hc, url, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Constant.ISDEBUG)
			Log.d("RequestServiceImpl", "result: " + result);
		System.out.println("result: " + result);
		return result;
	}

	@Override
	public Object doHttpGetRequest(HttpClient hc, String url) {
		// TODO Auto-generated method stub
		String result = null;
		try {
			result = HttpUtil.doGet(hc, url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Constant.ISDEBUG)
			Log.d("RequestServiceImpl", "result: " + result);
		return result;
	}

}
