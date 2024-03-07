package com.sfr.bankingSystem;

class InsufficientAmountException extends Exception {
	public InsufficientAmountException(String s)
	{
		super(s);
	}
}
public class ExceptionAmount {
	public static void exception()
	{
		try
		{
			throw new InsufficientAmountException("\t\t Oooppsss..insufficient amount in your account");
		}
		catch(InsufficientAmountException e)
		{
			System.out.println("\t......insufficient amount exception caught");
			System.out.println(e.getMessage());
		}
	}
}
