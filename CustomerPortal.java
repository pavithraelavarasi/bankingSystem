package com.sfr.bankingSystem;

interface CustomerPortal {
	void deposit(String login);
	void withdraw(String login);
	void billPaying(String login,String bill);
	void transferFunds(String login);
}
