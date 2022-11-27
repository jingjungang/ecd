package com.ukang.clinic.service;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

public abstract interface RequestService {
	public abstract Object doHttpGetRequest(HttpClient paramHttpClient,
			String paramString);

	public abstract Object doHttpPostRequest(HttpClient paramHttpClient,
			List<NameValuePair> paramList, String paramString);

	public abstract Object doHttpsGetRequest(HttpClient paramHttpClient,
			String paramString);

	public abstract Object doHttpsPostRequest(HttpClient paramHttpClient,
			List<NameValuePair> paramList, String paramString);
}