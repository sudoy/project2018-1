package com.abc.asms.beans;

import java.util.List;

public class SearchForm {
	private String sql;
	private List<String> sqlParameter;
	public SearchForm(String sql, List<String> sqlParameter) {
		this.sql = sql;
		this.sqlParameter = sqlParameter;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<String> getSqlParameter() {
		return sqlParameter;
	}
	public void setSqlParameter(List<String> sqlParameter) {
		this.sqlParameter = sqlParameter;
	}

}
