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

	public static long calcSum(long unitPrice, long saleNumber) {
		return unitPrice * saleNumber;
	}

	public static double calcRatio(double thisMonth, double lastMonth) {
		double ratio = thisMonth / lastMonth;
		if(thisMonth >=1 && lastMonth == 0) {
			ratio = 999.9999;
			return ratio;
		} else if(Double.isInfinite(ratio) || Double.isNaN(ratio)) {
			ratio = 0;
			return ratio;
		} else {
			return ratio;
		}
	}

	public static double calcFluctuationRatio(double thisMonth, double lastMonth) {
		double ratio = thisMonth / lastMonth;
		if(thisMonth >=1 && lastMonth == 0) {
			ratio = 999.9999;
			return ratio - 1;
		} else if(Double.isInfinite(ratio) || Double.isNaN(ratio)) {
			ratio = 0;
			return ratio;
		} else {
			return ratio - 1;
		}
	}

	public static String formatMonth(String today) {
		String s = today.substring(5);
		if(s.indexOf("0") == 0) {
			return s.substring(1);
		}
		return s;
	}

	public static String formatCenterMonth(String today) {
		if(today.lastIndexOf("0") == 5) {
			return today.replace("年0", "年");
		}
		return today;
	}

	public static String deleteComma(String Numbers) {
		return Numbers.replaceAll(",","");
	}

	public static String addNewLine(String Numbers) {
		return Numbers.replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;");
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

	public static String escapeHTML(String val) {
		if (val == null) {
			return "";
		}
		val = val.replaceAll("&", "&amp;");
		val = val.replaceAll("<", "&lt;");
		val = val.replaceAll(">", "&gt;");
		val = val.replaceAll("\"", "&quot;");
		val = val.replaceAll("'", "&apos;");
		return val;
	}

	public static String changeMark(String sort, String karam) {
		String s = null;
		if(sort != null) {
			if(sort.contains(karam)) {
				s = "-by-attributes";
				if(sort.contains("desc")) {
					s = s.concat("-alt");
				}
			}
		}
		return s;
	}

	public static String currentPage(String pageNumber, String currentPage) {

		String s = null;
		String page = pageNumber;
		if(pageNumber.equals("")) {
			page = "0";
		}
		if(page.equals(currentPage)) {
			s = "active";
		}

		return s;
	}
}
