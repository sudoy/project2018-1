package com.abc.asms.beans;

import java.util.Map;

public class SearchForm {
	private String sql;
	private Map<String, String> sqlParameter;
	public SearchForm(String sql, Map<String, String> sqlParameter) {
		this.sql = sql;
		this.sqlParameter = sqlParameter;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Map<String, String> getSqlParameter() {
		return sqlParameter;
	}
	public void setSqlParameter(Map<String, String> sqlParameter) {
		this.sqlParameter = sqlParameter;
	}

}
