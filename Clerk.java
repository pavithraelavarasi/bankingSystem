package com.sfr.bankingSystem;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.Console;
import java.util.HashMap;
import java.sql.Connection;

class Clerk {
	private static Connection con = Database.getInstance().makeConnection();
        public void clerkOptions(String login)
        {
		AddInformation add = new AddInformation();
		Display display = new Display();
		Validation valid = new Validation();
                try
                {
			System.out.println("----------------------------- Welcome "+valid.getNameFromLogin(login)+" -------------------------------");
                        System.out.println("\t\tClerk Options \n\t\tEnter any option");
                        boolean flag = true;
                        while(flag)
                        {
                                System.out.println("\t\t1 - add customer \n\t\t2 - calculate interest \n\t\t3 - view transaction history \n\t\t4 - view customers \n\t\t5 - view customers personal details\n\t\t6 - exit");
                                int opt = BankingSystem.sc.nextInt();
                                switch(Options.checkOptions(opt))
                                {
                                        case addCustomers : 
						 add.addCustomer();
                                                 break;
					case calculateInterest : 
						 System.out.println("Enter the login of the customer to interest calculation");
                                                 login = BankingSystem.sc.next();
                                                 Bank.bank.calculation(login);
						 break;
					case viewTransactions :
					    	 display.viewTransactionHistory();
                                                 break;
                                        case viewCustomers : 
						 display.viewCustomersNameId();
                                                 break;
					case viewCustomersPersonal : 
						 display.viewCustomers();
						 break;
                                        case exit : 
						 flag = false;
                                                 break;
                                        default : System.out.println("Invalid !!!! ");
                                }
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
}
enum Options {
	addCustomers(1),
	calculateInterest(2),
	viewTransactions(3),
	viewCustomers(4),
	viewCustomersPersonal(5),
	exit(6),
	invalid(7);

	private int choice;
	private Options(int choice)
	{
		this.choice = choice;
	}
	public static Options checkOptions(int select)
	{
		for(Options opt : Options.values())
		{
			if(opt.choice == select)
			{
				return opt;
			}
		}
		return invalid;
	}
}	
