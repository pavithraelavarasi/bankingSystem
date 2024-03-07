package com.sfr.bankingSystem;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

abstract class Customer implements CustomerPortal {
	Display display = new Display();
	Validation valid = new Validation();
	InsertIntoDatabase insert = new InsertIntoDatabase();
//	Bank bank;
	public void customerOptions(String login)
	{
		try
		{
			System.out.println("------------------------------------- Welcome "+valid.getNameFromLogin(login)+" ------------------------------------------");
			System.out.println("\t\tCustomer Options");
			boolean flag = true;
			Bank bank;
			while(flag)
			{
				System.out.println("\n\t\t ENTER ANY OPTION");
				System.out.println("\t\t1 - deposit \n\t\t2 - withdraw \n\t\t3 - transfer fund \n\t\t4 - pay bill \n\t\t5 - view account balance \n\t\t6 - view my transaction \n\t\t7 - exit");
				int opt = BankingSystem.sc.nextInt();
				switch(Options.checkOptions(opt))
				{
					case deposit : 
						deposit(login);
						 break;
					case withdraw : 
						 withdraw(login);
						 break;
					case transferFunds : 
						 transferFunds(login);
						 break;
					case payBill : 
						 payBills(login);
						 break;
					case viewMyAccount : 
						 display.viewAccountDetails(login);
						 break;
					case viewMyTransaction : 
						 display.viewMyTransaction(login);
						 break;
					case exit : 
						 flag = false;
						 break;
					default : System.out.println("Invalid !!!");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	abstract void addCustomer();
	public void deposit(String login)
	{
		try
		{
			display.accountDetails(login);
			System.out.println("Enter your account number");
			long account_num = BankingSystem.sc.nextLong();
			int cus_id = valid.getIdFromLogin(login);
			while(!valid.isCrtAccNum(login,account_num))
			{
				System.out.println("\t\t PLEASE ENTER CORRECT ACCOUNT NUMBER");
				account_num = BankingSystem.sc.nextLong();
			}
			System.out.println("Enter the amount you have to deposit");
			long deposit = BankingSystem.sc.nextLong();
			long balance = valid.returnBalance(login,account_num);
			String acc_type = valid.getAccountType(login,account_num);
			long min_bal = Bank.bank.viewMinimumBalance(acc_type);
			while(balance + deposit < min_bal)
			{
				System.out.println("Pls deposit atleast minimum balance");
				deposit = BankingSystem.sc.nextLong();
			}
			balance = valid.returnBalance(login,account_num);
			if(deposit > 0)
			{
				balance = balance + deposit;
				insert.insertDepositHistory(cus_id,account_num,deposit,balance);
			}
			else
			{
				ExceptionAmount.exception();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void withdraw(String login)
        {
                try
                {
                        display.accountDetails(login);
                        System.out.println("Enter your account number");
                        long account_num = BankingSystem.sc.nextLong();
			int cus_id = valid.getIdFromLogin(login);
			if(!valid.isCrtAccNum(login,account_num))
                        {
                                System.out.println("\t\t PLEASE ENTER CORRECT ACCOUNT NUMBER");
                                return;
                        }
                        System.out.println("Enter the amount you have to withdraw");
                        long withdraw = BankingSystem.sc.nextLong();
			if(!valid.isSufficientAmount(login,account_num,withdraw))
			{
				ExceptionAmount.exception();
//				System.out.println("\t\t YOU HAVE NOT ENOUGH AMOUNT TO WITHDRAW");
				return;
			}
                        long balance = valid.returnBalance(login,account_num);
			String acc_type = valid.getAccountType(login,account_num);
			long min_bal = Bank.bank.viewMinimumBalance(acc_type);
			if(balance - withdraw >= min_bal)
			{
                        	balance = balance - withdraw;
	                	insert.insertWithdrawHistory(cus_id,account_num,withdraw,balance);
			}
			else
			{
				System.out.println("\t\t OOPPSS...INSUFFICIENT BALANCE");
			}
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void billPaying(String login,String bill)
	{
		try
		{
                        display.accountDetails(login);
			System.out.println("Enter your account number");
                        long account_num = BankingSystem.sc.nextLong();
			int cus_id = valid.getIdFromLogin(login);
			if(!valid.isCrtAccNum(login,account_num))
                        {
                                System.out.println("\t\t PLEASE ENTER CORRECT ACCOUNT NUMBER");
                                return;
                        }
			System.out.println("Enter the amount you have to paid");
			long bill_amt = BankingSystem.sc.nextLong();
			System.out.println("Re- enter the amount");
			bill_amt = BankingSystem.sc.nextLong();
			if(!valid.isSufficientAmount(login,account_num,bill_amt))
			{
				System.out.println("\t\t YOU HAVE NOT ENOUGH MONEY TO PAY THE BILL");
				return;
			}
			long balance = valid.returnBalance(login,account_num);
			String acc_type = valid.getAccountType(login,account_num);
			long min_bal = Bank.bank.viewMinimumBalance(acc_type);
			balance = balance - bill_amt;
			if(!(balance - bill_amt >= min_bal))
			{
				System.out.println("\t\t YOU CAN'T PAY THIS AMOUNT ..IT REACHES MINIMUM BALANCE");
				return;
			}
			System.out.println("After boll :"+ balance);
			insert.insertPayBills(cus_id,account_num,bill,bill_amt,balance);
			System.out.println("\t\t"+bill + " PAID SUCCESSFULLY !");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void payBills(String login)
	{
		try
		{
			System.out.println("choose your option to pay bill");
			boolean flag = true;
			while(flag)
			{
				System.out.println("1 - EB BILL \t2 - GAS BILL \t3 - RENT PAYMENT \t4 - LADLINE PHONE BILL \t5 - EXIT");
				int opt = BankingSystem.sc.nextInt();
				switch(opt)
				{
					case 1 : String bill = "EB BILL";
						 billPaying(login,bill);
						 break;
					case 2 : bill = "GAS BILL";
						 billPaying(login,bill);
						 break;
					case 3 : bill = "RENT  ";
						 billPaying(login,bill);
						 break;
					case 4 : bill = "PHONE BILL ";
						 billPaying(login,bill);
						 break;
					case 5 : flag = false;
						 break;
					default : System.out.println("Enter any valid option");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void transferFunds(String login)
	{
		try
		{
                        display.accountDetails(login);
			System.out.println("Enter your account number");
                        long account_num = BankingSystem.sc.nextLong();
			String acc_type = valid.getAccountType(login,account_num);
			int cus_id = valid.getIdFromLogin(login);
			if(!valid.isCrtAccNum(login,account_num))
                        {
                                System.out.println("\t\t PLEASE ENTER CORRECT ACCOUNT NUMBER");
                                return;
                        }
			System.out.println("Enter receiver account number");
			long receiver_acc_num = BankingSystem.sc.nextLong();
			if(!valid.isPresentAccNum(receiver_acc_num))
			{
				System.out.println("\t\t THE ACCOUNT NUMBER NOT AVAILABLE");
				return;
			}
			System.out.println("Enter the amount do you have to send");
			long amount = BankingSystem.sc.nextLong();
			if(valid.isLimitTransfer(account_num,acc_type,amount))
			{
				System.out.println("\t\t UNABLE TO TRANFER ..IT REACHES LIMIT" );
				return;
			}
			if(!valid.isSufficientAmount(login,account_num,amount))
			{
				System.out.println("\t\t YOU HAVE NOT ENOUGH AMOUNT TO TRANSFER");
				return;
			}
			long balance = valid.returnBalance(login,account_num);
			balance = balance - amount;
			long min_bal = Bank.bank.viewMinimumBalance(acc_type);
			if(!(balance - amount >= min_bal))
			{
				System.out.println("\t\t YOU CAN'T TRANSFER THE AMOUNT .. IT REACHES MINIMUM BALANCE");
				return;
			}
//			insertHistory(cus_id,balance);
			insert.insertTransfer(cus_id,account_num,receiver_acc_num,amount,balance);
			System.out.println("\t\t YOU ARE TRANFERED  "+amount + " TO "+ receiver_acc_num +" ACCOUNT HOLDER SUCCESSFULLY !");
			insert.addTransAmtToReceiver(receiver_acc_num,amount);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
enum Options {
	deposit(1),
	withdraw(2),
	transferFunds(3),
	payBill(4),
	viewMyAccount(5),
	viewMyTransaction(6),
	exit(7),
	invalid(8);

	private int opt;
	private Options(int opt)
	{
		this.opt = opt;
	}
	public static Options checkOptions(int choice)
	{
		for(Options option : Options.values())
		{
			if(option.opt == choice)
			{
				return option;
			}
		}
		return invalid;
	}
}
