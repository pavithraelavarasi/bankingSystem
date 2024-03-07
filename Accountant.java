package com.sfr.bankingSystem;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.Console;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

class Accountant extends Customer {
	Console console = System.console();
	Validation valid =  new Validation();
	Display display = new Display();
	InsertIntoDatabase insert = new InsertIntoDatabase();
	UserModel user = new UserModel();
        public void accountantOptions(String login)
        {
                try
                {
			System.out.println("---------------------------- Welcome "+valid.getNameFromLogin(login)+" ----------------------------");
                        System.out.println("\t\tAccountant Options");
                        boolean flag = true;
                        while(flag)
                        {
				System.out.println("\n\t\t ENTER ANY OPTION");
                                System.out.println("\t\t1 - add customer \n\t\t2 - close account \n\t\t3 - interest added to balance(once in 3 months) \n\t\t4 - view transaction history \n\t\t5 - view customers \n\t\t6 - view Tranfer funds \n\t\t7 - exit");
                                int option = BankingSystem.sc.nextInt();
                                switch(Options.checkOptions(option))
                                {
                                        case addCustomers : 
						addCustomer();
                                                 break;
					case closeAccount : 
						 closeAccount();
						 break;
					case interestAddToBalanace : 
						 System.out.println("Enter login of the customer to add interest to bank balance");
						 login = console.readLine();
						 if(!valid.isLogin(login))
						 {
							 System.out.println("\t\t NO SUCH LOGIN");
							 return;
						 }
						 insert.addInterestToBalance(login);
						 break;
                                        case viewTransactions : 
						 display.viewTransactionHistory();
                                                 break;
                                        case viewCustomers : 
						 display.viewCustomersNameId();
                                                 break;
					case viewTransferFunds : 
						 System.out.println("Enter the login of the customer to view their tranfer funds");
						 login = console.readLine();
						 if(!valid.isLogin(login))
                                                 {
                                                         System.out.println("\t\t NO SUCH LOGIN");
                                                         return;
                                                 }
						 display.viewTranferFunds(login);
						 break;
                                        case exit : 
						 flag = false;
                                                 break;
                                        default : System.out.println("invalid !!!");
                                }
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	private void closeAccount()
	{
		System.out.println("Enter your account number to close");
                long acc_num = BankingSystem.sc.nextLong();
		try(ResultSet rs = Database.st.executeQuery("select customer_id,balance from transaction_history where account_number = "+acc_num+"order by date desc"))
		{
			int cus_id =0;
			long balance = 0;
			while(rs.next())
			{
				cus_id = rs.getInt(1);
				balance = rs.getLong(2);
			}
				System.out.println("\t\t YOU HAVE AMOUNT :"+ balance +" IN YOUR ACCOUNT");
				System.out.println("press 1 for withdraw \t press 2 for deposit to another account \t3 - exit");
				int opt = BankingSystem.sc.nextInt();
				boolean flag = true;
				if(opt == 1)
				{
					insert.insertWithdrawHistory(cus_id,acc_num,balance,0);
					valid.changeStatus(acc_num);
				}
				else if(opt == 2)
				{
					System.out.println("Enter your another account number to deposit the amount");
                                        long acc = BankingSystem.sc.nextLong();
					int id = valid.getIdFromAcc(acc);
                                        insert.insertDepositAnotherAcc(id,acc,balance);
					valid.changeStatus(acc_num);
				}
				else
				{
					System.out.println("Thank you");
					return;
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
	closeAccount(2),
	interestAddToBalanace(3),
	viewTransactions(4),
	viewCustomers(5),
	viewTransferFunds(6),
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
