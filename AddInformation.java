package com.sfr.bankingSystem;

import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.Console;

class AddInformation {
	InsertIntoDatabase insert = new InsertIntoDatabase();
	Validation valid = new Validation();
	Console console = System.console();

	public void addAdmin()
        {
                try
                {
                        UserModel users = new UserModel("pavithra","pavi01","pavi",8657486567l);
                        HashMap<Integer,String> role = valid.getRoles();
                        insert.insertAdmin(role,users);
                /*      System.out.println("inside the admin add");
                        System.out.println("Enter admin name:");
                        String name = console.readLine();
                        System.out.println("Enter your login:");
                        String login = console.readLine();
                        System.out.println("Enter your password:");
                        String password = console.readLine();
                        System.out.println("Enter yout phone number");
                        long phone = BankingSystem.sc.nextLong();
                        while(!valid.isValidPhoneNumber(phone))
                        {
                                System.out.println("Enter again phone number (with 10 digits)");
                                phone = BankingSystem.sc.nextLong();
                        }
                        insert.insertAdmin(name,login,password,phone);
                        return;*/
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
        public void addManager()
        {
                try
                {
                        System.out.println("Enter manager name:");
                        String name = console.readLine();
                        System.out.println("Enter your login:");
                        String login = console.readLine();
                        System.out.println("Enter your password:");
                        String password = console.readLine();
                        System.out.println("Enter yout phone number");
                        long phone = BankingSystem.sc.nextLong();
                        while(!valid.isValidPhoneNumber(phone))
                        {
                                System.out.println("Enter again phone number (with 10 digits)");
                                phone = BankingSystem.sc.nextLong();
                        }
                        UserModel users = new UserModel(name,login,password,phone);
			HashMap<Integer,String> role = valid.getRoles();
                        insert.insertManager(role,users);
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	void addAccountant()
        {
                try
                {
                        if(!valid.enoughStaff(4))
                        {
                                System.out.println("\t\t ALREADY THREE ACCOUNTANT ARE THERE");
                                return;
                        }
                        System.out.println("Enter Accountant name:");
                        String name = console.readLine();
                        System.out.println("Enter your login:");
                        String login = console.readLine();
                        if(valid.isLogin(login))
                        {
                                System.out.println("\t\t THE ACCOUNTANT IS ALREADY EXISTS");
                                return;
                        }
                        System.out.println("Enter your password:");
                        String password = console.readLine();
                        System.out.println("Enter yout phone number");
                        long phone = BankingSystem.sc.nextLong();
                        while(!valid.isValidPhoneNumber(phone))
                        {
                                System.out.println("Enter again phone number (with 10 digits)");
                                phone = BankingSystem.sc.nextLong();
                        }
                        UserModel users = new UserModel(name,login,password,phone);
                        HashMap<Integer,String> role = valid.getRoles();
                        insert.insertAccountant(role,users);
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	void addClerk()
        {
                try
                {
                        if(!valid.enoughStaff(3))
                        {
                                System.out.println("\t\t ALREADY THREE CLERKS ARE THERE ..");
                                return;
                        }
                        System.out.println("Enter clerk name:");
                        String name = console.readLine();
                        System.out.println("Enter your login:");
                        String login = console.readLine();
                        if(valid.isLogin(login))
                        {
                                System.out.println("\t\t THE CLERK IS ALREADY EXISTS");
                                return;
                        }
                        System.out.println("Enter your password:");
                        String password = console.readLine();
                        System.out.println("Enter yout phone number");
                        long phone = BankingSystem.sc.nextLong();
                        while(!valid.isValidPhoneNumber(phone))
                        {
                                System.out.println("Enter again phone number (with 10 digits)");
                                phone = BankingSystem.sc.nextLong();
                        }
                        UserModel users = new UserModel(name,login,password,phone);
                        HashMap<Integer,String> role = valid.getRoles();
                        insert.insertClerk(role,users);
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
	public void addCustomer()
        {
                try
                {
                        System.out.println("Enter your name:");
                        String name = console.readLine();
                        System.out.println("Enter your date of birth");
                        String date = BankingSystem.sc.next();
                        //Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(date);
/*                      if(!date.matches("^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$") || (!valid.isValidDate(date)) || (!valid.isDateOrder(dob)))
                        {
                                System.out.println("Enter valid date in the format(dd/MM/yyyy)  / invalid date / Given date of birth is invalid \t DOB must before to the today date");
                                return;
                        }*/
                        if(!date.matches("^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$"))
                        {
                                System.out.println("Enter valid date in the format(dd/MM/yyyy)");
                                return;
                        }
                        Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                        if((!valid.isValidDate(date)) || (!valid.isDateOrder(dob)))
                        {
                                System.out.println("Enter valid date ");
                                return;
                        }
                        java.sql.Date dob_sql = new java.sql.Date(dob.getTime());
                        System.out.println("Enter your login:");
                        String login = console.readLine();
                        if(valid.isAlreadyCustomer(login))
                        {
                                System.out.println("\t THE CUSTOMER IS ALREADY HERE");
                                System.out.println("press 1 to add another account \t press 2 to exit");
                                int opt = BankingSystem.sc.nextInt();
                                if(opt == 1)
                                {
                                        getAccountCreateInfo(login,dob);
                                        return;
                                }
                                else
                                        return;
                        }
                        System.out.println("Enter your password:");
                        String password = console.readLine();
                        System.out.println("Enter your phone number");
                        long phone = BankingSystem.sc.nextLong();
			while(!valid.isValidPhoneNumber(phone))
                        {
                                System.out.println("Enter again phone number (with 10 digits)");
                                phone = BankingSystem.sc.nextLong();
                        }
                        System.out.println("Enter your pan number");
                        String pan_num = BankingSystem.sc.next();
                        pan_num = pan_num.toUpperCase();
                        while(!valid.isValidPan(pan_num))
                        {
                                System.out.println("Enter valid pan number (like AFZPK7190K ..)");
                                pan_num = BankingSystem.sc.next();
                        }
                        System.out.println("Enter your adhar number");
                        long adhar_num = BankingSystem.sc.nextLong();
                        while(!valid.isValidAdhar(adhar_num))
                        {
                                System.out.println("\t\t PLEASE ENTER CORRECT ADHAR NUMBER (with 12 digits )");
                                adhar_num = BankingSystem.sc.nextLong();
                        }
                        CustomerModel customer = new CustomerModel(name,phone,login,password,adhar_num,pan_num);
                        HashMap<Integer,String> role = valid.getRoles();
                        insert.insertCustomer(role,customer,dob_sql);
                        getAccountCreateInfo(login,dob);
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
        private void getAccountCreateInfo(String login,Date dob)
        {
                try
                {
                        Customer customer = new Accountant();
                        String scheme = valid.getValidScheme(dob);
                        System.out.println("\t\t YOUR SCHEME :"+ scheme);
			System.out.println("Choose your account type");
                        System.out.println("1 - savings account \t2 - current account \t3 - salary account");
                        int opt = BankingSystem.sc.nextInt();
                        String account_type;
                        if(opt == 1)
                                account_type = "savings account";
                        else if(opt == 2)
                                account_type = "current account";
                        else
                                account_type = "salary account";
                        if(valid.isAlreadyAccType(login,account_type))
                        {
                                System.out.println("\t\t YOU ALREADY HAVE THIS ACCOUNT TYPE ACCOUNT ");
                                return;
                        }
                        long min_bal = Bank.bank.viewMinimumBalance(account_type);
                        System.out.println("choose your branch");
                        System.out.println("1 - Srivi branch \t2 - sivakasi branch \t3 - rajapalayam branch");
                        opt = BankingSystem.sc.nextInt();
                        String branch;
                        if(opt == 1)
                                branch = "srivi";
                        else if(opt == 2)
                                branch = "sivakasi";
                        else
                                branch = "rajapalayam";
                        if(valid.isAlreadyCustomer(login))
                        {
                              insert.insertCustomerAnotherAcc(login,branch,scheme,account_type);
                              customer.deposit(login);
                        }
                        else
                        {
                                insert.insertIntoAccountDetails(login,branch,scheme,account_type);
                                customer.deposit(login);
                        }
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
}
