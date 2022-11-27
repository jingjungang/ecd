package com.ukang.clinic.common;

import org.json.JSONException;
import org.json.JSONObject;

import com.ukang.clinic.entity.Response;
import com.ukang.clinic.entity.UserInfo;

public class DataParser {
	public static Response parseUserInfo(String paramString) {
		Response localResponse = new Response();
		localResponse.setRet(-1);
		try {
			JSONObject localJSONObject1 = new JSONObject(paramString);
			localResponse.setRet(localJSONObject1.getInt("status"));
			if (localResponse.getRet() == 1) {
				JSONObject localJSONObject2 = localJSONObject1
						.getJSONObject("into");
				UserInfo localUserInfo = new UserInfo();
				String str1 = localJSONObject2.getString("token");
				String str2 = localJSONObject2.getString("nickname");
				Constant.token = str1;
				localUserInfo.setMobile(localJSONObject2.getString("mobile"));
				localUserInfo.setEmail(MWDUtils.replaceNull(localJSONObject2
						.getString("email")));
				localUserInfo.setBirthday(MWDUtils.replaceNull(localJSONObject2
						.getString("birthday")));
				String str3 = MWDUtils.replaceNull(localJSONObject2
						.getString("sex"));
				if (str3 == null)
					str3 = "1";
				localUserInfo.setSex(str3);
				localUserInfo.setAvatar(MWDUtils.replaceNull(localJSONObject2
						.getString("avatar")));
				localUserInfo.setNickname(MWDUtils.replaceNull(str2));
				localUserInfo.setHospital(MWDUtils.replaceNull(localJSONObject2
						.getString("hospital")));
				localUserInfo.setSubject(MWDUtils.replaceNull(localJSONObject2
						.getString("subject")));
				localUserInfo.setJob(MWDUtils.replaceNull(localJSONObject2
						.getString("job")));
				localUserInfo.setEdu(MWDUtils.replaceNull(localJSONObject2
						.getString("edu")));
				Constant.userinfo = localUserInfo;
			}
			return localResponse;
		} catch (JSONException localJSONException) {
			localJSONException.printStackTrace();
		}
		return localResponse;
	}
}