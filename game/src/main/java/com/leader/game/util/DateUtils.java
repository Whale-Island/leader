package com.leader.game.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	static final Logger log = LoggerFactory.getLogger(DateUtils.class);

	public static final String NULL = "未知", DROPED_YEAR = "年", DROPED_MONTH = "月", DROPED_DAY = "日", DROPED_SPLIT = ":",
			NULL_SPLIT = "", ZONE_SPLIT = "0", TIME_GMT = "GMT+8", SPRING = "春", SUMMER = "夏", AUTUMN = "秋",
			WINTER = "冬";

	/** 时间转换成毫秒数 */
	public static final int ONE_SECOND = 1000, ONE_MINUTE = 60 * ONE_SECOND, ONE_HOUR = 60 * ONE_MINUTE,
			ONE_DAY = 24 * ONE_HOUR;
	/**
	 * 缺省的日期显示格式： yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 北京时间(<b>GMT+8区</b>)标准日历对象 */
	private static Calendar calendarG8 = Calendar.getInstance(TimeZone.getTimeZone(TIME_GMT));

	/** 标准时间格式化 */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
	private static final SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 私有构造方法，禁止对该类进行实例化
	 */
	private DateUtils() {
	}

	/**
	 * 得到系统当前日期时间
	 * 
	 * @return 当前日期时间
	 */
	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 得到用缺省方式格式化的当前日期
	 * 
	 * @return 当前日期
	 */
	public static String getDate() {
		return getDateTime(DEFAULT_DATE_FORMAT);
	}

	/**
	 * 得到用缺省方式格式化的当前日期及时间
	 * 
	 * @return 当前日期及时间
	 */
	public static String getDateTime() {
		return getDateTime(DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 得到系统当前日期及时间，并用指定的方式格式化
	 * 
	 * @param pattern
	 *            显示格式
	 * @return 当前日期及时间
	 */
	public static String getDateTime(String pattern) {
		Date datetime = Calendar.getInstance().getTime();
		return getDateTime(datetime, pattern);
	}

	/**
	 * 得到用指定方式格式化的日期
	 * 
	 * @param date
	 *            需要进行格式化的日期
	 * @param pattern
	 *            显示格式
	 * @return 日期时间字符串
	 */
	public static String getDateTime(Date date, String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = DEFAULT_DATETIME_FORMAT;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/** 获取指定时间的Calendar,默认采用北京时间(GMT+8) */
	public static Calendar getTimeCalendar(long time) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_GMT));
		calendar.setTimeInMillis(time);
		return calendar;
	}

	/**
	 * 获取今日指定时间(GMT+8)
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millis
	 * @return
	 */
	public static long getTodayTime(int hour, int minute, int second, int millis) {
		Calendar cal = getTimeCalendar(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millis);
		return cal.getTimeInMillis();
	}

	/**
	 * 得到当前年份
	 * 
	 * @return 当前年份
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 得到当前月份
	 * 
	 * @return 当前月份
	 */
	public static int getCurrentMonth() {
		// 用get得到的月份数比实际的小1，需要加上
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到当前日
	 * 
	 * @return 当前日
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * 获取指定时间到现在的时间数（秒）
	 *
	 * @param time
	 * @return 秒
	 */
	public static int getDurationToNowSec(long time) {
		return (int) (getDurationToNow(time) / 1000);
	}

	/**
	 * 获取指定时间到现在的时间数（毫秒）
	 *
	 * @param time
	 * @return
	 */
	public static long getDurationToNow(long time) {
		return System.currentTimeMillis() - time;
	}

	/**
	 * 返回本月的最后一天
	 * 
	 * @return 本月最后一天的日期
	 */
	public static Date getMonthLastDay() {
		return getMonthLastDay(getNow());
	}

	/**
	 * 返回给定日期中的月份中的最后一天
	 * 
	 * @param date
	 *            基准日期
	 * @return 该月最后一天的日期
	 */
	public static Date getMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 将日期设置为下一月第一天
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);
		// 减去1天，得到的即本月的最后一天
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 获取指定年份最大的周数
	 * 
	 * @param year
	 * @return
	 */
	public static int getWeeksOfYear(int year) {
		Calendar cal = new GregorianCalendar();
		cal.set(year, 1, 1);
		return cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取月份的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取日期的年
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取日期的月
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取日期的日
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取日期的日
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取日期的时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR);
	}

	/**
	 * 获取日期的分种
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 获取日期的秒
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 获取星期几
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek - 1;
	}

	/**
	 * 获取哪一年共有多少周
	 * 
	 * @param year
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		return getWeekNumOfYear(c.getTime());
	}

	/**
	 * 取得某天是一年中的多少周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekNumOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/** 获取当前日志记录的标准<b>日期</b>,默认采用北京时间(GMT+8) */
	public static String getDateFormat() {
		return getDateTimesFormat(calendarG8).split(" ")[0].trim();
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @return 当前日期
	 */
	public static Date getDate(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.getTime();
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param long
	 *            timestamp 时间戳
	 * @return String 日期字符串
	 */
	public static String getFormatDate(long timestamp) {
		return getFormatDate(timestamp, DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param long
	 *            timestamp 时间戳
	 * @return String 日期字符串
	 */
	public static String getFormatDate(long timestamp, String dateFormat) {
		SimpleDateFormat sd = new SimpleDateFormat(dateFormat);
		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sd.format(new Date(timestamp));
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param String
	 *            date 需要转换的日期 yyyy-MM-dd HH:mm:ss
	 * @return long 时间戳
	 */
	public static long getFormatDate(String date) {
		return getFormatDate(date, DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param String
	 *            date 需要转换的日期 yyyy-MM-dd
	 * @return long 时间戳
	 */
	public static long getFormatDate(String date, String dateFormat) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		return timestamp;
	}

	/**
	 * 获取当前标准<b>日期</b>和<b>时间</b>,默认采用北京时间(GMT+8)
	 * 
	 * @return
	 */
	public static String getDateTimesFormat() {
		/** 北京时间(<b>GMT+8区</b>)标准日历对象 */
		Calendar calendarG8 = Calendar.getInstance(TimeZone.getTimeZone(TIME_GMT));
		calendarG8.setTimeInMillis(System.currentTimeMillis());
		return dateFormat.format(calendarG8.getTime()).trim();
	}

	/**
	 * 获取当前的时间(<b>yyMMdd<b>)默认采用北京时间(GMT+8)
	 * 
	 * @return
	 */
	public static String getDateTimesFormat1() {
		calendarG8.setTimeInMillis(System.currentTimeMillis());
		return dateFormat1.format(calendarG8.getTime()).trim();
	}

	/**
	 * 获取标准<b>日期</b>和<b>时间</b>,默认采用北京时间(GMT+8)
	 * 
	 * @param calendarG8
	 * @return
	 */
	public static String getDateTimesFormat(Calendar calendarG8) {
		return dateFormat.format(calendarG8.getTime()).trim();
	}

	/**
	 * 取得某天所在周的第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}

	/**
	 * 取得某天所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		return c.getTime();
	}

	/**
	 * 取得某年某周的第一天 对于交叉:2008-12-29到2009-01-04属于2008年的最后一周,2009-01-05为2009年第一周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar calFirst = Calendar.getInstance();
		calFirst.set(year, 0, 7);
		Date firstDate = getFirstDayOfWeek(calFirst.getTime());

		Calendar firstDateCal = Calendar.getInstance();
		firstDateCal.setTime(firstDate);

		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week - 1) * 7);
		firstDate = getFirstDayOfWeek(cal.getTime());

		return firstDate;
	}

	/**
	 * 取得某年某周的最后一天 对于交叉:2008-12-29到2009-01-04属于2008年的最后一周, 2009-01-04为
	 * 2008年最后一周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar calLast = Calendar.getInstance();
		calLast.set(year, 0, 7);
		Date firstDate = getLastDayOfWeek(calLast.getTime());

		Calendar firstDateCal = Calendar.getInstance();
		firstDateCal.setTime(firstDate);

		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week - 1) * 7);
		Date lastDate = getLastDayOfWeek(cal.getTime());

		return lastDate;
	}

	/** 获得当前系统时间几日后凌晨的时间 返回(毫秒) day=0表示当日凌晨,默认采用北京时间(GMT+8) */
	public static long getDaysNightTime(int day) {
		return getDaysNightTime(System.currentTimeMillis(), day);
	}

	/** 获得指定时间的几日后凌晨的时间 返回(毫秒) day=0表示指定时间那天的凌晨,默认采用北京时间(GMT+8) */
	public static long getDaysNightTime(long time, int day) {
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(time);
		currentDate.setTimeZone(TimeZone.getTimeZone(TIME_GMT));
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		currentDate.add(Calendar.DATE, day);
		return currentDate.getTimeInMillis();
	}

	/** 获取指定时间是星期几<b>(1-7 分别表示：星期日--星期六)<b>（默认采用北京时间(GMT+8)） */
	public static int displayDayOfWeek(long time) {
		return getTimeCalendar(time).get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 取得当前日期以后若干天的日期。如果要得到以前的日期，参数用负数。 例如要得到上星期同一天的日期，参数则为-7
	 * 
	 * @param days
	 *            增加的日期数
	 * @return 增加以后的日期
	 */
	public static Date addDays(int days) {
		return add(getNow(), days, Calendar.DATE);
	}

	/**
	 * 取得指定日期以后若干天的日期。如果要得到以前的日期，参数用负数。
	 * 
	 * @param date
	 *            基准日期
	 * @param days
	 *            增加的日期数
	 * @return 增加以后的日期
	 */
	public static Date addDays(Date date, int days) {
		return add(date, days, Calendar.DATE);
	}

	/**
	 * 取得当前日期以后某月的日期。如果要得到以前月份的日期，参数用负数。
	 * 
	 * @param months
	 *            增加的月份数
	 * @return 增加以后的日期
	 */
	public static Date addMonths(int months) {
		return add(getNow(), months, Calendar.MONTH);
	}

	/**
	 * 取得指定日期以后某月的日期。如果要得到以前月份的日期，参数用负数。 注意，可能不是同一日子，例如2003-1-31加上一个月是2003-2-28
	 * 
	 * @param date
	 *            基准日期
	 * @param months
	 *            增加的月份数
	 * 
	 * @return 增加以后的日期
	 */
	public static Date addMonths(Date date, int months) {
		return add(date, months, Calendar.MONTH);
	}

	/**
	 * 增加周
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addWeeks(Date date, int amount) {
		return add(date, amount, Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 增加时
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, amount, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 增加分
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMinutes(Date date, int amount) {
		return add(date, amount, Calendar.MINUTE);
	}

	/**
	 * 增加秒
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, amount, Calendar.SECOND);
	}

	/**
	 * 增加毫秒
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return add(date, amount, Calendar.MILLISECOND);
	}

	/**
	 * 内部方法。为指定日期增加相应的天数或月数
	 * 
	 * @param date
	 *            基准日期
	 * @param amount
	 *            增加的数量
	 * @param field
	 *            增加的单位，年，月或者日
	 * @return 增加以后的日期
	 */
	private static Date add(Date date, int amount, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 当前日期前几天或者后几天的日期
	 * 
	 * @param n
	 * @return
	 */
	public static Date afterNDay(int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		return d2;
	}

	/**
	 * 当前日期前几天或者后几天的日期
	 * 
	 * @param time
	 * @param n
	 * @return
	 */
	public static Date afterNDays(long time, int n) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		return d2;
	}

	/***
	 * 计算两个日期之间的间隔的天数,前者小于后者时返回负数
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static int intervalDays(long one, long two) {
		return intervalDays(getDate(one), getDate(two));
	}

	/***
	 * 计算两个日期之间的间隔的天数,前者小于后者时返回负数
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static int intervalDays(Date one, Date two) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(one);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(two);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) { // 不同年
			int timeDistance = 0;
			int interval = year1 - year2;
			if (interval > 0) {
				for (int i = year2; i < year1; i++) {
					if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
						timeDistance += 366;
					else
						timeDistance += 365;
				}
				return timeDistance + (day1 - day2);
			} else {
				for (int i = year1; i < year2; i++) {
					if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
						timeDistance += 366;
					else
						timeDistance += 365;
				}
				return (day1 - day2) - timeDistance;
			}
		} else
			return day1 - day2;
	}

	/***
	 * 计算两个日期之间的间隔的年数
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static int intervalYears(Date one, Date two) {
		return getYear(one) - getYear(two);
	}

	/**
	 * 判断两个时间是否在同一天
	 *
	 * @param time
	 * @param time2
	 * @return
	 */
	public static boolean isSameDay(long time, long time2) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		int day1 = instance.get(Calendar.DAY_OF_YEAR);
		int year1 = instance.get(Calendar.YEAR);
		instance.setTimeInMillis(time2);
		int day2 = instance.get(Calendar.DAY_OF_YEAR);
		int year2 = instance.get(Calendar.YEAR);

		return day1 == day2 && year1 == year2;
	}

	/**
	 * 判断两个时间是否在同一周
	 *
	 * @param time
	 * @param time2
	 * @return
	 */
	public static boolean isSameWeeks(long time, long time2) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		int week1 = instance.get(Calendar.WEEK_OF_YEAR);
		int year1 = instance.get(Calendar.YEAR);
		instance.setTimeInMillis(time2);
		int week2 = instance.get(Calendar.WEEK_OF_YEAR);
		int year2 = instance.get(Calendar.YEAR);

		return week1 == week2 && year1 == year2;
	}

	/**
	 * 判断两个时间是否在同一周(不判断是否同月同年)
	 *
	 * @param time
	 * @param time2
	 * @return
	 */
	public static boolean isSameWeeksOfMonth(long time, long time2) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		int week1 = instance.get(Calendar.WEEK_OF_MONTH);
		int year1 = instance.get(Calendar.YEAR);
		instance.setTimeInMillis(time2);
		int week2 = instance.get(Calendar.WEEK_OF_MONTH);
		int year2 = instance.get(Calendar.YEAR);

		return week1 == week2 && year1 == year2;
	}

	/**
	 * 是否在同一个月
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isInSameMonth(long time1, long time2) {
		GregorianCalendar cd1 = new GregorianCalendar();
		cd1.setTimeInMillis(time1);
		cd1.setTimeZone(TimeZone.getDefault());
		int month1 = cd1.get(Calendar.MONTH);
		cd1.setTimeInMillis(time2);
		int month2 = cd1.get(Calendar.MONTH);
		return month1 == month2;
	}

	/** 判断两个时间是否是同年同月同天同时,默认采用北京时间(GMT+8) */
	public static boolean isSameHourOfMonthYear(long oldTime, long currentTime) {
		Calendar old = getTimeCalendar(oldTime);
		Calendar now = getTimeCalendar(currentTime);
		if (old.get(Calendar.HOUR_OF_DAY) == now.get(Calendar.HOUR_OF_DAY)
				&& old.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)
				&& old.get(Calendar.MONTH) == now.get(Calendar.MONTH)
				&& old.get(Calendar.YEAR) == now.get(Calendar.YEAR))
			return true;
		return false;
	}

	/** 判指定时间是否是指定的某小时某分,默认采用北京时间(GMT+8) */
	public static boolean isHourMinuteOfDay(long time, int hour, int minute) {
		Calendar c = getTimeCalendar(time);
		int h = c.get(Calendar.HOUR_OF_DAY);
		if (h != hour)
			return false;
		int m = c.get(Calendar.MINUTE);
		if (m != minute)
			return false;
		return true;
	}

	/** 判指定时间是否过了指定的某小时某分,默认采用北京时间(GMT+8) */
	public static boolean isOverHourMinuteOfDay(long time, int hour, int minute) {
		Calendar c = getTimeCalendar(time);
		int h = c.get(Calendar.HOUR_OF_DAY);
		if (h < hour)
			return false;
		int m = c.get(Calendar.MINUTE);
		if (h == hour && m < minute)
			return false;
		return true;
	}

	/** 判指定时间是否在指定的小时分钟内,默认采用北京时间(GMT+8) */
	public static boolean isBetweenHourMinute(long time, int hour1, int minute1, int hour2, int minute2) {
		Calendar c = getTimeCalendar(time);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if (hour < hour1 || hour > hour2)
			return false;
		int minute = c.get(Calendar.MINUTE);
		if (hour == hour1 && minute < minute1)
			return false;
		if (hour == hour2 && minute >= minute2)
			return false;
		return true;
	}

	/***
	 * 是否间隔n天
	 * 
	 * @param time1
	 * @param time2
	 * @param num
	 * @return
	 */
	public static boolean isIntervalDays(long time1, long time2, int n) {
		return isIntervalDays(getDate(time1), getDate(time2), n);
	}

	/***
	 * 是否间隔n天
	 * 
	 * @param time1
	 * @param time2
	 * @param num
	 * @return
	 */
	public static boolean isIntervalDays(Date time1, Date time2, int n) {
		return intervalDays(time1, time2) == n;
	}

	/**
	 * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
	 * 
	 * @param one
	 *            第一个日期数，作为基准
	 * @param two
	 *            第二个日期数，作为比较
	 * @return 两个日期相差天数
	 */
	public static long diffDays(Date one, Date two) {
		return (one.getTime() - two.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 计算两个日期相差月份数 如果前一个日期小于后一个日期，则返回负数
	 * 
	 * @param one
	 *            第一个日期数，作为基准
	 * @param two
	 *            第二个日期数，作为比较
	 * @return 两个日期相差月份数
	 */
	public static int diffMonths(Date one, Date two) {
		Calendar calendar = Calendar.getInstance();
		// 得到第一个日期的年分和月份数
		calendar.setTime(one);
		int yearOne = calendar.get(Calendar.YEAR);
		int monthOne = calendar.get(Calendar.MONDAY);
		// 得到第二个日期的年份和月份
		calendar.setTime(two);
		int yearTwo = calendar.get(Calendar.YEAR);
		int monthTwo = calendar.get(Calendar.MONDAY);
		return (yearOne - yearTwo) * 12 + (monthOne - monthTwo);
	}

	/**
	 * 秒差
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static long diffSecond(Date one, Date two) {
		return (one.getTime() - two.getTime()) / 1000;
	}

	/**
	 * 与当前时间的分种差
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static int diffMinute(long time) {
		return (int) ((Calendar.getInstance().getTimeInMillis() - time) / 1000 / 60);
	}

	/**
	 * 分种差
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static int diffMinute(long one, long two) {
		return (int) ((one - two) / 1000 / 60);
	}

	/**
	 * 分种差
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static int diffMinute(Date one, Date two) {
		return (int) ((one.getTime() - two.getTime()) / 1000 / 60);
	}

	/**
	 * 时差
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static int diffHour(Date one, Date two) {
		return (int) ((one.getTime() - two.getTime()) / 1000 / 60 / 60);
	}

	/**
	 * 将一个字符串用给定的格式转换为日期类型。<br>
	 * 注意：如果返回null，则表示解析失败
	 * 
	 * @param datestr
	 *            需要解析的日期字符串
	 * @param pattern
	 *            日期字符串的格式，默认为“yyyy-MM-dd”的形式
	 * @return 解析后的日期
	 */
	public static Date parse(String datestr, String pattern) {
		Date date = null;
		if (null == pattern || "".equals(pattern)) {
			pattern = DEFAULT_DATE_FORMAT;
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			date = dateFormat.parse(datestr);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		return date;
	}

	public static void main(String[] args) {

		String test = "2021-1-1 10:22:22";
		String test2 = "2019-12-30 10:22:22";
		Date date;
		Date date2;
		try {
			date = parse(test, DEFAULT_DATETIME_FORMAT);
			date2 = parse(test2, DEFAULT_DATETIME_FORMAT);
			System.out.println("得到当前日期 － getDate():" + DateUtils.getDate());
			System.out.println("得到当前日期时间 － getDateTime():" + DateUtils.getDateTime());

			System.out.println("得到当前年份 － getCurrentYear():" + DateUtils.getCurrentYear());
			System.out.println("得到当前月份 － getCurrentMonth():" + DateUtils.getCurrentMonth());
			System.out.println("得到当前日子 － getCurrentDay():" + DateUtils.getCurrentDay());

			System.out.println("解析 － parse(" + test + "):" + getDateTime(date, "yyyy-MM-dd"));
			System.out.println("得到" + test + "所在月份的最后一天:" + getDateTime(getMonthLastDay(date), "yyyy-MM-dd"));

			System.out.println("自增月份 － addMonths(3):" + getDateTime(addMonths(3), "yyyy-MM-dd"));
			System.out.println("增加月份 － addMonths(" + test + ",3):" + getDateTime(addMonths(date, 3), "yyyy-MM-dd"));
			System.out.println("增加日期 － addDays(" + test + ",3):" + getDateTime(addDays(date, 3), "yyyy-MM-dd"));
			System.out.println("自增日期 － addDays(3):" + getDateTime(addDays(3), "yyyy-MM-dd"));

			System.out.println("比较日期 － diffDays():" + DateUtils.diffDays(date, date2));
			System.out.println("比较月份 － diffMonths():" + DateUtils.diffMonths(date, date2));

			System.out.println("天数间隔 － intervalDays():" + DateUtils.intervalDays(date, date2));
			System.out.println("年数间隔 － intervalYears():" + DateUtils.intervalYears(date, date2));

			System.out.println("获取天数 － getDay():" + DateUtils.getDay(DateUtils.getNow()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
