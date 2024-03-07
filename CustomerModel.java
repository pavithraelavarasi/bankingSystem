package com.sfr.bankingSystem;


class CustomerModel {
	private String name;
	private long phone;
	private long accNo;
	private String login;
	private String pwd;
	private long adhar;
	private String pan;

	CustomerModel(String name,long phone,String login,String pwd,long adhar,String pan)
	{
		this.name = name;
		this.phone = phone;
		this.login = login;
		this.pwd = pwd;
		this.adhar = adhar;
		this.pan = pan;
	}
	CustomerModel(String name,String login,String pwd,long phone)
	{
		this.name = name;
		this.login = login;
		this.pwd = pwd;
		this.phone = phone;
	}
	CustomerModel()
	{
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public void setPhone(long phone)
	{
		this.phone = phone;
	}
	public long getPhone()
	{
		return phone;
	}
	public void setAccNo(long accNo)
	{
		this.accNo = accNo;
	}
	public long getAccNo()
	{
		return accNo;
	}
	public void setLogin(String login)
	{
		this.login = login;
	}
	public String getLogin()
	{
		return login;
	}
	public void setPass(String pwd)
	{
		this.pwd = pwd;
	}
	public String getPass()
	{
		return pwd;
	}
	public void setAdhar(long adhar)
	{
		this.adhar = adhar;
	}
	public long getAdhar()
	{
		return adhar;
	}
	public void setPan(String pan)
	{
		this.pan = pan;
	}
	public String getPan()
	{
		return pan;
	}
	public String toString()
	{
		return "\nName :"+name+"\tPhone Number :"+phone+"\tLogin :"+login+"\tPassword :"+pwd +"\tAdhar Number :"+adhar +"\tPan Number :"+pan;
	}
}
