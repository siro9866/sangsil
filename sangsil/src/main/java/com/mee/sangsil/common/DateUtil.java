package com.mee.sangsil.common;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/** The logger. */
	/** The page cUtil. */
	private static DateUtil cUtil = null;

	public static synchronized DateUtil getInstance() {

		if (cUtil == null) {
			cUtil = new DateUtil();
		}
		return cUtil;
	}

	/**
	 * 해당 format에 맞는 오늘 날짜 가져오기.
	 *
	 * @param yyyyMMdd
	 *
	 * @return the today
	 */
	public static String getToday(String format) {
		SimpleDateFormat formtter = new SimpleDateFormat(format);
		Date date = new Date();

		// return formtter.format(date);
		String today = formtter.format(date);

		return today;
	}

	/**
	 * 오늘 날짜의 해당연도 조회
	 *
	 * @return the year
	 */
	public String getThisYear() {
		Calendar cal = Calendar.getInstance();
		return Integer.toString(cal.get(Calendar.YEAR));
	}

	/**
	 * 날짜 더하기
	 *
	 * @param date
	 *            the date 기준이 되는 날짜
	 * @param format
	 *            the format SimpleDateFormat에 해당하는 날짜 형식 date 파라미터와 형식이 일치 해야
	 *            함. Ex) yyyy-MM-dd HH:mm:ss
	 * @param type
	 *            the type 더하는 날짜 Type (년도 : YEAR, 달 : MONTH, 일 : DAY) 대소문자 구분
	 *            안함
	 * @param value
	 *            the value 더할 값 + - int 값
	 *
	 * @return the string
	 */
	public String calcDate(String date, String format, String type, int value) {

		String dateStr = "";
		Calendar cal = null;
		cal = getCalendar(date, format);

		String typeValue = type.toUpperCase();

		if (typeValue.equals("YEAR")) {
			dateStr = addYear(cal, format, value);
		} else if (typeValue.equals("MONTH")) {
			dateStr = addMonth(cal, format, value);
		} else if (typeValue.equals("DAY")) {
			dateStr = addDay(cal, format, value);
		}

		return dateStr;
	}

	/**
	 * 년도 더하기
	 *
	 * @param cal
	 *            the cal
	 * @param format
	 *            the format
	 * @param year
	 *            the year
	 *
	 * @return the string
	 */
	public String addYear(Calendar cal, String format, int year) {

		cal.add(Calendar.YEAR, year);
		String returnString = setDateFormat(cal, format);

		return returnString;

	}

	/**
	 * 월 더하기
	 *
	 * @param cal
	 *            the cal
	 * @param format
	 *            the format
	 * @param month
	 *            the month
	 *
	 * @return the string
	 */
	public String addMonth(Calendar cal, String format, int month) {

		cal.add(Calendar.MONTH, month);
		String returnString = setDateFormat(cal, format);

		return returnString;

	}

	/**
	 * 날짜 더하기
	 *
	 * @param cal
	 *            the cal
	 * @param format
	 *            the format
	 * @param day
	 *            the day
	 *
	 * @return the string
	 */
	public String addDay(Calendar cal, String format, int day) {

		cal.add(Calendar.DATE, day);
		String returnString = setDateFormat(cal, format);

		return returnString;

	}

	/**
	 * 해당 Format에 해당하는 Calendar 객체 가져오기
	 *
	 * @param date
	 *            the date
	 * @param format
	 *            the format
	 *
	 * @return the calendar
	 */
	public Calendar getCalendar(String date, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = sdf.parse(date, new ParsePosition(0));
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		return cal;

	}

	/**
	 * 해당하는 날짜 Format에 대한 날짜 가져오기
	 *
	 * @param cal
	 *            the cal
	 * @param format
	 *            the format
	 *
	 * @return the string
	 */
	public String setDateFormat(Calendar cal, String format) {

		String dateStr = "";
		SimpleDateFormat dFormat = null;
		Date d = null;

		try {
			d = cal.getTime();
			dFormat = new SimpleDateFormat(format);
			dateStr = dFormat.format(d);
		} catch (Exception e) {
		}

		return dateStr;
	}

	/**
	 * 두날짜 사이의 일수를 구함
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public int getDiffDayCount(String fromDate, String toDate) {

		System.out.println("toDate:" + toDate + "	fromDate:" + fromDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			System.out.println("sdf.parse(toDate).getTime():" + sdf.parse(toDate).getTime()
					+ "	sdf.parse(fromDate).getTime():" + sdf.parse(fromDate).getTime());
			System.out.println("minus:" + (sdf.parse(toDate).getTime() - sdf.parse(fromDate).getTime()));
			System.out.println("minus/1000:" + (sdf.parse(toDate).getTime() - sdf.parse(fromDate).getTime()) / 1000);
			System.out.println(
					"minus/1000/60:" + (sdf.parse(toDate).getTime() - sdf.parse(fromDate).getTime()) / 1000 / 60);
			System.out.println("minus/1000/60/60:"
					+ (sdf.parse(toDate).getTime() - sdf.parse(fromDate).getTime()) / 1000 / 60 / 60);
			System.out.println("minus/1000/60/60/24:"
					+ (sdf.parse(toDate).getTime() - sdf.parse(fromDate).getTime()) / 1000 / 60 / 60 / 24);
			return (int) ((sdf.parse(toDate).getTime() - sdf.parse(fromDate).getTime()) / 1000 / 60 / 60 / 24);
		} catch (Exception e) {
			return 0;
		}
	}

}