package com.sfr.bankingSystem;

class TableCreation {

	public static void tables()
	{
		try
		{
			createRole();
			createLogin();
			createCustomers();
			createOfficers();
			createAccountDetails();
			createTransactionHistory();
			createInterest();
			createAccInterestTable();
			createPayBills();
			createTransferFunds();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void createRole()
	{
		try
		{
			String query = "create table if not exists role(role_id serial primary key,role varchar(20)unique)";
			Database.st.executeUpdate(query);
			System.out.println("Role table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void createLogin()
	{
		String query = "create table if not exists login(login varchar(20)unique,password varchar(20),role_id int,constraint fk_role_manager foreign key(role_id) references role(role_id) on update cascade on delete cascade)";
		try
		{
			Database.st.executeUpdate(query);
			System.out.println("Login table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void createCustomers()
	{
		try
		{
			String query = "create table if not exists customers(customer_id int primary key,name varchar(20),login varchar(20),password varchar(20),date_of_birth date,adhar_number bigint,pan_number varchar(20),phone bigint,role_id int,constraint fk_role_manager foreign key(role_id) references role(role_id) on update cascade on delete cascade)";
			Database.st.executeUpdate(query);
			System.out.println("customer table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
/*	public static void createRequested()
	{
		try
		{
			String query = "create table if not exists requested(name varchar(20),login varchar(20) unique primary key,password varchar(20),account_status boolean default 'false')";
			Database.st.executeUpdate(query);
			System.out.println("Requested table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/
	public static void createOfficers()
	{
		try
		{
			String query = "create table if not exists all_users(id serial primary key,name varchar(20),login varchar(20)unique,password varchar(20),phone bigint,role_id int,constraint fk_role_manager foreign key(role_id) references role(role_id) on update cascade on delete cascade)";
			Database.st.executeUpdate(query);
			System.out.println("All users table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void createAccountDetails()
	{
		try
		{
			String query = "create table if not exists account_details(customer_id int, constraint fk_customer_account foreign key (customer_id) references customers (customer_id)on delete cascade on update cascade,account_number bigint unique,branch text,scheme text,account_type text,active_status boolean default 'true')";
			Database.st.executeUpdate(query);
			System.out.println("Account details table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void createTransactionHistory()
	{
		try
		{
			String query = "create table if not exists transaction_history(customer_id int,constraint fk_customer_transaction foreign key (customer_id) references customers (customer_id) on delete cascade on update cascade,account_number bigint,date TIMESTAMP,description text,received DECIMAL(10,2),bill DECIMAL(10,2),transfer DECIMAL(10,2),withdrawals DECIMAL(10, 2),deposits DECIMAL(10, 2),balance DECIMAL(10, 2))";
			Database.st.executeUpdate(query);
			System.out.println("Transaction history table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void createPayBills()
	{
		try
		{
			String query = "create table if not exists pay_bills(customer_id int,constraint fk_customer_bill foreign key (customer_id) references customers (customer_id)on delete cascade on update cascade,account_number bigint,date TIMESTAMP,bill varchar(20),amount DECIMAL(10, 2))";
			Database.st.executeUpdate(query);
			System.out.println("Pay pill table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void createTransferFunds()
	{
		try
		{
			String query = "create table if not exists transfer_funds(customer_id int,constraint fk_customer_tranferfund foreign key (customer_id) references customers (customer_id)on delete cascade on update cascade,sender_account_number bigint ,receiver_account_number bigint ,amount DECIMAL(10, 2),transfer_date TIMESTAMP)";
			Database.st.executeUpdate(query);
			System.out.println("Transfer funds table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void createAccInterestTable()
	{
		try
		{
			String query = "create table if not exists account_interest(customer_id int,constraint fk_customer_tranferfund foreign key (customer_id) references customers (customer_id)on delete cascade on update cascade,account_number bigint,date Timestamp,scheme text,interest decimal(10,2))";
			Database.st.executeUpdate(query);
			System.out.println("balance table create");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void createInterest()
	{
		try
		{
			String query = "create table if not exists interest(account_scheme text,interest decimal(10,2))";
			Database.st.executeUpdate(query);
			System.out.println("interest table created");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
