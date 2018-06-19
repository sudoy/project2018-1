package com.abc.asms.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HTMLUtils {

	public static String formatLocalDate(LocalDate date) {
		String s = "";
		if(date == null) {
			return "";
		}
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		s = f.format(date);
		return s;
	}

	public static boolean judgeCheckbox(String[] param, String key) {
		if(param == null) {
			return true;
		}
		for(String p : param) {
			if(p.equals(key)) {
				return true;
			}
		}
		return false;
	}

	public static boolean judgeRatio(double toMonth, double lastMonth) {
		double ratio = toMonth / lastMonth;
		if(ratio >= 1) {
			return true;
		} else {
			return false;
		}
	}

	public static int calcSum(int unitPrice, int saleNumber) {
		return unitPrice * saleNumber;
	}

	public static double calcRatio(double toMonth, double lastMonth) {
		double ratio = toMonth / lastMonth;
		if(Double.isInfinite(ratio) || Double.isNaN(ratio)) {
			ratio = 0;
			return ratio;
		} else {
			return ratio;
		}
	}

	public static String formatMonth(String today) {
		String s = today.substring(5);
		if(s.indexOf("0") == 0) {
			return s.substring(1);
		}
		return s;
	}

	public static String deleteComma(String Numbers) {
		return Numbers.replaceAll(",","");
	}

	public static String formatAuthority(int authority) {
		if(authority == 0) {
			return "権限なし";
		}
		if(authority == 1) {
			return "売上登録";
		}
		if(authority == 10) {
			return "アカウント登録";
		}
		if(authority == 11) {
			return "売上登録/アカウント登録";
		}
		return "error";
	}

	public static String replaceSaleDate(String saleDate) {

		String s = "";
		if(saleDate == null) {
			return "";
		}
		s = saleDate.replace("/", "-");
		return s;
	}

}
