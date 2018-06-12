package com.abc.asms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HTMLUtils {

	public static String parseDate(Date date) {

		String s = "";
		if(date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		s = sdf.format(date);

		return s;

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
}
