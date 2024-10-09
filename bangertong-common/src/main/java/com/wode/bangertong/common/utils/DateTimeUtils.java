package com.wode.bangertong.common.utils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateTimeUtils {
	// 申请验证码默认间隔1分钟
	public static final int REQUEST_INTERVAL = 60 * 1000;

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static final int MARKET_OPEN_HOUR = 9;

	public static final int MARKET_CLOSE_HOUR = 15;

	public static final SimpleDateFormat PUSH_MESSAGE_DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm");

	public static final Timestamp getCurrentTimestamp() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.set(Calendar.MILLISECOND, 0);

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 获取年份
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return
	 */
	public static final int getYear(Timestamp timestamp) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(timestamp);
		return cal.get(Calendar.YEAR);
	}

	public static final Date getCurrentDate() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		cal.set(Calendar.MILLISECOND, 0);

		return new Date(cal.getTimeInMillis());
	}

	public static Timestamp calculateTimestamp(Date date, Time time) {
		Calendar cal_time = Calendar.getInstance(TimeZone.getDefault());
		cal_time.setTime(time);
		int hour = cal_time.get(Calendar.HOUR_OF_DAY);
		int min = cal_time.get(Calendar.MINUTE);
		int sec = cal_time.get(Calendar.SECOND);

		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * HH:MM:SS
	 */
	public static final Time parseTime(String time) {
		if (time != null && time.charAt(0) == 'T') {
			time = time.substring(1);
		}

		return Time.valueOf(time);
	}

	/**
	 * 判断是否是周末
	 * 
	 * @param date
	 * @return
	 */
	public static final boolean isWeekend(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(date);

		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
			return true;
		}

		return false;
	}

	public static final boolean isWeekend(java.util.Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(date);

		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
			return true;
		}

		return false;
	}

	public static Date calculateDate(Date date, int day) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(date);

		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + (day));

		return new Date(cal.getTimeInMillis());
	}

	public static Date getNextWorkDate(Date date) {
		Date today = DateTimeUtils.calculateDate(date, 1);
		while (DateTimeUtils.isWeekend(today)) {
			today = DateTimeUtils.calculateDate(today, 1);
		}
		return today;
	}

	/**
	 * 解析日期
	 * 
	 * @param date
	 *            日期格式yyyy-MM-dd
	 * @return
	 */
	public static final Date parseDate(String date) {
		return Date.valueOf(date);
	}

	public static final int getHour(Timestamp time) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(time);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static final int getMinute(Timestamp time) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(time);
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 从timestamp中获取日期，将时分秒置0
	 * 
	 * @return
	 */
	public static final Date getDate(Timestamp timestamp) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(timestamp);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 从timestamp中获取日期，将时分秒置0
	 * @param pattern
	 * @return
	 */
	public static final String getDate(Timestamp timestamp, String pattern) {
		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(timestamp);

		return getDateStr(new Date(cal.getTimeInMillis()),pattern);
	}

	/**
	 * 判断是否是法定假日
	 * 
	 * @param date
	 *            日期
	 * @param holidays
	 *            假期列表
	 * @return
	 */
	public static boolean isHoliday(Date date, String holidays) {
		String day = DATE_FORMAT.format(date);

		return holidays.indexOf(day) >= 0;
	}

	public static boolean isHoliday(java.util.Date date, String holidays) {
		String day = DATE_FORMAT.format(date);

		return holidays.indexOf(day) >= 0;
	}

	public static boolean isVoteTime(Timestamp now) {
		int hour = getHour(now);
		int minute = getMinute(now);
		boolean isVoteTime = true;
		if (hour > MARKET_OPEN_HOUR && hour < MARKET_CLOSE_HOUR) {
			isVoteTime = false;
		} else if (hour == MARKET_OPEN_HOUR && minute >= 30) {
			isVoteTime = false;
		} else if (hour == MARKET_CLOSE_HOUR && minute <= 30) {
			isVoteTime = false;
		}
		return isVoteTime;
	}

	/**
	 * 解析时间戳字符串
	 * 
	 * @param timestamp
	 *            要解析的时间戳
	 * @return
	 */
	public static final Timestamp parseTimestamp(String timestamp) {
		return Timestamp.valueOf(timestamp);
	}

	/**
	 * 通过字符串转时间
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static final java.util.Date getDate(String dateStr, String pattern) {
		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 通过时间转字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static final String getDateStr(java.util.Date date, String pattern) {
		if(date == null) return null;

		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		Format format = new SimpleDateFormat(pattern);
		String dateStr = "";
		dateStr = format.format(date);

		return dateStr;
	}

	public static final Timestamp getExpireMonth() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.set(14, 0);
		cal.add(2, 1);
		return new Timestamp(cal.getTimeInMillis());
	}

	public static final Timestamp getExpireMonth(int month) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.set(14, 0);
		cal.add(2, month);
		return new Timestamp(cal.getTimeInMillis());
	}

	public static final Timestamp getExpireYear() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.set(14, 0);
		cal.add(1, 1);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 时间戳转换成日期格式字符串
	 * 
	 * @param seconds
	 *            精确到秒的字符串
	 * @param format
	 * @return
	 */
	public static String timeStamp2Date(String seconds, String format) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		if (format == null || format.isEmpty())
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds)));
	}

	/**
	 * 无符号时间转换有符号时间 (有毫秒)
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static String formatDate(String dateString) throws ParseException {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		dateString = dateString.substring(0, dateString.length() - 3);
		java.util.Date date = df.parse(dateString);
		return date.toLocaleString();
	}

	/**
	 * 无符号时间转换有符号时间(入榜时间转换)
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static String formatDate1(String dateString) throws ParseException {
		StringBuffer dateStr = new StringBuffer().append(dateString.substring(0, 4)).append("-").append(dateString.substring(4, 6)).append("-").append(dateString.substring(6, 8));
		return dateStr.toString();
	}

	/**
	 * 无符号时间转换有符号时间(停止收益时间转换)
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static String formatDate2(String dateString) throws ParseException {
		try {
			String dateStr = dateString.substring(0, 11);
			return dateStr;
		} catch (Exception e) {
			return "0";
		}
	}

	/**
	 * 获取当月最后一天日期
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthLastDay(int year, int month){
		int days = 0;
		switch (month) {
			case 2:
				days = year % 400 == 0 || year % 4 == 0 && year % 100 != 0 ? 29 : 28;
				break;
			//闰年计算公式
			case 4:
				days = 30;
				break;
			case 6:
				days = 30;
				break;
			case 9:
				days = 30;
				break;
			case 11:
				days = 30;
				break;
			default:
				days = 31;
				break;
			//除30天的月份和2月份之外的月份为31天。
		}
		return days;
	}

}