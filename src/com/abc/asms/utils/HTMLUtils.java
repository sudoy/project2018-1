package com.abc.asms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

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

	public static String DeleteComma(String strNum) {

	      //トークン格納用
	      String strNextToken = "";

	      //戻り値格納用(編集後数値)
	      String strNewNum = "";

	      StringTokenizer st = new StringTokenizer(strNum, ",");

	      //トークンが存在する間ループし変数にトークンを格納
	      while (st.hasMoreTokens()) {
	        strNextToken = st.nextToken();
	        strNewNum += strNextToken;
	      }

	      return strNewNum;

	    }

}
