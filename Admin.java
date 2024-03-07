package com.sfr.bankingSystem;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.Console;
import java.util.Map;
import java.util.HashMap;

class Admin {

	public void adminOptions(String login)
	{
		Display display = new Display();
		AddInformation add = new AddInformation();
		Validation valid = new Validation();
		try
                {
                        System.out.println("---------------------- Welcome "+valid.getNameFromLogin(login)+" -----------------------------------------");
                        System.out.println("\t\t Admin Options \n\t\t Enter any option");
                        boolean flag = true;
                        Clerk clerk = new Clerk();
                        while(flag)
                        {
                                System.out.println("\n\t\t1 - add manager \n\t\t2 - view customers \n\t\t3 - View transaction particular peroid \n\t\t4 - view customer personal details \n\t\t5 - view staffs \n\t\t6 - exit");
                                int opt = BankingSystem.sc.nextInt();
                                switch(opt)
                                {
					case 1 : add.addManager();
						 break;
					case 2 : display.viewCustomersNameId();
						 break;
					case 3 : display.viewTransactionPeriod();
						 break;
					case 4 : display.viewCustomers();
						 break;
					case 5 : display.viewStaffs();
						 break;
					case 6 : flag = false;
						 break;
					default : System.out.println("Enter valid option");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
