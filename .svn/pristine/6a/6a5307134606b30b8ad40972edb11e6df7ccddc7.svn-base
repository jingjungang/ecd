package com.ukang.clinic.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtilities {
	public final static String FORMAT1 = "yyyy-MM-dd";
	public final static String FORMAT2 = "yyyy-MM-dd HH:mm:ss";

	public static String toStringWithFormat(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date toDateWithFormat(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date, new ParsePosition(0));
	}

	public static void main(String[] args) {
		// system.out.println(toStringWithFormat(new Date(), FORMAT2));
		// system.out.println(toDateWithFormat("2010-12-12 13:11:11", FORMAT2));
	}

	public static String getSystemDate() {
		Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		return (new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay))
				.toString();
	}

	public static String getSystemTime() {
		Calendar c = Calendar.getInstance();
		int mHour = c.get(Calendar.HOUR_OF_DAY);
		int mMinute = c.get(Calendar.MINUTE);
		return (new StringBuilder().append(
				(mHour) < 10 ? "0" + (mHour) : (mHour)).append(":")
				.append((mMinute) < 10 ? "0" + (mMinute) : (mMinute)))
				.toString();
	}

	public static String getSystemTime_second() {
		Calendar c = Calendar.getInstance();
		int mHour = c.get(Calendar.HOUR_OF_DAY);
		int mMinute = c.get(Calendar.MINUTE);
		int mSecond = c.get(Calendar.SECOND);
		return (new StringBuilder()
				.append((mHour) < 10 ? "0" + (mHour) : (mHour)).append(":")
				.append((mMinute) < 10 ? "0" + (mMinute) : (mMinute))
				.append(":").append((mSecond) < 10 ? "0" + (mSecond)
				: (mSecond))).toString();
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(FORMAT1).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat(FORMAT1).format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期字符串及指定格式的时间数字，long型
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static long gettime(String specifiedDay, String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 获得指定时间的前指定小时
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedTimeTBefore(String specifiedDay, int i) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(FORMAT2).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int time = c.get(Calendar.HOUR_OF_DAY);
		c.set(Calendar.HOUR_OF_DAY, time - 1);

		String dayBefore = new SimpleDateFormat(FORMAT2).format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定时间的指定日期格式的指定小时、分钟、秒后的时间
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedTime(String specifiedDay, String format,
			int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + hour);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minute);
		c.set(Calendar.SECOND, c.get(Calendar.SECOND) + second);

		String dayAfter = new SimpleDateFormat(format).format(c.getTime());
		return dayAfter;
	}

	/**
	 * 判断指定时间是否与当前时间相差指定时间,当前值支持小时和分钟差值
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static boolean isGTTime(String specifiedDay, String format,
			int field, int diff) {
		Calendar specifieCal = Calendar.getInstance();
		Calendar currCal = Calendar.getInstance();
		Date specifieDate = null;
		try {
			specifieDate = new SimpleDateFormat(format).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		specifieCal.setTime(specifieDate);
		if (field == Calendar.MINUTE) {
			String temp1 = new SimpleDateFormat("yyyyMMdd.HH")
					.format(specifieCal.getTime());
			String temp2 = new SimpleDateFormat("yyyyMMdd.HH").format(currCal
					.getTime());
			if (temp1.equals(temp2)) {
				int temp = currCal.get(Calendar.MINUTE)
						- specifieCal.get(Calendar.MINUTE);
				if (Math.abs(temp) >= Math.abs(diff))
					return true;
				else
					return false;
			} else
				return true;
		} else if (field == Calendar.HOUR_OF_DAY) {
			String temp1 = new SimpleDateFormat("yyyyMMdd").format(specifieCal
					.getTime());
			String temp2 = new SimpleDateFormat("yyyyMMdd").format(currCal
					.getTime());
			if (temp1.equals(temp2)) {
				int temp = currCal.get(Calendar.HOUR_OF_DAY)
						- specifieCal.get(Calendar.HOUR_OF_DAY);
				if (Math.abs(temp) >= Math.abs(diff))
					return true;
				else
					return false;
			} else
				return true;
		} else
			return true;
	}

	/**
	 * 当前日期加减n天后的日期，返回String (yyyy-mm-dd)
	 * 
	 * @param n
	 * @return
	 */
	public static String nDaysAftertoday(int n) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT1);
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.DAY_OF_MONTH, +n);
		return df.format(rightNow.getTime());
	}

	/**
	 * 当前日期加减n天后的日期，返回Date (yyyy-mm-dd)
	 * 
	 * @param n
	 * @return
	 */
	public static Date nDaysAfterNowDate(int n) {
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.DAY_OF_MONTH, +n);
		return rightNow.getTime();
	}

	/**
	 * 给定一个日期型字符串，返回加减n天后的日期型字符串
	 * 
	 * @param basicDate
	 * @param n
	 * @return
	 */
	public static String nDaysAfterOneDateString(String basicDate, int n) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT1);
		Date tmpDate = null;
		Calendar cal = Calendar.getInstance();
		try {
			tmpDate = df.parse(basicDate);
		} catch (Exception e) {
			// 日期型字符串格式错误
		}
		cal.setTime(tmpDate);
		cal.add(Calendar.DATE, n);
		return df.format(cal.getTime());
	}

	/**
	 * 给定一个日期，返回加减n天后的日期
	 * 
	 * @param basicDate
	 * @param n
	 * @return
	 */
	public static Date nDaysAfterOneDate(Date basicDate, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(basicDate);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * 计算两个日期相隔的天数
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static int nDaysBetweenTwoDate(Date firstDate, Date secondDate) {
		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	/**
	 * 计算两个日期相隔的天数
	 * 
	 * @param firstString
	 * @param secondString
	 * @return
	 */
	public static int nDaysBetweenTwoDate(String firstString,
			String secondString) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT1);
		Date firstDate = null;
		Date secondDate = null;
		try {
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		} catch (Exception e) {
			// 日期型字符串格式错误
		}

		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	/**
	 * 输入日期取星期几的方法
	 * 
	 * @param DateStr
	 * @return
	 */
	public static String getWeekDay(String DateStr) {
		SimpleDateFormat formatYMD = new SimpleDateFormat(FORMAT1);// formatYMD表示的是yyyy-MM-dd格式
		SimpleDateFormat formatD = new SimpleDateFormat("E");// "E"表示"day in week"
		Date d = null;
		String weekDay = "";
		try {
			d = formatYMD.parse(DateStr);// 将String 转换为符合格式的日期
			weekDay = formatD.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// //system.out.println("日期:"+DateStr+" ： "+weekDay);
		return weekDay;
	}

	/**
	 * 输入日期取星期几的方法
	 * 
	 * @param DateStr
	 * @return
	 */
	public static String getWeeks(String DateStr) {
		SimpleDateFormat formatYMD = new SimpleDateFormat(FORMAT1);// formatYMD表示的是yyyy-MM-dd格式
		SimpleDateFormat formatD = new SimpleDateFormat("w");// "E"表示"day in week"
		Date d = null;
		String weeks = "";
		try {
			d = formatYMD.parse(DateStr);// 将String 转换为符合格式的日期
			weeks = formatD.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// //system.out.println("日期:"+DateStr+" ： "+weekDay);
		return weeks;
	}

	/**
	 * 根据当前时间和输入小时计算时间
	 * 
	 * @param hour
	 * @return
	 */
	public static String getHours(int hour) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.HOUR_OF_DAY, hour);
		return df.format(rightNow.getTime());
	}

	public static String GLtime(String date) {
		// TODO Auto-generated method stub
		String[] da = date.split("-");
		date = Integer.parseInt(da[0]) + "-" + Integer.parseInt(da[1]) + "-"
				+ Integer.parseInt(da[2]);
		return date;
	}

	/**
	 * 将时间戳转为字符串
	 * 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTime(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/**
	 * 获取年龄
	 * 
	 * @return
	 */
	public static String GetAge(String age) {
		// TODO Auto-generated method stub
		String year = "";
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new Date();
		java.util.Date mydate;
		try {
			mydate = myFormatter.parse(age);
			long day = (date.getTime() - mydate.getTime())
					/ (24 * 60 * 60 * 1000) + 1;
			year = new java.text.DecimalFormat("#").format(day / 365f);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return year;
	}
}
