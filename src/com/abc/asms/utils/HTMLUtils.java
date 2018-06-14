package com.abc.asms.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HTMLUtils {

	public static String parseDate(LocalDate date) {
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

	public static int sumCalc(int unitPrice, int saleNumber) {
		return unitPrice * saleNumber;
	}

	public static String dateFormat(String saleDate) {

		String s = "";
		if(saleDate == null) {
			return "";
		}
		s = saleDate.replace("/", "-");
		return s;

	}

	public static String deleteComma(String strNewNum) {
	      return strNewNum.replaceAll(",","");
	    }

}
