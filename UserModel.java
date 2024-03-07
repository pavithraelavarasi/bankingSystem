package com.sfr.bankingSystem;

class UserModel extends CustomerModel {

	private String name;
	private String login;
	private String pwd;
	private long phone;
	
	UserModel()
	{
	}
	UserModel(String name,String login,String pwd,long phone)
	{
		super(name,login,pwd,phone);
	}
}

