package com.sfr.bankingSystem;

class Manager {
	public void managerOptions(String login)
        {
		Display display = new Display();
		Validation valid = new Validation();
		AddInformation add = new AddInformation();
                try
                {
                        System.out.println("---------------------- Welcome "+valid.getNameFromLogin(login)+" -----------------------------------------");
                        System.out.println("\t\tManager Options \n\t\t Enter any option");
                        boolean flag = true;
                        Clerk clerk = new Clerk();
                        while(flag)
                        {
                                System.out.println("\n\t\t1 - add clerk \n\t\t2 - add accountant \n\t\t3 - view transaction history \n\t\t4 - view customers \n\t\t5 - view customer personal details \n\t\t6 - exit");
                                int opt = BankingSystem.sc.nextInt();
                                switch(opt)
                                {
                                        case 1 : add.addClerk();
                                                 break;
                                        case 2 : add.addAccountant();
                                                 break;
                                        case 3 : display.viewTransactionHistory();
                                                 break;
                                        case 4 : display.viewCustomersNameId();
                                                 break;
                                        case 5 : display.viewCustomers();
                                                 break;
                                        case 6 : flag = false;
                                                 break;
                                        default : System.out.println("Enter any valid options");
                                }
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
}
