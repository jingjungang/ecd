package com.ukang.clinic.utils;

import android.text.TextUtils;

public class Mobile_IDCardUtils {
	public Mobile_IDCardUtils() {

	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else if (mobiles.length() != 11)
			return false;
		else
			return mobiles.matches(telRegex);
	}

	/**
	 * 18位或者15位身份证验证 18位的最后一位可以是字母x
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isIDCard(String text) {
		String regx = "[0-9]{17}x";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		if (TextUtils.isEmpty(text)) {
			return false;
		} else if (text.length() != 15 && text.length() != 18) {
			return false;
		} else {
			return true;
		}
		// else {
		// Boolean b1 = text.matches(regx);
		// Boolean b2 = text.matches(reg1);
		// Boolean b3 = text.matches(regex);
		// return b1 || b2 || b3;
		// }

	}

	/**
	 * 验证邮箱格式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean emailValidation(String email) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return email.matches(regex);
	}
}
