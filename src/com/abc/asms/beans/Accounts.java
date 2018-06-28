package com.abc.asms.beans;

public class Accounts {

	private int accountId;
	private String name;
	private String kana;
	private String mail;
	private String password;
	private int authority;
	private int version;

	public Accounts(int accountId, String name, String kana, String mail, String password, int authority, int version) {
		super();
		this.accountId = accountId;
		this.name = name;
		this.kana = kana;
		this.mail = mail;
		this.password = password;
		this.authority = authority;
		this.version = version;
	}

	public Accounts(int accountId, String name, String kana, String mail, String password, int authority) {
		super();
		this.accountId = accountId;
		this.name = name;
		this.kana = kana;
		this.mail = mail;
		this.password = password;
		this.authority = authority;
	}

	public Accounts(int accountId, String name, String mail, String password, int authority) {
		super();
		this.accountId = accountId;
		this.name = name;
		this.mail = mail;
		this.password = password;
		this.authority = authority;
	}

	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKana() {
		return kana;
	}
	public void setKana(String kana) {
		this.kana = kana;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
